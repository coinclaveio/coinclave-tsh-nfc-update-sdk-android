package io.coinclave.crypto.applet.update.nfc.handlers

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

interface NFCCommandHandler<I : AbstractNFCCommand> {

    fun getHandledClass(): Class<I>

    fun handle(command: I, listener: NFCCommandHandlerListener)

    fun compileRequest(command: I, action: BaseNFCExchangeAction): List<CommandApdu>?

    fun needGetAdditionalCommands(action: BaseNFCExchangeAction): Boolean

    fun getAdditionalCommands(action: BaseNFCExchangeAction): List<BaseNFCExchangeAction>?

}