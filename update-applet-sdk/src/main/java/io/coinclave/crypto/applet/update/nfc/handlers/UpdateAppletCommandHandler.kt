package io.coinclave.crypto.applet.update.nfc.handlers

import io.coinclave.crypto.applet.update.StangeHexString
import io.coinclave.crypto.applet.update.dto.BlankResult
import io.coinclave.crypto.applet.update.exceptions.ExchangeNfcException
import io.coinclave.crypto.applet.update.network.client.CardApi
import io.coinclave.crypto.applet.update.network.models.APDUCommandResponse
import io.coinclave.crypto.applet.update.network.models.UpdateCardProgressRequest
import io.coinclave.crypto.applet.update.network.models.UpdateCardRequest
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.commands.UpdateAppletCommand
import io.coinclave.crypto.applet.update.nfc.iso7816.CommandApdu
import java.util.Collections
import java.util.UUID
import java.util.concurrent.FutureTask

const val CARD_ID = "cardId"

class UpdateAppletCommandHandler : BaseNFCCommandHandler<UpdateAppletCommand>() {

    private var cardApi: CardApi = CardApi()

    override fun getAdditionalCommands(command: UpdateAppletCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        val patchUpdateFuture = FutureTask {
            val lastCommand = command.context.getLastRequest()
            cardApi.patchUpdateCard(
                UpdateCardProgressRequest()
                    .cardId(command.context.getAbstractValue(CARD_ID).toString())
                    .addCommandResponsesItem(
                        APDUCommandResponse()
                            .command(lastCommand.commandString)
                            .response(
                                StangeHexString.hexify(
                                    command.context.getValueFromStep(
                                        lastCommand
                                    )!!.responseBytes
                                )
                            )
                    )
            )
        }
        Thread(patchUpdateFuture).start()
        val response = patchUpdateFuture.get() ?: throw ExchangeNfcException(
            "Fail process check applet version exchange",
            command,
        )
        if (response == null || response.isEmpty()) {
            command.context.result = BlankResult(null)
            return null
        }
        return response
            .map { StangeHexString.parseHexString(it.command) }
            .map { CommandApdu(it) }
    }

    override fun getHandledClass(): Class<UpdateAppletCommand> {
        return UpdateAppletCommand::class.java
    }

    override fun needGetAdditionalCommands(action: BaseNFCExchangeAction): Boolean {
        if (action.action == BaseNFCExchangeAction.Action.PROCESS_UPDATE_APPLET_VERSION) {
            return true
        }
        return false
    }

    override fun compileRequest(command: UpdateAppletCommand, action: BaseNFCExchangeAction): List<CommandApdu>? {
        if (action.action == BaseNFCExchangeAction.Action.INIT_UPDATE_APPLET_VERSION) {
            val cardId = UUID.randomUUID().toString()
            val initUpdateAppletFuture = FutureTask {
                cardApi.updateCard(UpdateCardRequest()
                    .cardId(cardId)
                    .currentAppletVersion(command.oldVersion)
                    .newAppletVersion(command.newVersion))
            }
            Thread(initUpdateAppletFuture).start()
            val response = initUpdateAppletFuture.get() ?: throw ExchangeNfcException(
                "Fail init check applet version exchange",
                command,
            )
            command.context.putAbstractValue(CARD_ID, cardId)
            return response
                .map { StangeHexString.parseHexString(it.command) }
                .map { CommandApdu(it) }
        }
        if (action.action == BaseNFCExchangeAction.Action.PROCESS_UPDATE_APPLET_VERSION) {
            val initCheckAppletVersionFuture = FutureTask {
                val lastCommand = command.context.getLastRequest()
                cardApi.patchUpdateCard(
                    UpdateCardProgressRequest()
                        .cardId(command.context.getAbstractValue(CARD_ID).toString())
                        .addCommandResponsesItem(
                            APDUCommandResponse()
                                .command(lastCommand.commandString)
                                .response(
                                    StangeHexString.hexify(
                                        command.context.getValueFromStep(
                                            lastCommand
                                        )!!.responseBytes
                                    )
                                )
                        )
                )
            }
            Thread(initCheckAppletVersionFuture).start()
            val response = initCheckAppletVersionFuture.get() ?: throw ExchangeNfcException(
                "Fail process check applet version exchange",
                command,
            )
            return response
                .map { StangeHexString.parseHexString(it.command) }
                .map { CommandApdu(it) }
        }
        return Collections.emptyList()
    }

}