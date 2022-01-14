import java.util.HashMap;
import java.util.Map;

class CashFlow {
    private double income;
    private double expense;
    private double interest;
    private double incomeTax;
    private static Map<String, Double> totals = new HashMap<>() {{
        put("Total income", 0.00);
        put("Total expense", 0.00);
        put("Total interest", 0.00);
        put("Total interest earned", 0.00);
        put("Total income tax", 0.00);
        put("Total tax deducted", 0.00);
        put("Total profit", 0.00);
    }};
    private ExpCategories categories;

    CashFlow(double income, double expense, double interest, double incomeTax) {
        this.income = income;
        this.expense = expense;
        this.interest = interest;
        this.incomeTax = incomeTax;
        CashFlow.totals.merge("Total income", income, Double::sum);
        CashFlow.totals.merge("Total expense", expense, Double::sum);
        CashFlow.totals.merge("Total interest", interest, Double::sum);
        CashFlow.totals.merge("Total income tax", incomeTax, Double::sum);
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

    void updateTaxDeducted(double prevBalance) {
        CashFlow.totals.merge("Total tax deducted", getTaxDeducted(prevBalance), Double::sum);
    }

    double getProfit(double prevBalance) {
        return income + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - expense;
    }

    void upadateProfit(double prevBalance) {
        double profit = income + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - expense;
        CashFlow.totals.merge("Total profit", profit, Double::sum);
    }

    ExpCategories getCategories() {
        return categories;
    }

    void updateInterestEarned(double prevBalance) {
        CashFlow.totals.merge("Total interest earned", prevBalance * interest, Double::sum);
    }

    static Map<String, Double> getTotals() {
        return CashFlow.totals;
    }
}