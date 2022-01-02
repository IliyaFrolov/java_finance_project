import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Cycle {
    DAY, WEEK, MONTH;

    private double avInterestEarned;
    private double avIncome;
    private double avExpenses;
    private double avSavings;

    void calcAverages(double totalInterestEarned, double totalIncome, double totalExpenses, double totalSavings, double cycles) {
        if (cycles == 0) {
            return;
        }

        this.avInterestEarned = totalInterestEarned/cycles;
        this.avIncome = totalIncome/cycles;
        this.avExpenses = totalExpenses/cycles;
        this.avSavings = totalSavings/cycles;
    }
    
    @Override
    public String toString() {
        final String SUMMARY = "Summary of averages per %s:\n" 
        + "Average interest earned - %.2f\n"
        + "Average income - %.2f\n"
        + "Average expenses - %.2f\n"
        + "Average Savings - %.2f";

        return String.format(SUMMARY, this.name().toLowerCase(), avInterestEarned, avIncome, avExpenses, avSavings); 
    }
    
}

class Account {
    private double initialBalance;
    private double balanceBefore;
    private double totalInterestEarned = 0;
    private double interestEarned;
    private double interest;
    private double totalIncome = 0;
    private double income;
    private double totalExpenses = 0;
    private double expenses;
    private double balanceAfter;
    private double totalSavings = 0;
    private double savings = 0;
    private int cycles = 1;
    private Cycle cycleType;
    private Map<String, Double> categories;
    private List<List<Double>> summaryLog = new ArrayList<>();

    private Account(double balanceBefore, double interest, double income, double expenses, Map<String, Double> categories, int cycles, Cycle cycleType) {
        this.balanceBefore = balanceBefore;
        this.initialBalance = balanceBefore;
        this.interest = interest;
        this.income = income;
        this.expenses = expenses;
        this.categories = categories;
        this.cycles = cycles;
        this.cycleType = cycleType;
    }

    static Account createAccount(double balanceBefore, double interest, double income, double expenses) {
        var instance = new Account(balanceBefore, interest, income, expenses, new HashMap<>(), 1, Cycle.MONTH);
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, double interest, double income, double expenses, int cycles, Cycle cycleType) {
        var instance = new Account(balanceBefore, interest, income, expenses, new HashMap<>(), cycles, cycleType);
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, double interest, double income, Map<String, Double> categories) {
        var instance = new Account(balanceBefore, interest, income, 0, categories, 1, Cycle.MONTH);
        instance.calcExpenses();
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, double interest, double income, Map<String, Double> categories, int cycles, Cycle cycleType) {
        var instance = new Account(balanceBefore, interest, income, 0, categories, cycles, cycleType);
        instance.calcExpenses();
        instance.run();

        return instance;
    }

    private void calcExpenses() {
        for (double expense : categories.values()) {
            expenses += expense;
        }
    }
    
    private void calcFinances() {
        interestEarned = balanceBefore*interest;
        totalInterestEarned += interestEarned;
        balanceAfter = balanceBefore + income - expenses + interestEarned;
        double temp = balanceAfter - balanceBefore;
        savings = temp < 0 ? 0 : temp;
        totalSavings += savings;
        totalIncome += income;
        totalExpenses += expenses;
    }

    private void resetAndSave() {
        summaryLog.add(Arrays.asList(balanceBefore, income, 
            expenses, interestEarned, 
            savings, balanceAfter));

        balanceBefore = balanceAfter;
    }

    private void printCatBreakdown() {
        System.out.println("\nCategory Breakdown:");

        for (var category : categories.entrySet()) {
            System.out.println(String.format("%s - %.2f", category.getKey(), category.getValue()));
        }

        System.out.println();
    }

    void printCycleReport() {
        final String SUMMARY = "Summary for this %s(s) finances:\n" 
        + "Initial Balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expenses - %.2f\n"
        + "Interest Earned - %.2f\n"
        + "Savings - %.2f\n"
        + "Balance - %.2f\n";

        for (List<Double> var : summaryLog) {
            System.out.println(String.format(SUMMARY, cycleType.name().toLowerCase(), var.get(0), var.get(1), var.get(2), var.get(3), var.get(4), var.get(5)));

            if (!categories.isEmpty()) {
                printCatBreakdown();
            }
        }
    }
    
    void printFinalReport() {
        final String SUMMARY = "Final Summary of finances after %d %s(s):\n" 
        + "Initial Balance - %.2f\n"
        + "Total Income - %.2f\n"
        + "Total Expenses - %.2f\n"
        + "Total Interest Earned - %.2f\n"
        + "Total Savings - %.2f\n"
        + "Total Balance - %.2f\n";

        System.out.println(String.format(SUMMARY, cycles, cycleType.name().toLowerCase(), initialBalance, totalIncome, totalExpenses, totalInterestEarned, totalSavings, balanceAfter));

        if (!categories.isEmpty()) {
            printCatBreakdown();
        }
    }

    void printAverages(Cycle cycleType) {
        System.out.println(cycleType);
    }

    private void run() {
        for (int i = 0; i < cycles; i++) {
                calcFinances();
                resetAndSave();
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

        Cycle.DAY.calcAverages(totalInterestEarned, totalIncome, totalExpenses, totalSavings, dayCycles);
        Cycle.WEEK.calcAverages(totalInterestEarned, totalIncome, totalExpenses, totalSavings, weekCycles);
        Cycle.MONTH.calcAverages(totalInterestEarned, totalIncome, totalExpenses, totalSavings, monthCycles);
    }
}
