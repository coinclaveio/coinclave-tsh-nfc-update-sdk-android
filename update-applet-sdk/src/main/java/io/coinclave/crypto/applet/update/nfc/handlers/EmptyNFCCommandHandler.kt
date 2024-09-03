package io.coinclave.crypto.applet.update.nfc.handlers

import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.CheckAppletVersionCommand
import io.coinclave.crypto.applet.update.nfc.commands.EmptyNFCCommand
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

class EmptyNFCCommandHandler: NFCCommandHandler<EmptyNFCCommand> {

    override fun getHandledClass(): Class<EmptyNFCCommand> {
        return EmptyNFCCommand::class.java
    }

    override fun needGetAdditionalCommands(action: BaseNFCExchangeAction): Boolean {
        return false
    }

    override fun getAdditionalCommands(command: EmptyNFCCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        throw RuntimeException("Get additional commands not implement for empty command handler")
    }

    override fun handle(command: EmptyNFCCommand, listener: NFCCommandHandlerListener) {
        val action = command.actions[0]
        action.requests!!.forEach {
            listener.send(it)
        }
    }

    override fun compileRequest(command: EmptyNFCCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        throw RuntimeException("NFC request create not implement for empty command handler")
    }
}