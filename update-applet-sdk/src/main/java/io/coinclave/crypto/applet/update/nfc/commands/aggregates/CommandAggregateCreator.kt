package io.coinclave.crypto.applet.update.nfc.commands.aggregates

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.CheckAppletVersionCommand
import io.coinclave.crypto.applet.update.nfc.commands.DynamicCommand
import io.coinclave.crypto.applet.update.nfc.commands.DynamicNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.EmptyAPDUCommand
import io.coinclave.crypto.applet.update.nfc.commands.EmptyNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.StaticNFCExchangeAction

class CommandAggregateCreator {

    fun createActivateAppletCommandsAggregate(): AbstractNFCCommand {
        return CheckAppletVersionCommand(listOf(
            DynamicNFCExchangeAction(
                BaseNFCExchangeAction.Action.INIT_CHECK_APPLET_VERSION
            ),
            DynamicNFCExchangeAction(
                BaseNFCExchangeAction.Action.PROCESS_CHECK_APPLET_VERSION
            ))
        )
    }

}