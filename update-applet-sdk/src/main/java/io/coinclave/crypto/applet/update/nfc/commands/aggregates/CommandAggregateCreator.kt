package io.coinclave.crypto.applet.update.nfc.commands.aggregates

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.CheckAppletVersionCommand
import io.coinclave.crypto.applet.update.nfc.commands.DynamicNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.UpdateAppletCommand

class CommandAggregateCreator {

    fun createCheckAppletVersionCommandsAggregate(): AbstractNFCCommand {
        return CheckAppletVersionCommand(
            listOf(
                DynamicNFCExchangeAction(
                    BaseNFCExchangeAction.Action.INIT_CHECK_APPLET_VERSION
                ),
                DynamicNFCExchangeAction(
                    BaseNFCExchangeAction.Action.PROCESS_CHECK_APPLET_VERSION
                )
            )
        )
    }

    fun createUpdateAppletCommandsAggregate(
        oldVersion: String,
        newVersion: String
    ): AbstractNFCCommand {
        return UpdateAppletCommand(
            oldVersion, newVersion, listOf(
                DynamicNFCExchangeAction(
                    BaseNFCExchangeAction.Action.INIT_UPDATE_APPLET_VERSION
                ),
                DynamicNFCExchangeAction(
                    BaseNFCExchangeAction.Action.PROCESS_UPDATE_APPLET_VERSION
                )
            )
        )
    }

}