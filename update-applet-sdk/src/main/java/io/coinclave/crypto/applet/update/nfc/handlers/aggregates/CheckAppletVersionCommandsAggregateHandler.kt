package io.coinclave.crypto.applet.update.nfc.handlers.aggregates

import io.coinclave.crypto.applet.update.StangeHexString
import io.coinclave.crypto.applet.update.dto.ActivateCardResult
import io.coinclave.crypto.applet.update.dto.Error
import io.coinclave.crypto.applet.update.exceptions.ExchangeNfcException
import io.coinclave.crypto.applet.update.network.client.AppletApi
import io.coinclave.crypto.applet.update.network.invoker.ApiException
import io.coinclave.crypto.applet.update.network.models.DecodeCardDataRequest
import io.coinclave.crypto.applet.update.network.models.Token
import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu
import java.io.Serializable
import java.util.concurrent.FutureTask

class CheckAppletVersionCommandsAggregateHandler :
    AggregateHandler {

    private var processErrors = ArrayList<Error>()
    private var appletApi: AppletApi = AppletApi()
    private lateinit var result: ActivateCardResult

    override fun handleResult(response: ResponseApdu?): Serializable {
        return result
    }

    override fun handleIntermediateResult(
        command: AbstractNFCCommand,
        action: BaseNFCExchangeAction
    ): Boolean {
        when (action.action) {
            BaseNFCExchangeAction.Action.PROCESS_CHECK_APPLET_VERSION -> {

            }
            BaseNFCExchangeAction.Action.INIT_CHECK_APPLET_VERSION -> {
//                if (action.responses == null) {
//                    return false
//                }
//                val tokenFuture = FutureTask {
//                    authApi.accessToken
//                }
//                val token: Token
//                try {
//                    Thread(tokenFuture).start()
//                    token = tokenFuture.get() ?: throw ExchangeNfcException(
//                        "Authorization not received",
//                        command,
//                    )
//                    command.context.putAbstractValue("token", token)
//                } catch (e: Exception) {
//                    throw ExchangeNfcException(
//                        "Decoded card data not received",
//                        command,
//                    )
//                }
//
//                val cardFuture = FutureTask {
//                    activationApi.decodeCardData(DecodeCardDataRequest().apply {
//                        data = StangeHexString.hexify(action.response!!)
//                    }, token.accessToken)
//                }
//                try {
//                    Thread(cardFuture).start()
//                    val card = cardFuture.get() ?: throw ExchangeNfcException(
//                        "Decoded card data not received",
//                        command,
//                    )
////                    val card = Card().apply {
////                        pan = "9876543210987654"
////                        expirationDate = ExpirationDate().apply {
////                            month = "07"
////                            year = "85"
////                        }
////                    }
//                    command.context.putAbstractValue("card", card)
//                    result = ActivateCardResult(card.pan, card.expirationDate.month + "/" + card.expirationDate.year)
//                } catch (e: Exception) {
//                    if (e is ApiException) {
//                        throw ExchangeNfcException(
//                            "Receive error from server: " + e.code + " " + e.message,
//                            command,
//                        )
//                    } else if (e.cause is ApiException) {
//                        val apiException : ApiException = e.cause as ApiException
//                        throw ExchangeNfcException(
//                            "Receive error from server: " + apiException.code + " " + apiException.message,
//                            command,
//                        )
//                    }
//                    throw ExchangeNfcException(
//                        "Decoded card data not received",
//                        command,
//                    )
//                }
            }

            else -> {}
        }
        return true
    }

}