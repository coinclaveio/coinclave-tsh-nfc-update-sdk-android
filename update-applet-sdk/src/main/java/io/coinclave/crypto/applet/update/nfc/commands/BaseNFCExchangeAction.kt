package io.coinclave.crypto.applet.update.nfc.commands

import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu
import java.io.Serializable

open class BaseNFCExchangeAction(val action: Action): Serializable {

    private var state = State.WAITING
    val responses = HashMap<CommandApdu, ByteArray>()
    var requests: List<CommandApdu>? = null

    private enum class State {
        WAITING, PENDING, SENDING, SENT, RECEIVED, ERROR_REPEAT, ERROR
    }

    enum class Action {
        EMPTY, INIT_CHECK_APPLET_VERSION, PROCESS_CHECK_APPLET_VERSION, INIT_UPDATE_APPLET_VERSION, PROCESS_UPDATE_APPLET_VERSION
    }

    fun putValueFromStep(command: CommandApdu, data: ByteArray) {
        responses[command] = data
    }

    fun getLastRequest(): CommandApdu? {
        if (requests == null || requests!!.isEmpty()) {
            return null
        }
        return requests!!.get(requests!!.size - 1)
    }

    fun getLastResponse(): ByteArray? {
        val lastRequest = getLastRequest() ?: return null;
        return responses[lastRequest]
    }

}