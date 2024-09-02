package io.coinclave.crypto.applet.update.exceptions

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand

class ExchangeNfcException(override val message: String, val command: AbstractNFCCommand?): RuntimeException() {
}