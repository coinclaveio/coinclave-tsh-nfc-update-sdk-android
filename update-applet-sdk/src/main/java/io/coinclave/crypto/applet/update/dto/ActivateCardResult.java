package io.coinclave.crypto.applet.update.dto;

import java.io.Serializable;

public class ActivateCardResult implements Serializable {

    private String pan;
    private String expiryDate;

    public ActivateCardResult(String pan, String expiryDate) {
        this.pan = pan;
        this.expiryDate = expiryDate;
    }

    public String getPan() {
        return pan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
