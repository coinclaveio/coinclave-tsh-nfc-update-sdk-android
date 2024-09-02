package io.coinclave.crypto.applet.update.nfc.handlers

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu

interface NFCCommandHandlerListener {

    fun prepare()

    fun destroy()

    fun send(command: CommandApdu): ResponseApdu?

    fun handleResult(response: ResponseApdu?)

    fun handleIntermediateResult(command: AbstractNFCCommand, currentAction: BaseNFCExchangeAction): ResultCode

    fun handleException(e: Exception)

    public enum class ResultCode {
        RESULT_HANDLED, NEED_INTERRUPT, NEED_COMPILE_REQUEST
    }

}