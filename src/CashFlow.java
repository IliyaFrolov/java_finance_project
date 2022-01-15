import java.util.HashMap;
import java.util.Map;

class CashFlow {
    private Map<String, Double> flow = new HashMap<>();
    private static Map<String, Double> totals = new HashMap<>(); 
    private ExpCategories categories;

    CashFlow(double income, double expense, double interest, double incomeTax) {
        flow.put("Income", income);
        flow.put("Expense", expense);
        flow.put("Interest", interest);
        flow.put("Income tax", incomeTax);
        flow.entrySet().stream().forEach(entry -> CashFlow.totals.merge(entry.getKey(), entry.getValue(), Double::sum));
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

    double getProfit(double prevBalance) {
        return flow.get("Income") + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - flow.get("Expense");
    }

    void updateInterestEarned(double prevBalance) {
        CashFlow.totals.merge("Interest earned", prevBalance * flow.get("Interest"), Double::sum);
    }

    void updateTaxDeducted(double prevBalance) {
        CashFlow.totals.merge("Tax deducted", getTaxDeducted(prevBalance), Double::sum);
    }

    void upadateProfit(double prevBalance) {
        double profit = flow.get("Income") + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - flow.get("Expense");
        CashFlow.totals.merge("Profit", profit, Double::sum);
    }

    ExpCategories getCategories() {
        return categories;
    }

    static Map<String, Double> getTotals() {
        return CashFlow.totals;
    }

    Map<String, Double> getFlow() {
        return flow;
    }
}