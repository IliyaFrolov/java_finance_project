package com.hfad.easybudget.account;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hfad.easybudget.util.Balance;
import com.hfad.easybudget.util.CashFlow;
import com.hfad.easybudget.util.Cycle;
import com.hfad.easybudget.util.ExpCategories;

public class Account implements Parcelable {
    private Balance balance;
    private List<CashFlow> cashFlowList;
    private int cycles;
    private Cycle cycleType;
    private List<Map<String, Double>> summaryLog = new ArrayList<>();
    
    private Account(Balance balance, Cycle cycleType, List<CashFlow> cashFlowList, int cycles)  {
        this.balance = balance;
        this.cycleType = cycleType;
        this.cashFlowList = cashFlowList;
        this.cycles = cycles;
    }

    protected Account(Parcel in) {
        in.readSerializable();
        in.readList(cashFlowList, Account.class.getClassLoader());
        cycles = in.readInt();
        in.readList(summaryLog, Account.class.getClassLoader());
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public static Account createAccount(double initBalance, Cycle cycleType, List<CashFlow> cashFlowList, int cycles) {
        Account instance = new Account(new Balance(initBalance), cycleType, cashFlowList, cycles);
        instance.run();

        return instance;
    }

    public static Account createAccount(double initBalance, Cycle cycleType, List<CashFlow> cashFlowList) {
        Account instance = new Account(new Balance(initBalance), cycleType, cashFlowList, cashFlowList.size());
        instance.run();

        return instance;
    }

    public Balance getBalance() {
        return balance;
    }

    public List<CashFlow> getCashFlow() {
        return cashFlowList;
    }

    public int getCycles() {
        return cycles;
    }

    public Cycle getCycleType() {
        return cycleType;
    }

    public List<Map<String, Double>> getSummaryLog() {
        return summaryLog;
    }
    
    private void calcFinances(CashFlow cashFlow) {
        double prevBalance = balance.getPrevBalance();
        Map<String, Double> flow = cashFlow.getFlow();

        cashFlow.updateInterestEarned(prevBalance);
        cashFlow.updateTaxDeducted(prevBalance);
        cashFlow.upadateProfit(prevBalance);
        balance.updateBalance(cashFlow.getProfit(prevBalance));

        summaryLog.add(Map.ofEntries(
            Map.entry("Previous balance", prevBalance),
            Map.entry("Income", flow.get("Income")), 
            Map.entry("Expense", flow.get("Expense")), 
            Map.entry("Interest", flow.get("Interest")),
            Map.entry("Income tax", flow.get("Income tax")),
            Map.entry("Interest earned", cashFlow.getInterestEarned(prevBalance)), 
            Map.entry("Tax deducted", cashFlow.getTaxDeducted(prevBalance)),
            Map.entry("Profit", cashFlow.getProfit(prevBalance)), 
            Map.entry("Balance", balance.getNextBalance()
        )));
    }

    private void run() {
        for (int i = 0; i < cycles; i++) {
            CashFlow cashFlow = cashFlowList.get(i);

            calcFinances(cashFlow);
        }

        double dayCycles = 0.00;
        double weekCycles = 0.00;
        double monthCycles = 0.00;

        switch (cycleType) {
            case DAY:
                dayCycles = cycles;
                monthCycles = cycles/30;
                weekCycles = cycles/7;
                break;
        
            case WEEK:
                dayCycles = cycles*7;
                weekCycles = cycles;
                monthCycles = cycles/(30.00/7.00);
                break;
            
            case MONTH:
                dayCycles = cycles*30;
                weekCycles = cycles*(30.00/7.00);
                monthCycles = cycles;
        }
        
        Cycle.DAY.calcAverages(CashFlow.getTotals(), dayCycles, ExpCategories.getCatTotals());
        Cycle.WEEK.calcAverages(CashFlow.getTotals(), weekCycles, ExpCategories.getCatTotals());
        Cycle.MONTH.calcAverages(CashFlow.getTotals(), monthCycles, ExpCategories.getCatTotals());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(balance);
        parcel.writeList(cashFlowList);
        parcel.writeInt(cycles);
        parcel.writeSerializable(cycleType);
        parcel.writeList(summaryLog);
    }
}
