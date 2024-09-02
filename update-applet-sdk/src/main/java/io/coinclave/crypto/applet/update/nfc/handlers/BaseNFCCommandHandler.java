package io.coinclave.crypto.applet.update.nfc.handlers;

import androidx.annotation.NonNull;

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand;
import io.coinclave.crypto.applet.update.nfc.commands.BaseNFCExchangeAction;
import io.coinclave.crypto.applet.update.nfc.commands.DynamicNFCExchangeAction;
import io.coinclave.crypto.applet.update.nfc.iso7816.ResponseApdu;

import java.util.List;

public abstract class BaseNFCCommandHandler<I extends AbstractNFCCommand> implements NFCCommandHandler<I> {

    @Override
    public void handle(@NonNull I command, @NonNull NFCCommandHandlerListener listener) {
        try {
            listener.prepare();
            List<BaseNFCExchangeAction> actions = command.getActions();
            for (BaseNFCExchangeAction action : actions) {
                command.getContext().incrementStepIndex();

                boolean isDynamicAction = action instanceof DynamicNFCExchangeAction;
                if (isDynamicAction) {
                    action.setRequests(compileRequest(command, action));
                }

                if (action.getRequests() == null || action.getRequests().isEmpty()) {
                    throw new RuntimeException("Requests for command action is null. Action: " + action.getAction().name());
                }
                for (var request : action.getRequests()) {
                    ResponseApdu intermediateResult = listener.send(request);
                    if (intermediateResult != null) {
                        command.getContext().putValueFromStep(request, intermediateResult);
                        action.putValueFromStep(request, intermediateResult.getResponseBytes());
                    }
                }

                if (isDynamicAction) {
                    if (needGetAdditionalCommands(action)) {
                        var additionalCommands = getAdditionalCommands(action);
                        while(additionalCommands != null && !additionalCommands.isEmpty()) {
                            for (var request : action.getRequests()) {
                                ResponseApdu intermediateResult = listener.send(request);
                                if (intermediateResult != null) {
                                    command.getContext().putValueFromStep(request, intermediateResult);
                                    action.putValueFromStep(request, intermediateResult.getResponseBytes());
                                }
                            }
                        }
                    }
                }

                if (listener.handleIntermediateResult(command, action) == NFCCommandHandlerListener.ResultCode.NEED_INTERRUPT) {
                    break;
                }
            }
            BaseNFCExchangeAction lastAction = actions.get(actions.size() - 1);
            listener.handleResult(command.getContext().getValueFromStep(lastAction.getLastRequest()));
        } catch (Exception e) {
            listener.handleException(e);
        } finally {
            listener.destroy();
        }
    }

}
