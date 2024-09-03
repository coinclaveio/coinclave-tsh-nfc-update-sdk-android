package io.coinclave.crypto.applet.update.nfc.handlers

import io.coinclave.crypto.applet.update.StangeHexString
import io.coinclave.crypto.applet.update.exceptions.ExchangeNfcException
import io.coinclave.crypto.applet.update.network.client.AppletApi
import io.coinclave.crypto.applet.update.network.models.APDUCommandResponse
import io.coinclave.crypto.applet.update.network.models.CheckAppletsVersionRequest
import io.coinclave.crypto.applet.update.network.models.StartCheckAppletsVersionRequest
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.CheckAppletVersionCommand
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu
import java.util.Collections
import java.util.concurrent.FutureTask

const val SESSION_ID = "sessionId"

class CheckAppletVersionCommandHandler : BaseNFCCommandHandler<CheckAppletVersionCommand>() {

    private var appletApi: AppletApi = AppletApi()

    override fun getAdditionalCommands(command: CheckAppletVersionCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        val initCheckAppletVersionFuture = FutureTask {
            val lastCommand = command.context.getLastRequest()
            appletApi.processCheckAppletVersion(CheckAppletsVersionRequest()
                .sessionId(command.context.getAbstractValue(SESSION_ID).toString())
                .addCommandResponsesItem(APDUCommandResponse()
                    .command(lastCommand.commandString)
                    .response(StangeHexString.hexify(command.context.getValueFromStep(lastCommand)!!.responseBytes))
                )
            )
        }
        Thread(initCheckAppletVersionFuture).start()
        val response = initCheckAppletVersionFuture.get() ?: throw ExchangeNfcException(
            "Fail process check applet version exchange",
            command,
        )
        if (response.apduCommands == null || response.apduCommands.isEmpty()) {
            command.context.result = response.result
            return null
        }
        return response.apduCommands
            .map { StangeHexString.parseHexString(it.command) }
            .map { CommandApdu(it) }
    }

    override fun getHandledClass(): Class<CheckAppletVersionCommand> {
        return CheckAppletVersionCommand::class.java
    }

    override fun needGetAdditionalCommands(action: BaseNFCExchangeAction): Boolean {
        if (action.action == BaseNFCExchangeAction.Action.PROCESS_CHECK_APPLET_VERSION) {
            return true
        }
        return false
    }

    override fun compileRequest(command: CheckAppletVersionCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        if (action.action == BaseNFCExchangeAction.Action.INIT_CHECK_APPLET_VERSION) {
            val initCheckAppletVersionFuture = FutureTask {
                appletApi.initCheckAppletVersion(StartCheckAppletsVersionRequest().appletName("WALLET_OS"))
            }
            Thread(initCheckAppletVersionFuture).start()
            val response = initCheckAppletVersionFuture.get() ?: throw ExchangeNfcException(
                "Fail init check applet version exchange",
                command,
            )
            command.context.putAbstractValue(SESSION_ID, response.sessionId)
            return response.apduCommands
                .map { StangeHexString.parseHexString(it.command) }
                .map { CommandApdu(it) }
        }
        if (action.action == BaseNFCExchangeAction.Action.PROCESS_CHECK_APPLET_VERSION) {
            val initCheckAppletVersionFuture = FutureTask {
                val lastCommand = command.context.getLastRequest()
                appletApi.processCheckAppletVersion(CheckAppletsVersionRequest()
                    .sessionId(command.context.getAbstractValue(SESSION_ID).toString())
                    .addCommandResponsesItem(APDUCommandResponse()
                        .command(lastCommand.commandString)
                        .response(StangeHexString.hexify(command.context.getValueFromStep(lastCommand)!!.responseBytes))
                    )
                )
            }
            Thread(initCheckAppletVersionFuture).start()
            val response = initCheckAppletVersionFuture.get() ?: throw ExchangeNfcException(
                "Fail process check applet version exchange",
                command,
            )
            return response.apduCommands
                .map { StangeHexString.parseHexString(it.command) }
                .map { CommandApdu(it) }
        }
        return Collections.emptyList()
    }

}