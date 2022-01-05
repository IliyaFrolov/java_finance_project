class Balance {
    private double initBalance;
    private double prevBalance;
    private double nextBalance;

    Balance(double initBalance) {
        this.initBalance = initBalance;
        this.prevBalance = initBalance;
        this.nextBalance = initBalance;
    }

    double getInitBalance() {
        return initBalance;
    }

    double getPrevBalance() {
        return prevBalance;
    }

    double getNextBalance() {
        return nextBalance;
    }

    void updateBalance(double profit) {
        nextBalance += profit;
        prevBalance = nextBalance;
    }
}