import java.util.Map;

class Account {
    private double balanceBefore;
    private double interest;
    private double income;
    private double expenses;
    private double balanceAfter = 0;
    private double savings;
    private Map<String, Double> categories;

    Account(double balanceBefore, double interest, double income, double expenses, Map<String, Double> categories) {
        this.balanceBefore = balanceBefore;
        this.interest = interest;
        this.income = income;
        this.expenses = expenses;
        this.categories = categories;
    }

    public void printReport() {
        calcFinances();
        String summary = "Summary for this month's finances:\n" 
        + "Initial Balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expenses - %.2f\n"
        + "Interest Earned - %.2f\n"
        + "Savings - %.2f\n"
        + "Total Balance - %.2f";

        System.out.println(String.format(
            summary, balanceBefore, income, 
            expenses, balanceBefore*interest, 
            savings, balanceAfter
        ));
        printCatBreakdown();
    }

    private void calcFinances() {
        balanceAfter = balanceBefore + income - expenses + balanceBefore*interest;
        double temp = balanceAfter - balanceBefore;
        savings = temp < 0 ? 0 : temp;
    }

    private void printCatBreakdown() {
        System.out.println("\nCategory Breakdown:");

        for (var category : categories.entrySet()) {
            System.out.println(String.format("%s - %.2f", category.getKey(), category.getValue()));
        }
    }
}
