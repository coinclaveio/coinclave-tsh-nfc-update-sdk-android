package io.coinclave.crypto.applet.update.nfc.commands.aggregates

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand

class CheckAppletVersionCommandsAggregate(private val commands: AbstractNFCCommand): CommandAggregate {

    override fun getCommands(): AbstractNFCCommand {
        return commands
    }

}
