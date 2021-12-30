import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Account {
    private double initialBalance;
    private double balanceBefore;
    private double totalInterestEarned = 0;
    private double interestEarned = 0;
    private double interest;
    private double totalIncome = 0;
    private double income;
    private double totalExpenses = 0;
    private double expenses = 0;
    private double totalBalance = 0;
    private double balanceAfter = 0;
    private double totalSavings = 0;
    private double savings;
    private int cycles = 1;
    private Map<String, Double> categories = new HashMap<>();
    private List<List<Double>> summaryLog = new ArrayList<>();

    private Account(double balanceBefore, double interest, double income, double expenses, Map<String, Double> categories, int cycles) {
        this.totalBalance = balanceBefore;
        this.balanceBefore = balanceBefore;
        this.initialBalance = balanceBefore;
        this.interest = interest;
        this.income = income;
        this.expenses = expenses;
        this.categories = categories;
        this.cycles = cycles;
    }

    static Account createAccount(double balanceBefore, double interest, double income, double expenses) {
        var instance = new Account(balanceBefore, interest, income, expenses, new HashMap<>(), 1);
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, double interest, double income, double expenses, int cycles) {
        var instance = new Account(balanceBefore, interest, income, expenses, new HashMap<>(), cycles);
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, double interest, double income, Map<String, Double> categories) {
        var instance = new Account(balanceBefore, interest, income, 0, categories, 1);
        instance.calcExpenses();
        instance.run();

        return instance;
    }

    static Account createAccount(double balanceBefore, double interest, double income, Map<String, Double> categories, int cycles) {
        var instance = new Account(balanceBefore, interest, income, 0, categories, cycles);
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
        totalBalance = balanceAfter;
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
        final String SUMMARY = "Summary for this month's finances:\n" 
        + "Initial Balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expenses - %.2f\n"
        + "Interest Earned - %.2f\n"
        + "Savings - %.2f\n"
        + "Balance - %.2f";

        for (List<Double> var : summaryLog) {
            System.out.println(String.format(
                SUMMARY, var.get(0), var.get(1), 
                var.get(2), var.get(3), 
                var.get(4), var.get(5)
            ));

            if (!categories.isEmpty()) {
                printCatBreakdown();
            }
        }
    }
    
    void printFinalReport() {
        final String SUMMARY = "Final Summary of finances:\n" 
        + "Initial Balance - %.2f\n"
        + "TotalIncome - %.2f\n"
        + "TotalExpenses - %.2f\n"
        + "Total Interest Earned - %.2f\n"
        + "Total Savings - %.2f\n"
        + "Total Balance - %.2f";

        System.out.println(String.format(
            SUMMARY, initialBalance, totalIncome, 
            totalExpenses, totalInterestEarned, 
            totalSavings, totalBalance
        ));

        if (!categories.isEmpty()) {
            printCatBreakdown();
        }
    }

    private void run() {
        for (int i = 0; i < cycles; i++) {
                calcFinances();
                resetAndSave();
        }
    }
}
