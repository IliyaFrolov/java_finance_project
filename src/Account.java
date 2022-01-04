import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private double interestEarned;
    private double income;
    private double expense;

    double getInterestEarned() {
        return interestEarned;
    }

    void setInterestEarned(double interestEarned) {
        this.interestEarned = interestEarned;
    }

    double getIncome() {
        return income;
    }

    double getExpense() {
        return expense;
    }

    double getProfit() {
        return income + interestEarned - expense;
    }
}

class Account {
    private double initialBalance;
    private double balanceBefore;
    private double balanceAfter;
    private List<Double> interest;
    private double interestEarned;
    private List<Double> income;
    private List<Double> expense;
    private List<Double> savings = new ArrayList<>();
    private double totalInterestEarned = 0;
    private double totalIncome = 0;
    private double totalExpense = 0;
    private double totalSavings = 0;
    private int cycles = 1;
    private Cycle cycleType;
    private List<Map<String, Double>> categories;
    private List<List<Double>> summaryLog = new ArrayList<>();

    private Account(double balanceBefore, List<Map<String, Double>> categories, int cycles, Cycle cycleType, List<Double> interest, List<Double> income, List<Double> expense) {
        this.balanceBefore = balanceBefore;
        this.initialBalance = balanceBefore;
        this.categories = categories;
        this.cycles = cycles;
        this.cycleType = cycleType;
        this.interest = interest;
        this.income = income;
        this.expense = expense;
    }

    static Account createAccount(double balanceBefore, int cycles, Cycle cycleType, List<Double> interest, List<Double> income, List<Double> expense) {
        var instance = new Account(balanceBefore, new ArrayList<>(), cycles, cycleType, interest, income, expense);
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, int cycles, Cycle cycleType, List<Map<String, Double>> categories, List<Double> interest, List<Double> income, boolean isCat) {
        var instance = new Account(balanceBefore, categories, cycles, cycleType, interest, income, new ArrayList<>());
        instance.run();

        return instance;
    }

    private void calcExpenses() {
        /*for (double expense : categories.values()) {
            expenses += expense;
        }*/
    }
    
    private void calcFinances(double interest, double income, double expenses) {
        interestEarned = balanceBefore*interest;
        totalInterestEarned += interestEarned;
        balanceAfter = balanceBefore + income - expenses + interestEarned;
        double temp = balanceAfter - balanceBefore;
        double currSavings = temp < 0 ? 0 : temp;
        savings.add(currSavings);
        totalSavings += currSavings;
        totalIncome += income;
        totalExpense += expenses;
    }

    private void resetAndSave(double currInterest, double currIncome, double currExpenses, double currSavings) { //doesnt make sense anymore, saving the same list
        summaryLog.add(Arrays.asList(balanceBefore, currIncome, 
            currExpenses, interestEarned, 
            currSavings, balanceAfter));

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
                calcFinances(interest.get(i), income.get(i), expense.get(i));
                resetAndSave(interest.get(i), income.get(i), expense.get(i), savings.get(i));
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
