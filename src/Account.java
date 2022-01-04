import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

enum Cycle {
    DAY, WEEK, MONTH;

    private double avInterestEarned;
    private double avIncome;
    private double avExpense;
    private double avSavings;

    void calcAverages(double totalInterestEarned, double totalIncome, double totalExpense, double totalSavings, double cycles) {
        if (cycles == 0) {
            return;
        }

        this.avInterestEarned = totalInterestEarned/cycles;
        this.avIncome = totalIncome/cycles;
        this.avExpense = totalExpense/cycles;
        this.avSavings = totalSavings/cycles;
    }
    
    @Override
    public String toString() {
        final String SUMMARY = "Summary of averages per %s:\n" 
        + "Average interest earned - %.2f\n"
        + "Average income - %.2f\n"
        + "Average expense - %.2f\n"
        + "Average savings - %.2f";

        return String.format(SUMMARY, this.name().toLowerCase(), avInterestEarned, avIncome, avExpense, avSavings); 
    }
    
}

class CashFlow {
    private double income;
    private double expense;
    private double interest;

    CashFlow(double income, double expense, double interest) {
        this.income = income;
        this.expense = expense;
        this.interest = interest;
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
}

class Account {
    private double initialBalance;
    private double balanceBefore;
    private double balanceAfter;
    private List<CashFlow> cashFlowList;
    private List<Double> savings = new ArrayList<>();
    private double totalInterestEarned = 0;
    private double totalIncome = 0;
    private double totalExpense = 0;
    private double totalSavings = 0;
    private int cycles = 1;
    private Cycle cycleType;
    private List<Map<String, Double>> categories;
    private List<List<Double>> summaryLog = new ArrayList<>();

    private Account(double balanceBefore, List<Map<String, Double>> categories, int cycles, Cycle cycleType, List<CashFlow> cashFlowList)  {
        this.balanceBefore = balanceBefore;
        this.initialBalance = balanceBefore;
        this.categories = categories;
        this.cycles = cycles;
        this.cycleType = cycleType;
        this.cashFlowList = cashFlowList;
    }

    static Account createAccount(double balanceBefore, int cycles, Cycle cycleType, List<CashFlow> cashFlowList) {
        var instance = new Account(balanceBefore, new ArrayList<>(), cycles, cycleType, cashFlowList);
        instance.run();

        return instance;
    }
    /*
    static Account createAccount(double balanceBefore, int cycles, Cycle cycleType, List<Map<String, Double>> categories, List<Double> interest, List<Double> income, boolean isCat) {
        var instance = new Account(balanceBefore, categories, cycles, cycleType, interest, income, new ArrayList<>());
        instance.run();

        return instance;
    }*/

    private void calcExpenses() {
        /*for (double expense : categories.values()) {
            expenses += expense;
        }*/
    }
    
    private void calcFinances(CashFlow cashFlow) {
        double currSavings = cashFlow.getProfit(balanceBefore);

        balanceAfter = balanceBefore + currSavings;
        savings.add(currSavings < 0 ? 0 : currSavings);
        totalInterestEarned += cashFlow.getInterestEarned(balanceBefore);
        totalSavings += currSavings;
        totalIncome += cashFlow.getIncome();
        totalExpense += cashFlow.getExpense();
    }

    private void resetAndSave(CashFlow cashFlow) { 
        summaryLog.add(Arrays.asList(balanceBefore, cashFlow.getIncome(), 
            cashFlow.getExpense(), cashFlow.getInterestEarned(balanceBefore), 
            cashFlow.getProfit(balanceBefore), balanceAfter));

        balanceBefore = balanceAfter;
    }

    private void printCatBreakdown() {
        /*System.out.println("\nCategory Breakdown:");

        for (var category : categories.entrySet()) {
            System.out.println(String.format("%s - %.2f", category.getKey(), category.getValue()));
        }

        System.out.println();*/
    }

    void printCycleReport() {
        final String SUMMARY = "Summary for %s %d:\n" 
        + "Initial balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expense - %.2f\n"
        + "Interest earned - %.2f\n"
        + "Savings - %.2f\n"
        + "Balance - %.2f\n";

        for (int i = 0; i < summaryLog.size(); ++i) {
            System.out.println(String.format(SUMMARY, cycleType.name().toLowerCase(), i+1, summaryLog.get(i).get(0), summaryLog.get(i).get(1), summaryLog.get(i).get(2), summaryLog.get(i).get(3), summaryLog.get(i).get(4), summaryLog.get(i).get(5)));

            if (!categories.isEmpty()) {
                printCatBreakdown();
            }
        }
    }
    
    void printFinalReport() {
        final String SUMMARY = "Final Summary of finances after %d %s(s):\n" 
        + "Initial balance - %.2f\n"
        + "Total income - %.2f\n"
        + "Total expense - %.2f\n"
        + "Total interest earned - %.2f\n"
        + "Total savings - %.2f\n"
        + "Total balance - %.2f\n";

        System.out.println(String.format(SUMMARY, cycles, cycleType.name().toLowerCase(), initialBalance, totalIncome, totalExpense, totalInterestEarned, totalSavings, balanceAfter));

        if (!categories.isEmpty()) {
            printCatBreakdown();
        }
    }

    void printAverages(Cycle cycleType) {
        System.out.println(cycleType);
    }

    private void run() {
        for (int i = 0; i < cycles; i++) {
            CashFlow cashFlow = cashFlowList.get(i);

            calcFinances(cashFlow);
            resetAndSave(cashFlow);
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

        Cycle.DAY.calcAverages(totalInterestEarned, totalIncome, totalExpense, totalSavings, dayCycles);
        Cycle.WEEK.calcAverages(totalInterestEarned, totalIncome, totalExpense, totalSavings, weekCycles);
        Cycle.MONTH.calcAverages(totalInterestEarned, totalIncome, totalExpense, totalSavings, monthCycles);
    }
}
