class Account {
    private double balanceBefore;
    private double interest;
    private double income;
    private double expenses;
    private double balanceAfter = 0;

    Account(double balanceBefore, double interest, double income, double expenses) {
        this.balanceBefore = balanceBefore;
        this.interest = interest;
        this.income = income;
        this.expenses = expenses;
    }

    public void printReport() {
        final String SUMMARY = "Summary for this month's finances:\n" 
        + "Initial Balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expenses - %.2f\n"
        + "Interest Earned - %.2f\n"
        + "Total Balance - %.2f";
        calcBalance();
        System.out.println(String.format(
            SUMMARY, balanceBefore, income, 
            expenses, balanceBefore*interest, 
            balanceAfter
        ));
    }

    private void calcBalance() {
        balanceAfter = balanceBefore + income - expenses + balanceBefore*interest;
    }
}
