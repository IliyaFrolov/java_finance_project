import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Account {
    private double initialBalance;
    private double balanceBefore;
    private double interest;
    private double totalIncome;
    private double income;
    private double totalExpenses = 0;
    private double expenses = 0;
    private double balanceAfter = 0;
    private double totalSavings = 0;
    private double savings;
    private int cycles = 1;
    private Map<String, Double> categories = new HashMap<>();
    private List<String> summaryLog = new ArrayList<>();

    private Account(double balanceBefore, double interest, double income) {
        this.balanceBefore = balanceBefore;
        this.initialBalance = balanceBefore;
        this.interest = interest;
        this.income = income;
        this.totalIncome = income;
    }

    Account(double balanceBefore, double interest, double income, double expenses) {
        this(balanceBefore, interest, income);
        this.expenses = expenses;
        this.totalExpenses = expenses;
    }

    Account(double balanceBefore, double interest, double income, double expenses, int cycles) {
        this(balanceBefore, interest, income);
        this.cycles = cycles;
        this.expenses = expenses;
        this.totalExpenses = expenses;
    }

    Account(double balanceBefore, double interest, double income, Map<String, Double> categories) {
        this(balanceBefore, interest, income);
        this.categories = categories;
        calcExpenses();
    }

    Account(double balanceBefore, double interest, double income, Map<String, Double> categories, int cycles) {
        this(balanceBefore, interest, income);
        this.cycles = cycles;
        this.categories = categories;
        calcExpenses();
    }

    private void calcExpenses() {
        for (double expense : categories.values()) {
            expenses += expense;
            totalExpenses += expenses;
        }
    }
    
    private void calcFinances() {
        balanceAfter = balanceBefore + income - expenses + balanceBefore*interest;
        double temp = balanceAfter - balanceBefore;
        savings = temp < 0 ? 0 : temp;
        totalSavings += savings;
        totalIncome += income;
    }

    private void resetAndSave(final String SUMMARY) {
        summaryLog.add(SUMMARY);
        balanceBefore = balanceAfter;
        balanceAfter = 0;
        savings = 0;
    }

    private void printCatBreakdown() {
        System.out.println("\nCategory Breakdown:");

        for (var category : categories.entrySet()) {
            System.out.println(String.format("%s - %.2f", category.getKey(), category.getValue()));
        }

        System.out.println();
    }

    private String printReport() {
        final String SUMMARY = "Summary for this month's finances:\n" 
        + "Initial Balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expenses - %.2f\n"
        + "Interest Earned - %.2f\n"
        + "Savings - %.2f\n"
        + "Total Balance - %.2f";

        System.out.println(String.format(
            SUMMARY, balanceBefore, income, 
            expenses, balanceBefore*interest, 
            savings, balanceAfter
        ));

        if (!categories.isEmpty()) {
            printCatBreakdown();
        }

        return SUMMARY;
    }

    void run() {
        for (int i = 0; i < cycles; i++) {
                calcFinances();
                final String SUMMARY = printReport();
                resetAndSave(SUMMARY);
        }
    }
}
