import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ExpCategories {
    private double groceries;
    private double eatingOut;
    private double memberships;
    private double bills;
    private double travelling;
    private double other;
    private static double totalGroceries = 0;
    private static double totalEatingOut = 0;
    private static double totalMemberships = 0;
    private static double totalBills = 0;
    private static double totalTravelling = 0;
    private static double totalOther = 0;

    public ExpCategories(double groceries, double eatingOut, double memberships, double bills, double travelling, double other) {
        this.groceries = groceries;
        this.eatingOut = eatingOut;
        this.memberships = memberships;
        this.bills = bills;
        this.travelling = travelling;
        this.other = other;
        ExpCategories.totalGroceries += groceries;
        ExpCategories.totalEatingOut += eatingOut;
        ExpCategories.totalMemberships += memberships;
        ExpCategories.totalBills += bills;
        ExpCategories.totalTravelling += travelling;
        ExpCategories.totalOther += other;
    }
    @Override
    public String toString() {
        String breakdown = 
        "Category Breakdown:\n"
        + "Groceries - %.2f\n"
        + "Eating out - %.2f\n"
        + "Memberships - %.2f\n"
        + "Bills - %.2f\n"
        + "Travelling - %.2f\n"
        + "Other - %.2f\n";

        return String.format(breakdown, 
            groceries,
            eatingOut,
            memberships,
            bills,
            travelling,
            other
        );
    }

    double getExpense() {
        return groceries 
        + eatingOut
        + memberships
        + bills
        + travelling 
        + other;
    }

    static List<Double> getTotalsList() {
        return new ArrayList<Double>(Arrays.asList(
            totalGroceries,
            totalEatingOut,
            totalMemberships,
            totalBills,
            totalTravelling,
            totalOther
        ));
    }

    static String getSummary() {
        String breakdown = 
        "Category Summary Breakdown:\n"
        + "Total Groceries - %.2f\n"
        + "Total Eating out - %.2f\n"
        + "Total Memberships - %.2f\n"
        + "Total Bills - %.2f\n"
        + "Total Travelling - %.2f\n"
        + "Total Other - %.2f\n";

        return String.format(breakdown, 
            totalGroceries,
            totalEatingOut,
            totalMemberships,
            totalBills,
            totalTravelling,
            totalOther
        );
    }

}
