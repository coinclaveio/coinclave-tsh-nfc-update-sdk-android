package io.coinclave.crypto.applet.update.nfc.handlers.aggregates

import io.coinclave.crypto.applet.update.dto.Error
import io.coinclave.crypto.applet.update.network.client.AppletApi
import io.coinclave.crypto.applet.update.network.models.CheckAppletVersionData
import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu
import java.io.Serializable

class CheckAppletVersionCommandsAggregateHandler :
    AggregateHandler {

    private var processErrors = ArrayList<Error>()
    private var appletApi: AppletApi = AppletApi()
    private lateinit var result: CheckAppletVersionData

    override fun handleResult(response: ResponseApdu?): Serializable {
        return result
    }

    override fun handleIntermediateResult(
        command: AbstractNFCCommand,
        action: BaseNFCExchangeAction
    ): Boolean {
        when (action.action) {
            BaseNFCExchangeAction.Action.PROCESS_CHECK_APPLET_VERSION -> {
                if (command.context.result != null) {
                    result = command.context.result as CheckAppletVersionData
                }
            }
            BaseNFCExchangeAction.Action.INIT_CHECK_APPLET_VERSION -> {

            }

            else -> {}
        }
        return true
    }

}