package com.hfad.easybudget.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CashFlow implements Parcelable {
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

    protected CashFlow(Parcel in) {
        in.readMap(flow, CashFlow.class.getClassLoader());
        in.readMap(totals, CashFlow.class.getClassLoader());
        in.readSerializable();
    }

    public static final Creator<CashFlow> CREATOR = new Creator<CashFlow>() {
        @Override
        public CashFlow createFromParcel(Parcel in) {
            return new CashFlow(in);
        }

        @Override
        public CashFlow[] newArray(int size) {
            return new CashFlow[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(flow);
        parcel.writeMap(CashFlow.totals);
        parcel.writeSerializable(categories);
    }
}