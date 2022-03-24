package com.hfad.easybudget.util;

import java.util.HashMap;
import java.util.Map;

public class CashFlow {
    private Map<String, Double> flow = new HashMap<>();
    private static Map<String, Double> totals = new HashMap<>(); 
    private ExpCategories categories;

    public CashFlow(double income, double expense, double interest, double incomeTax) {
        flow.put("Income", income);
        flow.put("Expense", expense);
        flow.put("Interest", interest);
        flow.put("Income tax", incomeTax);
        flow.entrySet().stream().forEach(entry -> CashFlow.totals.merge(entry.getKey(), entry.getValue(), Double::sum));
    }

    public CashFlow(double income, ExpCategories categories, double interest, double incomeTax) {
        this(income, categories.getExpense(), interest, incomeTax);
        this.categories = categories;
    }

    public double getInterestEarned(double prevBalance) {
        return flow.get("Interest") * prevBalance;
    }

    public double getTaxDeducted(double prevBalance) {
        return flow.get("Income tax") * prevBalance;
    }

    public double getProfit(double prevBalance) {
        return flow.get("Income") + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - flow.get("Expense");
    }

    public void updateInterestEarned(double prevBalance) {
        CashFlow.totals.merge("Interest earned", prevBalance * flow.get("Interest"), Double::sum);
    }

    public void updateTaxDeducted(double prevBalance) {
        CashFlow.totals.merge("Tax deducted", getTaxDeducted(prevBalance), Double::sum);
    }

    public void upadateProfit(double prevBalance) {
        double profit = flow.get("Income") + getInterestEarned(prevBalance) - getTaxDeducted(prevBalance) - flow.get("Expense");
        CashFlow.totals.merge("Profit", profit, Double::sum);
    }

    public ExpCategories getCategories() {
        return categories;
    }

    public static Map<String, Double> getTotals() {
        return CashFlow.totals;
    }

    public Map<String, Double> getFlow() {
        return flow;
    }
}