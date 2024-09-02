package io.coinclave.crypto.applet.update.nfc.commands

import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

class RawAPDUCommand(val command: ByteArray) : CommandApdu(command)