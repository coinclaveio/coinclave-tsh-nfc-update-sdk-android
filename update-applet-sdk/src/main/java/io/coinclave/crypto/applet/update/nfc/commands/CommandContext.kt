package io.coinclave.crypto.applet.update.nfc.commands

import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu
import java.io.Serializable

class CommandContext: Serializable {

    private val values = HashMap<CommandApdu, ResponseApdu>()
    private val requests = ArrayList<CommandApdu>()
    private val abstractValues = HashMap<String, Any>()
    private var currentStepIndex = 0

    fun getValueFromStep(command: CommandApdu): ResponseApdu? {
        return values[command]
    }

    fun putValueFromStep(command: CommandApdu, data: ResponseApdu?) {
        data?.let {
            values[command] = it
            requests.add(command)
        }
    }

    fun incrementStepIndex() {
        currentStepIndex++
    }

    fun putAbstractValue(key: String, obj: Any) {
        abstractValues[key] = obj
    }

    fun getAbstractValue(key: String): Any? {
        return abstractValues[key]
    }

    fun getLastRequest(): CommandApdu {
        return requests.last()
    }

}