package com.trade.modal;

import com.trade.domain.verificationType;
import jakarta.persistence.Entity;


public class TwoFactorAuth {
    private boolean isEnabled = true;
    private verificationType sendTo;

    // Getter for isEnabled
    public boolean isEnabled() {
        return isEnabled;
    }

    // Setter for isEnabled
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    // Getter for sendTo
    public verificationType getSendTo() {
        return sendTo;
    }

    // Setter for sendTo
    public void setSendTo(verificationType sendTo) {
        this.sendTo = sendTo;
    }
}
