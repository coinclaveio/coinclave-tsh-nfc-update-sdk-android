package io.coinclave.crypto.applet.update.nfc.iso7816;

public interface Iso7816Constants {
    byte CLA_00 = 0;
    byte CLA_80 = -128;
    byte CLA_84 = -124;
    byte INS_SELECT = -92;
    byte P1_SELECT_BY_NAME = 4;
    byte P2_SELECT_FIRST = 0;
    byte P2_SELECT_NEXT = 2;
    byte LE = 0;
    short SW_CARD_BLOCKED = 27265;
    short SW_SUCCESS = -28672;
    short SW_APPLICATION_BLOCKED = 25219;
    short SW_RECORD_NOT_FOUND = 27267;
}
