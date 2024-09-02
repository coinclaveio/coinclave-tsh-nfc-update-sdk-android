package io.coinclave.crypto.applet.update.nfc.commands

import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

class EmptyAPDUCommand :
    CommandApdu(0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte()) {
}