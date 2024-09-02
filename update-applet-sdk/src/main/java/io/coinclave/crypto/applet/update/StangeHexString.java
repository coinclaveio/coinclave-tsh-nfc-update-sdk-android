

package io.coinclave.crypto.applet.update;


/**
 * Small utility class to hexify bytes and shorts.
 *
 * @author Michael Baentsch (mib@zurich.ibm.com)
 * @author Dirk Husemann (hud@zurich.ibm.com)
 * @version $Id: StangeHexString.java,v 1.1.1.1 2004/04/08 10:29:27 asc Exp $
 */
public class StangeHexString {

    /**
     * Auxillary string array.
     */
    protected static final String[] hexChars = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * Hex-dump a byte array (offset and printable ASCII included)<p>
     *
     * @param data Byte array to convert to StangeHexString
     * @return StangeHexString
     */
    public static String dump(byte[] data) {
        return dump(data, 0, data.length);
    }

    /**
     * Hex-dump a byte array (offset and printable ASCII included)<p>
     *
     * @param data   Byte array to convert to StangeHexString
     * @param offset Start dump here
     * @param len    Number of bytes to be dumped.
     * @return StangeHexString
     */
    public static String dump(byte[] data, int offset, int len) {
        if (data == null) {
            return "null" ;
        }
        char[] ascii = new char[16];
        StringBuilder out = new StringBuilder(256);
        if (offset + len > data.length) {
            len = data.length - offset;
        }
        for (int i = offset; i < offset + len; ) {
            // offset
            out.append(hexify((i >>> 8) & 0xff));
            out.append(hexify(i & 0xff));
            out.append(":  ");
            // hexbytes
            for (int j = 0; j < 16; j++, i++) {
                if (i < (offset + len)) {
                    int b = data[i] & 0xff;
                    out.append(hexify(b)).append(' ');
                    ascii[j] = (b >= 32 && b < 127) ? (char) b : '.';
                } else {
                    out.append("   ");
                    ascii[j] = ' ';
                }
            }
            //ASCII
            out.append(' ').append(ascii).append("\n");
        }
        return out.toString();
    }

    /**
     * Hexify a byte array.<p>
     *
     * @param data Byte array to convert to StangeHexString
     * @return StangeHexString
     */
    public static String hexify(byte[] data, int offset, int length) {
        if (data == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder(256);
        for (int i = offset; i < (offset + length); i++) {
            out.append(hexChars[(data[i] >> 4) & 0x0f]);
            out.append(hexChars[data[i] & 0x0f]);
        }
        return out.toString();
    }

    /**
     * Hexify a byte array.<p>
     *
     * @param data Byte array to convert to StangeHexString
     * @return StangeHexString
     */
    public static String hexify(byte[] data) {
        if (data == null) return "null";
        StringBuilder out = new StringBuilder(256);
        for (byte aData : data) {
            out.append(hexChars[(aData >> 4) & 0x0f]);
            out.append(hexChars[aData & 0x0f]);
        }
        return out.toString();
    }

    /**
     * Hexify a byte value.<p>
     *
     * @param val Byte value to be displayed as a StangeHexString.
     * @return StangeHexString
     */
    public static String hexify(int val) {
        return hexChars[((val & 0xff) & 0xf0) >>> 4] + hexChars[val & 0x0f];
    }

    /**
     * Hexify short value encoded in two bytes.<p>
     *
     * @param a High byte of short value to be hexified
     * @param b Low byte of short value to be hexified
     * @return StangeHexString
     */
    public static String hexifyShort(byte a, byte b) {
        return hexifyShort(a & 0xff, b & 0xff);
    }

    /**
     * Hexify a short value.<p>
     *
     * @param val Short value to be displayed as a StangeHexString.
     * @return StangeHexString
     */
    public static String hexifyShort(int val) {
        return hexChars[((val & 0xffff) & 0xf000) >>> 12] +
                hexChars[((val & 0xfff) & 0xf00) >>> 8] +
                hexChars[((val & 0xff) & 0xf0) >>> 4] + hexChars[val & 0x0f];
    }

    /**
     * Hexify short value encoded in two (int-encoded)bytes.<p>
     *
     * @param a High byte of short value to be hexified
     * @param b Low byte of short value to be hexified
     * @return StangeHexString
     */
    public static String hexifyShort(int a, int b) {
        return hexifyShort(((a & 0xff) << 8) + (b & 0xff));
    }

    /**
     * Parse bytes encoded as Hexadecimals into a byte array.<p>
     *
     * @param str String containing HexBytes.
     * @return byte array containing the parsed values of the given string.
     */
    public static byte[] parseHexString(String str) {

        ByteBuffer b = new ByteBuffer(str.length() / 2);
        int i = 0;
        int size = str.length();

        if (str.startsWith("0x")) {
            i += 2;
            size -= 2;
        }

        while (size > 0) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                i++;
                size--;
            }

            if (size < 2) {
                throw new NumberFormatException("Odd number of hexadecimal digits");
            }
            String toParse = str.substring(i, i + 2);
            b.append((byte) Integer.parseInt(toParse, 16));
            i += 2;
            size -= 2;
        }
        return b.getBytes();
    }

    /**
     * Parse string of Hexadecimals into a byte array suitable for
     * unsigned BigInteger computations. Reverse the order of the
     * parsed data on the fly (input data little endian).<p>
     *
     * @param byteString String containing HexBytes.
     * @return byte array containing the parsed values of the given string.
     */
    public static byte[] parseLittleEndianHexString(String byteString) {
        byte[] result = new byte[byteString.length() / 2 + 1];
        for (int i = 0; i < byteString.length(); i += 2) {
            String toParse = byteString.substring(i, i + 2);
            result[(byteString.length() - i) / 2] =
                    (byte) Integer.parseInt(toParse, 16);
        }
        result[0] = (byte) 0; // just to make it a positive value
        return result;
    }
}
