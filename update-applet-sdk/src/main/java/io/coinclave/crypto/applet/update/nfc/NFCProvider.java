package io.coinclave.crypto.applet.update.nfc;

import android.nfc.TagLostException;
import android.nfc.tech.IsoDep;
import android.util.Log;

import io.coinclave.crypto.applet.update.nfc.iso7816.ByteUtility;

import java.io.IOException;

public class NFCProvider implements CardCommunicationProvider {

    private final String TAG = "NfcProvider";

    private final NFCManager mNFCManager;
    private IsoDep mIsoDep;
    private boolean isCardTapped;


    private boolean nFCEnabled;

    /**
     * Command execution time in nano seconds
     */
    private long mCommandExecutionTime = 0;

    public NFCProvider(NFCManager nfcManager) {
        mNFCManager = nfcManager;
        // Check if NFC is enabled
        if (!mNFCManager.isNFCEnabled()) {
            nFCEnabled = false;
        } else {
            nFCEnabled = true;
        }
        disconnectReader();
    }

    @Override
    public byte[] sendReceive(byte[] bytes) throws L1RSPException {
        Log.d("BYTE", ByteUtility.byteArrayToHexString(bytes));
        byte[] response;
        try {
            if (mIsoDep.isConnected()) {
                long startTime = System.nanoTime();
                response = mIsoDep.transceive(bytes);
                long endTime = System.nanoTime();
                // Command execution time in nano seconds
                mCommandExecutionTime = endTime - startTime;
            } else {
               Log.e(TAG, "sendReceive: ISO DEP obtained as null");
                isCardTapped = false;
                throw new L1RSPException("TRANSMISSION_ERROR");
            }
        } catch (TagLostException exception) {
            Log.e(TAG, "TagLostException", exception.getCause());
            isCardTapped = false;
            throw new L1RSPException("Tag Lost - TIMEOUT_ERROR");

        } catch (IOException e) {
            isCardTapped = false;
            throw new L1RSPException(e.getMessage());

        }
        if (response.length < 2) {
            throw new L1RSPException("Response Length less than 2 bytes");
        }
        Log.d("BYTE", ByteUtility.byteArrayToHexString(response));
        return response;
    }

    @Override
    public boolean connectCard() throws L1RSPException {

        if (mIsoDep != null) {
            Log.i(TAG, "connectCard: Card Tapped");
            try {
                mIsoDep.connect();
                mIsoDep.setTimeout(100000);
                isCardTapped = true;
                return true;
            } catch (IOException | IllegalStateException e) {
                throw new L1RSPException("Exception encountered on IsoDep.connect()");
            }
        }
        return isCardTapped;
    }

    @Override
    public boolean removeCard() {

        if (mIsoDep != null && mIsoDep.isConnected()) {
            try {
                mIsoDep.close();
                isCardTapped = false;
                mIsoDep = null;
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            Log.w(TAG, "removeCard: IsoDep is null or disconnected");
            return true;
        }
    }

    @Override
    public boolean connectReader(IsoDep isoDep) throws L1RSPException {
        this.mIsoDep = isoDep;
        isCardTapped = false;
        return true;
    }

    @Override
    public boolean disconnectReader() {
        try {
            mIsoDep.close();
        } catch (IOException e) {
            return false;
        } catch (NullPointerException npe) {
            return true;
        }

        mNFCManager.disableNFCReaderMode();

        mIsoDep = null;
        isCardTapped = false;
        return true;
    }

    @Override
    public boolean isReaderConnected() {
        return true;
    }

    @Override
    public boolean isCardPresent() {
        return mIsoDep.isConnected();
    }

    @Override
    public String getDescription() {
        return "Built-in NFC Controller";
    }

    @Override
    public InterfaceType getInterfaceType() {
        return InterfaceType.CONTACTLESS;
    }

    @Override
    public int getPreviousCommandExecutionTime() {
        // return command execution time in microseconds
        return (int) (mCommandExecutionTime / 1000);
    }

    public boolean isNfcEnabled() {
        return mNFCManager.isNFCEnabled();
    }

}
