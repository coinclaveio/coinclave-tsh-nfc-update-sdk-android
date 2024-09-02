package io.coinclave.crypto.applet.update

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.coinclave.crypto.applet.update.dto.BlankResult
import io.coinclave.crypto.applet.update.dto.Error
import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.CommandAggregate
import io.coinclave.crypto.applet.update.nfc.handlers.aggregates.AllAggregateHandlers
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu

const val PARAM_SMART_CARD_CONTENT = "io.coinclave.crypto.applet.activation.NfcHandlerActivity.attach"

/**
 * Activity for work with NFC applet.
 */
open class NfcHandlerActivity : AppCompatActivity() {

    private lateinit var viewModel: NfcAttachActivityViewModel
    private lateinit var aggregateHandlers: AllAggregateHandlers
    private lateinit var logText: TextView
    private var logLayout = false;
    private var commandAggregate: CommandAggregate? = null
    protected val cryptoWalletClientDictionary = CommandDictionaryKit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        aggregateHandlers = AllAggregateHandlers()

        commandAggregate = intent.getSerializableExtra(Intent.EXTRA_SUBJECT) as CommandAggregate
        if (commandAggregate == null) {
            throw RuntimeException("Nfc CommandAggregate is empty")
        }

        val viewModel by viewModels<NfcAttachActivityViewModel>()
        this.viewModel = viewModel

        if (!viewModel.nfcIsSupported()) {
            setResult(Activity.RESULT_CANCELED, Intent().apply {
                putExtra(Intent.EXTRA_SUBJECT, BlankResult(null).apply {
                    errors.add(Error.NFC_NOT_SUPPORTED)
                })
            })
            finish()
            return
        }

        val contentExtra = intent.getIntExtra(PARAM_SMART_CARD_CONTENT, -1)
        if (contentExtra == -1) {
            logLayout = true;
            setContentView(R.layout.nfc_activity_log)
            logText = findViewById(R.id.logText)
        } else {
            setContentView(contentExtra)
        }

        viewModel.state.observe(this) {
            it?.let {
                when (it) {
                    NfcAttachActivityViewModel.State.FINISHED -> {
                        if (logLayout) {
                            logText.setText(logText.text.toString() + "\nFinish NFC exchange")
                        }
                        inputNFCResult(viewModel.connectivityResult)
                    }

                    NfcAttachActivityViewModel.State.INTERMEDIATE_RESULT -> {
                        viewModel.intermediateResult?.let { action ->
                            if (logLayout && !action.responses.isEmpty()) {
                                action.responses.values.forEach {
                                    logText.setText(logText.text.toString() + "\nIntermediate result form card: " + StringUtils.dump(it))
                                }
                            } else {
                                setContentView(R.layout.nfc_activity_log);
                            }
                            viewModel.intermediateResultHandled(
                                inputNFCIntermediateResult(viewModel.intermediateCommand!!, action)
                            )
                        }
                    }

                    NfcAttachActivityViewModel.State.ERROR_CONNECT -> {
                        if (logLayout) {
                            logText.setText(logText.text.toString() + "\nError NFC exchange")
                        } else {
                            setResult(RESULT_CANCELED)
                            finish()
                        }
                    }

                    NfcAttachActivityViewModel.State.WAITING -> {
                        if (logLayout) {
                            logText.setText(logText.text.toString() + "\nWaiting")
                        } else {
                            setContentView(R.layout.nfc_activity_log);
                        }
                    }
                    NfcAttachActivityViewModel.State.EXCHANGING -> {
                        if (logLayout) {
                            logText.setText("Start NFC exchange")
                        } else {
                            setContentView(R.layout.nfc_activity_log);
                        }
                    }
                    NfcAttachActivityViewModel.State.COMMAND_NOT_SUPPORTED -> {
                        if (logLayout) {
                            logText.setText(logText.text.toString() + "\nCommand not supported")
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        processNFCAPDUMessage(intent)
        super.onNewIntent(intent)
    }

    private fun processNFCAPDUMessage(intent: Intent) {
        val action = intent.action
        if (!viewModel.nfcFormatIsSupported(action)) {
            return
        }
        this.intent = intent
        val tag: Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)!!
        viewModel.init(this, tag)
        viewModel.startCardExchange(commandAggregate!!.getCommands())
    }

    fun inputNFCResult(response: ResponseApdu?) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(Intent.EXTRA_SUBJECT, aggregateHandlers.handleResult(response, commandAggregate!!))
        })
        finish()
    }

    fun inputNFCIntermediateResult(command: AbstractNFCCommand, action: BaseNFCExchangeAction): Boolean {
        return aggregateHandlers.handleIntermediateResult(action, command, commandAggregate!!)
    }

    override fun onResume() {
        super.onResume()

        if (!viewModel.nfcIsSupported()) {
            return
        }

        if (!viewModel.nfcAdapterIsEnabled()) {
            showWirelessSettingsDialog()
        } else {
            viewModel.setupForegroundDispatch(this)
        }
    }

    private fun showWirelessSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> finish() }
        builder.create().show()
        return
    }

    internal fun nfcSupported(): Boolean {
        return viewModel.nfcIsSupported()
    }

}