import java.util.HashMap;
import java.util.Map;

class CashFlow {
    private Map<String, Double> flow = new HashMap<>();
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
        flow.put("Income", income);
        flow.put("Expense", expense);
        flow.put("Interest", interest);
        flow.put("Income tax", incomeTax);
        CashFlow.totals.merge("Total income", income, Double::sum);
        CashFlow.totals.merge("Total expense", expense, Double::sum);
        CashFlow.totals.merge("Total interest", interest, Double::sum);
        CashFlow.totals.merge("Total income tax", incomeTax, Double::sum);
    }

    CashFlow(double income, ExpCategories categories, double interest, double incomeTax) {
        this(income, categories.getExpense(), interest, incomeTax);
        this.categories = categories;
    }

    double getInterestEarned(double prevBalance) {
        return flow.get("Interest") * prevBalance;
    }

    double getTaxDeducted(double prevBalance) {
        return flow.get("Income tax") * prevBalance;
    }

    void updateTaxDeducted(double prevBalance) {
        CashFlow.totals.merge("Total tax deducted", getTaxDeducted(prevBalance), Double::sum);
    }

    double getProfit(double prevBalance) {
        return flow.get("Income") + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - flow.get("Expense");
    }

    void upadateProfit(double prevBalance) {
        double profit = flow.get("Income") + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - flow.get("Expense");
        CashFlow.totals.merge("Total profit", profit, Double::sum);
    }

    ExpCategories getCategories() {
        return categories;
    }

    void updateInterestEarned(double prevBalance) {
        CashFlow.totals.merge("Total interest earned", prevBalance * flow.get("Interest"), Double::sum);
    }

    static Map<String, Double> getTotals() {
        return CashFlow.totals;
    }

    Map<String, Double> getFlow() {
        return flow;
    }
}