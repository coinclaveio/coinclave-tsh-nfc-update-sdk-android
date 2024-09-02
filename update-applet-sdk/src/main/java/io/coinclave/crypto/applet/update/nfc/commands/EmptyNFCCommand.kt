package io.coinclave.crypto.applet.update.nfc.commands

class EmptyNFCCommand : AbstractNFCCommand(
    listOf(
        StaticNFCExchangeAction(
            BaseNFCExchangeAction.Action.EMPTY,
            EmptyAPDUCommand()
        )
    )
)