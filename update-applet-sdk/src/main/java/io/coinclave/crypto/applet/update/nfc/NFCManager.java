package io.coinclave.crypto.applet.update.nfc;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;

public class NFCManager {
   private Activity mActivity;
   // reader mode flags: listen for type A (not B), skipping ndef check
   private static final int READER_FLAGS =
           NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B |
                   NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK |
                   NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS;

   private NfcAdapter mNfcAdapter;

   public NFCManager(final Activity currentContext, final NfcAdapter NfcAdapter) {
       this.mActivity = currentContext;
       mNfcAdapter = NfcAdapter;
   }

   boolean isNFCEnabled() {
      return mNfcAdapter!= null && mNfcAdapter.isEnabled();
   }

    void enableNFCReaderMode(NfcAdapter.ReaderCallback readerCallback) {
       if (mNfcAdapter != null) {
           try {
               mNfcAdapter.enableReaderMode(mActivity, readerCallback, READER_FLAGS, new Bundle());

           }catch (IllegalStateException e){}

       }
   }

   void disableNFCReaderMode() {
       if (mNfcAdapter != null) {
           mNfcAdapter.disableReaderMode(mActivity);
       }
   }

   public int getReaderFlags(){
       return READER_FLAGS;
   }
}
