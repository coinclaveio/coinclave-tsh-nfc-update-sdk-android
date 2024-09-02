package io.coinclave.crypto.applet.update

class CommandDictionaryKit {

    fun getSelectActivationAppletCommand(): ByteArray {
        return ByteString("00A404000D4A4E45545F4C5F41435449560100").bytes;
    }

    fun getEncryptedCardDataCommand(): ByteArray {
        return ByteString("00C2000000").bytes;
    }

}