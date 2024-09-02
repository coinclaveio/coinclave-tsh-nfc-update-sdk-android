package io.coinclave.crypto.applet.update;

/**
 * Created with IntelliJ IDEA.
 * User: t.egiazarov
 * Date: 18.01.13
 * Time: 18:47
 */
public class GPException extends Exception {

    public static final int ACCESS_DENIED = 1;

    public static final int ARGUMENTS_MISSING = 2;

    public static final int CARD_COMM_ERROR = 3;

    public static final int CARD_INVALID_SW = 4;

    public static final int CRYPTO_FAILED = 5;

    public static final int DATA_TOO_LARGE = 6;

    public static final int DEVICE_ERROR = 7;

    public static final int INVALID_ARGUMENTS = 8;

    public static final int INVALID_DATA = 9;

    public static final int INVALID_ENCODING = 10;

    public static final int INVALID_INDEX = 11;

    public static final int INVALID_KEY = 12;

    public static final int INVALID_LENGTH = 13;

    public static final int INVALID_MECH = 14;

    public static final int INVALID_TAG = 15;

    public static final int INVALID_TYPE = 16;

    public static final int INVALID_USAGE = 17;

    public static final int KEY_NOT_FOUND = 18;

    public static final int OBJECTCREATIONFAILED = 19;

    public static final int SECURE_CHANNEL_WRONG_STATE = 20;

    public static final int TAG_ALREADY_EXISTS = 21;

    public static final int TAG_NOT_FOUND = 22;

    public static final int UNDEFINED = 23;

    public static final int UNSUPPORTED = 24;

    public static final int USER_DEFINED = 25;

    public static final int OBJECT_NOT_FOUND = 26;

    public static final int LAST_ERROR = 26;

    public static final String[] ErrorName = {
            "ACCESS_DENIED",
            "ARGUMENTS_MISSING",
            "CARD_COMM_ERROR",
            "CARD_INVALID_SW",
            "CRYPTO_FAILED",
            "DATA_TOO_LARGE",
            "DEVICE_ERROR",
            "INVALID_ARGUMENTS",
            "INVALID_DATA",
            "INVALID_ENCODING",
            "INVALID_INDEX",
            "INVALID_KEY",
            "INVALID_LENGTH",
            "INVALID_MECH",
            "INVALID_TAG",
            "INVALID_TYPE",
            "INVALID_USAGE",
            "KEY_NOT_FOUND",
            "OBJECTCREATIONFAILED",
            "SECURE_CHANNEL_WRONG_STATE",
            "TAG_ALREADY_EXISTS",
            "TAG_NOT_FOUND",
            "UNDEFINED",
            "UNSUPPORTED",
            "USER_DEFINED",
            "OBJECT_NOT_FOUND"
    };

    private final int type;

    public GPException(String message) {
        super(message);
        this.type = UNSUPPORTED;
    }

    public GPException(String message, int type) {
        super(message);
        this.type = type;
    }

    public GPException(String message, int type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public static void throwAsGPErrorEx(int error, int reason, String message) throws GPException {
        throw new GPException(message, error);
    }

    public int getType() {
        return type;
    }

    /**
     * Return object content as string
     *
     * @return String
     */
    @Override
    public String toString() {

        String errtxt;
        if ((type >= 1) && (type <= LAST_ERROR)) {
            errtxt = ErrorName[type - 1];
        } else {
            errtxt = " " + type;
        }
        return errtxt + "  " + this.getMessage();
    }


}
