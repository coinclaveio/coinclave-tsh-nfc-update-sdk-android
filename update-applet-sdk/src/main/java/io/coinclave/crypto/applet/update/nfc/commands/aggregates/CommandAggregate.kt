package io.coinclave.crypto.applet.update.nfc.commands.aggregates

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import java.io.Serializable

interface CommandAggregate: Serializable {

    fun getCommands(): AbstractNFCCommand

}