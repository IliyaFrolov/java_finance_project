class CashFlow {
    private double income;
    private double expense;
    private double interest;
    private double incomeTax;
    private static double totalIncome = 0;
    private static double totalExpense = 0;
    private ExpCategories categories;

    CashFlow(double income, double expense, double interest, double incomeTax) {
        this.income = income;
        this.expense = expense;
        this.interest = interest;
        this.incomeTax = incomeTax;
        CashFlow.totalIncome += income;
        CashFlow.totalExpense += expense;
    }

    CashFlow(double income, ExpCategories categories, double interest, double incomeTax) {
        this(income, categories.getExpense(), interest, incomeTax);
        this.categories = categories;
    }

    double getIncome() {
        return income;
    }

    double getExpense() {
        return expense;
    }

    double getInterest() {
        return interest;
    }

    double getIncomeTax() {
        return incomeTax;
    }

    double getInterestEarned(double prevBalance) {
        return interest * prevBalance;
    }

    double getTaxDeducted(double prevBalance) {
        return incomeTax * prevBalance;
    }

    double getProfit(double prevBalance) {
        return income + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - expense;
    }

    static double getTotalIncome() {
        return CashFlow.totalIncome; 
    }

    static double getTotalExpense() {
        return CashFlow.totalExpense; 
    }

    ExpCategories getCategories() {
        return categories;
    }
}