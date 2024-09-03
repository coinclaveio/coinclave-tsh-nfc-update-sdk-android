package io.coinclave.crypto.applet.update.nfc.handlers;


import java.util.Arrays;
import java.util.List;

import io.coinclave.crypto.applet.update.nfc.commands.AbstractNFCCommand;

public class NfcCommandHandlersStorage {

    private final List<? extends NFCCommandHandler> handlers;

    public NfcCommandHandlersStorage() {
        handlers = Arrays.asList(
                new StaticNFCCommandHandler(),
                new EmptyNFCCommandHandler(),
                new CheckAppletVersionCommandHandler(),
                new UpdateAppletCommandHandler()
        );
    }

    public List<? extends NFCCommandHandler> getHandlers() {
        return handlers;
    }

}
