package io.coinclave.crypto.applet.update.nfc.commands

data class UpdateAppletCommand(val oldVersion: String, val newVersion: String, val commandActions: List<DynamicNFCExchangeAction>) : AbstractNFCCommand(commandActions)