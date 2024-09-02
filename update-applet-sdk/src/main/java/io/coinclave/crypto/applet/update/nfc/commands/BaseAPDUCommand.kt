package io.coinclave.crypto.applet.update.nfc.commands

import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

class BaseAPDUCommand(command: ByteArray) : CommandApdu(command)