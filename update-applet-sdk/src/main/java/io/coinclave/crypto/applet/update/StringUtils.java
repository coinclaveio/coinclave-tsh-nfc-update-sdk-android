package io.coinclave.crypto.applet.update;

import java.nio.ByteBuffer;

/**
 *  Supporting utilities for Strings
 */
public class StringUtils {

    /**
     * Hex-dump a byte array
     *
     * @param data Byte array
     *
     * @return Dump
     */
    public static String dump(byte[] data) {
        if (data == null) {
            return "";
        }
        return dump(data, 0, data.length);
    }

    /**
     * Hex-dump a ByteBuffer
     *
     * @param byteBuffer ByteBuffer
     *
     * @return Dump
     */
    public static String dump(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return "null";
        }
        ByteBuffer buffer = (ByteBuffer) byteBuffer.slice();

        StringBuilder out = new StringBuilder(buffer.remaining() * 3);
        StringBuilder ascii = new StringBuilder(16);

        int offset = 0;
        while (buffer.hasRemaining()) {
            out.append(String.format("%08X:  ", offset));
            int len = Math.min(16, buffer.remaining());
            for (int j = 0; j < len; j++) {
                int b = buffer.get() & 0xff;
                out.append(String.format("%02X ", b));
                ascii.append(Character.isISOControl(b) ? '.' : (char) b);
            }
            for (int j = len; j < 16; j++) {
                out.append("   ");
                ascii.append(' ');
            }
            out.append(' ').append(ascii).append("\n");
            ascii.setLength(0);
            offset += 16;
        }
        return out.toString();
    }

    /**
     * Hex-dump a byte array from offset and length
     *
     * @param data Byte array to convert to StringUtils
     * @param offset Start dump here
     * @param len Number of bytes to be dumped.
     * @return the Dump
     */
    public static String dump(byte[] data, int offset, int len) {
        return dump(ByteBuffer.wrap(data, offset, len));
    }

    public static String toHexString(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return "null";
        }
        ByteBuffer buffer = (ByteBuffer) byteBuffer.slice();
        StringBuilder out = new StringBuilder(buffer.remaining());

        while (buffer.hasRemaining()) {
            int b = buffer.get() & 0xff;
            out.append(String.format("%02X", b));
        }
        return out.toString();
    }

    public static String toHexString(byte[] data, int offset, int len) {
        return toHexString(ByteBuffer.wrap(data, offset, len));
    }

    public static String toHexString(byte[] data) {
        return toHexString(ByteBuffer.wrap(data, 0, data.length));
    }


    /**
     * Parse bytes encoded as Hexadecimals into a byte array.<p>
     *
     * @param str String containing HexBytes.
     *
     * @return byte array containing the parsed values of the given string.
     */
    public static byte[] fromHexString(String str) {

        int ofs = 0;
        int len = str.length();
        if ((len % 2) != 0) {
            throw new NumberFormatException("Odd number of digits");
        }
        if (str.startsWith("0x")) {
            ofs += 2;
            len -= 2;
        }
        byte[] data = new byte[len / 2];
        for (; ofs < len; ofs += 2) {
            int dH = Character.digit(str.charAt(ofs), 16);
            int dL = Character.digit(str.charAt(ofs + 1), 16);
            if ((dH < 0) || (dL < 0)) {
                throw new NumberFormatException("Illegal digit");
            }
            data[ofs / 2] = (byte) ((dH << 4) + dL);
        }
        return data;
    }

    public static String canonize(byte[] path) {
        if (path == null) {
            return null;
        }
        return canonize(path, 0, path.length);
    }

    public static String canonize(byte byteValue) {
        return canonize(new byte[] {byteValue});
    }

    public static String printInRows(byte[] data, int chunkSize) {
        StringBuilder msg = new StringBuilder();
        char[] str = canonize(data).toCharArray();
        int pos = 0;
        while (pos < str.length) {
            msg.append('[');
            int at = pos / 2;
            if (at < 10) {
                msg.append('0');
            }
            if (at < 100) {
                msg.append('0');
            }
            msg.append(at).append("] ");
            int length = Math.min(chunkSize * 2, str.length - pos);
            msg.append(str, pos, length).append('\n');
            pos += length;
        }
        return msg.toString();
    }

    public static String canonize(byte[] path, int offset, int length) {
        if (path == null) {
            return null;
        }
        StringBuilder retVal = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String e = Integer.toHexString(path[i + offset] & 0xFF);
            if (e.length() == 1) {
                retVal.append('0');
            }
            retVal.append(e);
        }
        return retVal.toString();
    }
}
