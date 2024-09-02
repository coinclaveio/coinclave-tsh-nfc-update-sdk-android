package io.coinclave.crypto.applet.update.nfc.handlers.aggregates

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu
import java.io.Serializable

interface AggregateHandler {

    fun handleResult(response: ResponseApdu?): Serializable

    fun handleIntermediateResult(command: AbstractNFCCommand, currentAction: BaseNFCExchangeAction): Boolean

}