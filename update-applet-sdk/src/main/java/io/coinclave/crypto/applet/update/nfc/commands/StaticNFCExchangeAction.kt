package io.coinclave.crypto.applet.update.nfc.commands

import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu

class StaticNFCExchangeAction(action: Action, staticRequest: CommandApdu): BaseNFCExchangeAction(action) {

    init {
        super.requests = listOf(staticRequest)
    }

}