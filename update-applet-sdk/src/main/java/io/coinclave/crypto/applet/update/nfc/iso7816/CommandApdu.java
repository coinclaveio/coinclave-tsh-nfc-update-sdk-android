package io.coinclave.crypto.applet.update.nfc.iso7816;

import java.io.Serializable;
import java.util.Arrays;

public class CommandApdu implements Iso7816Constants, Serializable {
    private final int HEADER_LENGTH = 4;
    private String mCmdName = "";
    private byte[] mHeaderBytes;
    private byte mLc;
    private byte[] mCmdData;
    private byte mLe;
    private boolean bIsLePresent = false;

    public CommandApdu(byte cla, byte ins, byte p1, byte p2) {
        this.setHeaderBytes(cla, ins, p1, p2);
        this.mCmdData = new byte[0];
    }

    public CommandApdu(byte cla, byte ins, byte p1, byte p2, byte le) {
        this.setHeaderBytes(cla, ins, p1, p2);
        this.mLe = le;
        this.bIsLePresent = true;
    }

    public CommandApdu(byte[] data) {
        if (data == null || data.length == 0) {
            throw new RuntimeException("Empty array for CommandApdu constructor");
        }
        this.setHeaderBytes(data[0], data[1], data[2], data[3]);
        if (data.length == 4) {
            this.mCmdData = new byte[0];
            return;
        }

        if (data[4] != (data.length - 5)) {
            this.mLe = data[data.length - 1];
            this.bIsLePresent = true;
        }

        this.setLcAndCommandData(Arrays.copyOfRange(data, 5, bIsLePresent ? data.length - 1 : data.length));
    }

    public CommandApdu(byte cla, byte ins, byte p1, byte p2, byte[] data) {
        this.setHeaderBytes(cla, ins, p1, p2);
        this.setLcAndCommandData(data);
    }

    public CommandApdu(byte cla, byte ins, byte p1, byte p2, byte[] data, byte le) {
        this.setHeaderBytes(cla, ins, p1, p2);
        this.setLcAndCommandData(data);
        this.mLe = le;
        this.bIsLePresent = true;
    }

    private void setLcAndCommandData(byte[] data) {
        this.mLc = (byte) data.length;
        this.mCmdData = data;
    }

    public final String getCmdName() {
        return this.mCmdName.isEmpty() ? ByteUtility.byteArrayToHexString(this.mHeaderBytes) : this.mCmdName;
    }

    public final void setCmdName(String mCmdName) {
        if (mCmdName != null) {
            this.mCmdName = mCmdName;
        }

    }

    private final void setHeaderBytes(byte cla, byte ins, byte p1, byte p2) {
        this.mHeaderBytes = new byte[4];
        this.mHeaderBytes[0] = cla;
        this.mHeaderBytes[1] = ins;
        this.mHeaderBytes[2] = p1;
        this.mHeaderBytes[3] = p2;
    }

    public byte[] getCommandBytes() {
        int commandLength = 4;
        if (this.mCmdData != null) {
            commandLength += this.mCmdData.length + 1;
        }

        if (this.bIsLePresent) {
            ++commandLength;
        }

        byte[] commandBytes = new byte[commandLength];
        System.arraycopy(this.mHeaderBytes, 0, commandBytes, 0, 4);
        if (this.mCmdData != null) {
            commandBytes[4] = this.mLc;
            System.arraycopy(this.mCmdData, 0, commandBytes, 5, this.mLc & 255);
        }

        if (this.bIsLePresent) {
            commandBytes[commandLength - 1] = this.mLe;
        }

        return commandBytes;
    }

    public final byte[] getCommandData() {
        return this.mCmdData;
    }

    public final String getCommandString() {
        return ByteUtility.byteArrayToHexString(this.getCommandBytes());
    }

    public final String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("= Command APDU =\n");
        sb.append("- Name : " + this.mCmdName + "\n");
        return sb.toString();
    }
}
