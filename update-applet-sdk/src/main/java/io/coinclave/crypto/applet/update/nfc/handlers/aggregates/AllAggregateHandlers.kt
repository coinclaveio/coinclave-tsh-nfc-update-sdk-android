package io.coinclave.crypto.applet.update.nfc.handlers.aggregates

import io.coinclave.crypto.applet.update.CommandDictionaryKit
import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.CheckAppletVersionCommandsAggregate
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.CommandAggregate
import io.coinclave.crypto.applet.update.nfc.commands.aggregates.UpdateAppletCommandsAggregate
import java.io.Serializable

class AllAggregateHandlers {

    private val handlers: MutableMap<Class<out CommandAggregate>, AggregateHandler>

    fun handleResult(response: ResponseApdu?, commandAggregate: CommandAggregate): Serializable {
        checkHandlerExists(commandAggregate)
        return handlers[commandAggregate.javaClass]!!.handleResult(response)
    }

    fun handleIntermediateResult(
        action: BaseNFCExchangeAction,
        command: AbstractNFCCommand,
        commandAggregate: CommandAggregate
    ): Boolean {
        checkHandlerExists(commandAggregate)
        return handlers[commandAggregate.javaClass]!!
            .handleIntermediateResult(command, action)
    }

    private fun checkHandlerExists(aggregate: CommandAggregate) {
        if (!handlers.containsKey(aggregate.javaClass)) {
            throw RuntimeException("Not found handler for aggregate " + aggregate.javaClass)
        }
    }

    init {
        handlers = HashMap()
        handlers[CheckAppletVersionCommandsAggregate::class.java] =
            CheckAppletVersionCommandsAggregateHandler()
        handlers[UpdateAppletCommandsAggregate::class.java] =
            UpdateAppletCommandsAggregateHandler()
    }
}