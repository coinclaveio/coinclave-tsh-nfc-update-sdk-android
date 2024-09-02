
package io.coinclave.crypto.applet.update;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Implements Global Platform ByteString class
 *
 * @author Andreas Schwier (info@cardcontact.de)
 */
public class ByteString {

    // This must match the definitions in the global object
    public static final int HEX = 16;           // to match Number.toHexString(16) method

    public static final int UTF8 = 2;

    public static final int ASCII = 3;

    public static final int BASE64 = 4;

    public static final int CN = 5;

    public static final int OID = 6;

    public static final int XOR = 1;

    public static final int OR = 2;

    public static final int AND = 3;

    private byte[] bs = null;           // Buffer for data


    /**
     * Implements GP ByteString constructor
     * <p/>
     * ByteString new ByteString(String stringValue, Number encoding)
     */
    public ByteString(byte[] data) {
        bs = Arrays.copyOf(data, data.length);
    }

    public ByteString(String data) throws GPException {
        this(data, HEX);
    }


    public ByteString(String data, int encoding) throws GPException {
        switch (encoding) {
            case HEX:

                try {
                    bs = TlvHexString.parseHexString(data);
                } catch (NumberFormatException e) {
                    throw new GPException("String contains invalid hexadecimal data: ", GPException.INVALID_DATA);
                }
                break;

            case UTF8:
                try {
                    bs = data.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new GPException("String contains invalid UTF-8 characters", GPException.INVALID_DATA);
                }
                break;

            case ASCII:
                try {
                    bs = data.getBytes("8859_1");
                } catch (UnsupportedEncodingException e) {
                    throw new GPException("String contains invalid Latin-1 characters", GPException.INVALID_DATA);
                }
                break;
            default:
                throw new GPException("Invalid encoding type", GPException.INVALID_ENCODING);
        }

    }

    /**
     * Return length of ByteBuffer
     *
     * @return length
     */
    public int getLength() {
        return bs.length;
    }

    /**
     * Return bytes contained
     *
     * @return Array of bytes
     */
    public byte[] getBytes() {
        return bs;
    }

    /**
     * Implements ByteString and(ByteString)
     */
    public ByteString and(ByteString inputStr) throws GPException {
        return bitwiseOperation(inputStr, AND);
    }

    /**
     * Implements ByteString or(ByteString)
     */
    public ByteString or(ByteString inputStr) throws GPException {
        return bitwiseOperation(inputStr, OR);
    }

    /**
     * Implements ByteString xor(ByteString)
     */
    public ByteString xor(ByteString inputStr) throws GPException {
        return bitwiseOperation(inputStr, XOR);
    }

    /**
     * Implements Number byteAt(Number offset)
     */
    public double byteAt(int offset) throws GPException {

        byte[] o = this.bs;

        if ((offset < 0) || (offset >= o.length)) {
            throw new GPException("Offset for byteAt() out of range", GPException.INVALID_INDEX);
        }

        return (double) (o[offset] & 0xFF);
    }

    /**
     * Implements ByteString bytes(Number offset)
     * ByteString bytes(Number offset, Number count)
     */
    public ByteString bytes(int offset, int count) throws GPException {

        byte[] b = this.bs;

        if ((offset < 0) || (offset > b.length)) {
            throw new GPException("Offset for bytes() out of range", GPException.INVALID_INDEX);

        }
        if ((count < 0) || (offset + count > b.length)) {
            throw new GPException("Count for bytes() out of range", GPException.INVALID_LENGTH);
        }

        byte[] result = new byte[count];
        System.arraycopy(b, offset, result, 0, count);

        return new ByteString(result);
    }

    /**
     * Implements ByteString concat(ByteString value)
     */
    public ByteString concat(ByteString inputStr) throws GPException {


        if (inputStr == null) {
            GPException.throwAsGPErrorEx(GPException.INVALID_TYPE, 1, "Argument for concat() must be ByteString");
        }

        byte[] b = this.bs;
        byte[] v = inputStr.bs;

        byte[] result = new byte[b.length + v.length];
        System.arraycopy(b, 0, result, 0, b.length);
        System.arraycopy(v, 0, result, b.length, v.length);

        return new ByteString(result);
    }

    public String toHexString() {
        return TlvHexString.hexifyByteArray(bs);
    }

    @Override
    public String toString() {
        return toHexString();
    }

    public String toPlainString(String charset) {
        try {
            return new String(bs, charset);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * Perform bitwise AND, OR or XOR on this object and parameter
     *
     * @return ByteString object with result
     */
    private ByteString bitwiseOperation(ByteString inputStr, int op) throws GPException {

        byte[] a = inputStr.bs;
        byte[] o = this.bs;

        if (a.length != o.length) {
            throw new GPException("Object and argument have different length", GPException.INVALID_LENGTH);
        }

        byte[] result = new byte[o.length];

        switch (op) {
            case OR:
                for (int i = 0; i < result.length; i++) {
                    result[i] = (byte) (o[i] | a[i]);
                }
                break;
            case AND:
                for (int i = 0; i < result.length; i++) {
                    result[i] = (byte) (o[i] & a[i]);
                }
                break;
            case XOR:
                for (int i = 0; i < result.length; i++) {
                    result[i] = (byte) (o[i] ^ a[i]);
                }
                break;
            default:
                throw new GPException("Invalid bitwise type", GPException.INVALID_ENCODING);
        }

        return new ByteString(result);
    }

}
