package io.coinclave.crypto.applet.update.nfc.commands

import java.io.Serializable

abstract class AbstractNFCCommand(val actions: List<BaseNFCExchangeAction>) : Serializable {

    val context = CommandContext()

}