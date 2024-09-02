package io.coinclave.crypto.applet.update.nfc;


import android.nfc.tech.IsoDep;

public interface CardCommunicationProvider {
    byte[] sendReceive(byte[] var1) throws L1RSPException;

    boolean connectCard() throws L1RSPException;

    boolean removeCard();

    boolean connectReader(IsoDep IsoDep) throws L1RSPException;

    boolean disconnectReader();

    boolean isReaderConnected();

    boolean isCardPresent();

    String getDescription();

    InterfaceType getInterfaceType();

    int getPreviousCommandExecutionTime();

    public static enum InterfaceType {
        MAGSTRIPE,
        CONTACT,
        CONTACTLESS;

        private InterfaceType() {
        }
    }
}
