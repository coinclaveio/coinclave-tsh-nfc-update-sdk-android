package io.coinclave.crypto.applet.update.nfc.iso7816;

import java.util.Arrays;

public final class ResponseApdu {
    private byte[] mResponseBytes;
    private byte[] mResponseData;
    private byte[] mStatusWord;
    private boolean mIsSuccessResponse;

    public ResponseApdu(byte[] responseBytes) {
        if (responseBytes != null && responseBytes.length >= 2) {
            this.mResponseBytes = responseBytes;
            if (responseBytes.length > 2) {
                this.mResponseData = new byte[responseBytes.length - 2];
                System.arraycopy(responseBytes, 0, this.mResponseData, 0, responseBytes.length - 2);
            } else {
                this.mResponseData = new byte[0];
            }

            this.mStatusWord = new byte[]{responseBytes[responseBytes.length - 2], responseBytes[responseBytes.length - 1]};
            if ((this.mStatusWord[0] & 255) == 144 && this.mStatusWord[1] == 0) {
                this.mIsSuccessResponse = true;
            }
        } else {
            String respString = ByteUtility.byteArrayToHexString(responseBytes);
            this.mIsSuccessResponse = false;
            this.mResponseData = new byte[0];
            this.mStatusWord = new byte[2];
            this.mResponseBytes = new byte[2];
        }

    }

    public final byte[] getResponseBytes() {
        return this.mResponseBytes;
    }

    public final byte[] getResponseData() {
        return this.mResponseData;
    }

    public final short getStatusWord() {
        return ByteUtility.byteArrayToShort(this.mStatusWord);
    }

    public final byte[] getStatusWordBytes() {
        return this.mStatusWord;
    }

    public final boolean isSuccess() {
        return this.mIsSuccessResponse;
    }

    public final boolean isEqual(byte[] data){
        return Arrays.equals(mResponseData, data);
    }
}
