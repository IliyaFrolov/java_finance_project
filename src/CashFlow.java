class CashFlow {
    private double income;
    private double expense;
    private double interest;
    private ExpCategories categories;

    CashFlow(double income, double expense, double interest) {
        this.income = income;
        this.expense = expense;
        this.interest = interest;
    }

    CashFlow(double income, ExpCategories categories, double interest) {
        this.income = income;
        this.expense = categories.getExpense();
        this.interest = interest;
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

    double getInterestEarned(double balanceBefore) {
        return interest * balanceBefore;
    }

    double getProfit(double balanceBefore) {
        return income + getInterestEarned(balanceBefore) - expense;
    }

    ExpCategories getCategories() {
        return categories;
    }
}