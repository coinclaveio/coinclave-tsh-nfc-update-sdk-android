package io.coinclave.crypto.applet.update

import android.app.Activity
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.coinclave.crypto.applet.update.exceptions.ExchangeNfcException
import io.coinclave.crypto.applet.update.nfc.NFCManager
import io.coinclave.crypto.applet.update.nfc.NFCProvider
import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.RawAPDUCommand
import io.coinclave.crypto.applet.update.nfc.handlers.NfcCommandHandlersStorage
import io.coinclave.crypto.applet.update.nfc.handlers.NFCCommandHandler
import io.coinclave.crypto.applet.update.nfc.handlers.NFCCommandHandlerListener
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu

class NfcAttachActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val supportFormats = arrayOf(
        NfcAdapter.ACTION_TAG_DISCOVERED,
        NfcAdapter.ACTION_TECH_DISCOVERED,
        NfcAdapter.ACTION_NDEF_DISCOVERED
    )

    private var command: AbstractNFCCommand? = null
    private var nfcAdapter: NfcAdapter? = null
    private var nfcProvider: NFCProvider? = null
    private var nfcManager: NFCManager? = null
    private var context: Context? = null
    private var tag: Tag? = null
    internal var state = MutableLiveData<State>().apply { value = State.WAITING }
    internal var connectivityResult: ResponseApdu? = null
    internal var intermediateResult: BaseNFCExchangeAction? = null
    internal var intermediateCommand: AbstractNFCCommand? = null
    internal var intermediateResultHandled: Boolean = false
    private lateinit var nfcCommandHandlersStorage: NfcCommandHandlersStorage

    init {
        nfcAdapter = NfcAdapter.getDefaultAdapter(application)
    }

    internal fun init(
        activity: Activity,
        tag: Tag
    ) {
        this.nfcManager = NFCManager(activity, nfcAdapter)
        this.nfcProvider = NFCProvider(nfcManager)
        this.tag = tag
        this.nfcCommandHandlersStorage =
            NfcCommandHandlersStorage()
        this.context = activity.applicationContext
    }

    internal fun startCardExchange(command: AbstractNFCCommand) {
        if (this.command != null) {
            return
        }

        this.command = command

        val handler = findHandler(command)
        if (handler == null) {
            state.value = State.COMMAND_NOT_SUPPORTED
            return
        }

        state.value = State.EXCHANGING

        handler.handle(command, object : NFCCommandHandlerListener {
            override fun prepare() {
                state.value = State.EXCHANGING
                nfcProvider!!.connectReader(IsoDep.get(tag))
                nfcProvider!!.connectCard()
            }

            override fun destroy() {
                nfcProvider!!.removeCard()
                nfcProvider!!.disconnectReader()
            }

            override fun send(command: CommandApdu): ResponseApdu? {
                try {
                    return if (command is RawAPDUCommand) {
                        ResponseApdu(nfcProvider!!.sendReceive(command.command))
                    } else {
                        ResponseApdu(nfcProvider!!.sendReceive(command.commandBytes))
                    }
                } catch (e: Exception) {
                    state.value = State.ERROR_CONNECT
                }
                return null
            }

            override fun handleResult(response: ResponseApdu?) {
                connectivityResult = response
                state.value = State.FINISHED
            }

            override fun handleIntermediateResult(
                command: AbstractNFCCommand,
                action: BaseNFCExchangeAction
            ): NFCCommandHandlerListener.ResultCode {
                intermediateResult = action
                intermediateCommand = command
                intermediateResultHandled = false
                state.value = State.INTERMEDIATE_RESULT
                return if (intermediateResultHandled)
                    NFCCommandHandlerListener.ResultCode.RESULT_HANDLED
                else NFCCommandHandlerListener.ResultCode.NEED_INTERRUPT
            }

            override fun handleException(e: Exception) {
                if (e is ExchangeNfcException) {
                    intermediateCommand = e.command
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
                state.value = State.ERROR_CONNECT
            }
        })
    }

    fun intermediateResultHandled(handled: Boolean) {
        intermediateResultHandled = handled
     }

    private fun findHandler(command: AbstractNFCCommand): NFCCommandHandler<AbstractNFCCommand>? {
        val filteredHandlers = nfcCommandHandlersStorage.handlers.filter { command.javaClass == it.getHandledClass() }
        if (filteredHandlers.isEmpty()) {
            return null
        }
        return filteredHandlers[0]
    }

    internal fun nfcIsSupported(): Boolean {
        return nfcAdapter != null
    }

    internal fun nfcFormatIsSupported(action: String?): Boolean {
        if (action == null) {
            return false
        }
        return supportFormats.contains(action)
    }

    fun nfcAdapterIsEnabled(): Boolean {
        return nfcAdapter!!.isEnabled
    }

    fun getCommand(): AbstractNFCCommand? {
        return intermediateCommand
    }

    fun setupForegroundDispatch(activity: Activity) {
        nfcAdapter?.let {
            val intent = Intent(activity.applicationContext, activity.javaClass).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)
            val filters = arrayOf(
                IntentFilter().apply {
                    addAction(NfcAdapter.ACTION_TECH_DISCOVERED)
                    addCategory(Intent.CATEGORY_DEFAULT)
                },
                IntentFilter().apply {
                    addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
                    addCategory(Intent.CATEGORY_DEFAULT)
                },
                IntentFilter().apply {
                    addAction(NfcAdapter.ACTION_TAG_DISCOVERED)
                    addCategory(Intent.CATEGORY_DEFAULT)
                }
            )
            val techList = arrayOf<Array<String>>()
            it.enableForegroundDispatch(activity, pendingIntent, filters, techList)
        }
    }

    internal enum class State {
        WAITING, EXCHANGING, FINISHED, COMMAND_NOT_SUPPORTED, ERROR_CONNECT, INTERMEDIATE_RESULT
    }

}