class CashFlow {
    private double income;
    private double expense;
    private double interest;
    private double incomeTax;
    private static double totalIncome = 0;
    private static double totalExpense = 0;
    private static double totalInterest = 0;
    private static double totalInterestEarned = 0;
    private static double totalIncomeTax = 0;
    private static double totalTaxDeducted = 0;
    private static double totalProfit = 0;
    private ExpCategories categories;

    CashFlow(double income, double expense, double interest, double incomeTax) {
        this.income = income;
        this.expense = expense;
        this.interest = interest;
        this.incomeTax = incomeTax;
        CashFlow.totalIncome += income;
        CashFlow.totalExpense += expense;
        CashFlow.totalInterest += interest;
        CashFlow.totalIncomeTax += incomeTax;
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

    static double getTotalInterest() {
        return CashFlow.totalInterest;
    }

    double getInterestEarned(double prevBalance) {
        return interest * prevBalance;
    }

    static double getTotalIncomeTax() {
        return CashFlow.totalIncomeTax;
    }

    double getTaxDeducted(double prevBalance) {
        return incomeTax * prevBalance;
    }

    static double getTotalTaxDeducted() {
        return CashFlow.totalTaxDeducted;
    }

    void updateTaxDeducted(double prevBalance) {
        CashFlow.totalTaxDeducted += getTaxDeducted(prevBalance);
    }

    double getProfit(double prevBalance) {
        return income + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - expense;
    }

    void upadateProfit(double prevBalance) {
        CashFlow.totalProfit += income + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - expense;
    }

    static double getTotalIncome() {
        return CashFlow.totalIncome; 
    }

    static double getTotalExpense() {
        return CashFlow.totalExpense; 
    }

    static double getTotalInterestEarned() {
        return CashFlow.totalInterestEarned;
    }

    static double getTotalProfit() {
        return CashFlow.totalProfit;
    }

    ExpCategories getCategories() {
        return categories;
    }

    void updateInterestEarned(double prevBalance) {
        CashFlow.totalInterestEarned += prevBalance * interest;
    }
}