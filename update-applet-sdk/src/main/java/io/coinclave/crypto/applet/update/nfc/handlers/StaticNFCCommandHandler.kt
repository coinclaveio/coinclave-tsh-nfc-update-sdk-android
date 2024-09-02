package io.coinclave.crypto.applet.update.nfc.handlers

import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.StaticNFCCommand
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

class StaticNFCCommandHandler : BaseNFCCommandHandler<StaticNFCCommand>() {

    override fun getAdditionalCommands(action: BaseNFCExchangeAction): List<BaseNFCExchangeAction>? {
        throw RuntimeException("NFC request not created")
    }

    override fun getHandledClass(): Class<StaticNFCCommand> {
        return StaticNFCCommand::class.java
    }

    override fun needGetAdditionalCommands(action: BaseNFCExchangeAction): Boolean {
        return false
    }

    override fun compileRequest(command: StaticNFCCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        throw RuntimeException("NFC request not created")
    }

}