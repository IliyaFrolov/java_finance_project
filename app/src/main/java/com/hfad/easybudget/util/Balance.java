package com.hfad.easybudget.util;

import java.io.Serializable;
import java.nio.file.SecureDirectoryStream;

public class Balance implements Serializable {
    private double initBalance;
    private double prevBalance;
    private double nextBalance;

    public Balance(double initBalance) {
        this.initBalance = initBalance;
        this.prevBalance = initBalance;
        this.nextBalance = initBalance;
    }

    public double getInitBalance() {
        return initBalance;
    }

    public double getPrevBalance() {
        return prevBalance;
    }

    public double getNextBalance() {
        return nextBalance;
    }

    public void updateBalance(double profit) {
        nextBalance += profit;
        prevBalance = nextBalance;
    }
}