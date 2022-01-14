import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

enum Cycle {
    DAY, WEEK, MONTH;

    private Map<String, Double> averages = new HashMap<>();
    private double avGroceries;
    private double avEatingOut;
    private double avMemberships;
    private double avBills;
    private double avTravelling;
    private double avOther;

    void calcAverages(Map<String, Double> totals, double cycles, List<Double> categoriesTotal) {
        if (cycles == 0) {
            return;
        }
        
        averages = totals.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()/cycles));
        this.avGroceries = categoriesTotal.get(0) / cycles;
        this.avEatingOut = categoriesTotal.get(1) / cycles;
        this.avMemberships = categoriesTotal.get(2) / cycles;
        this.avBills = categoriesTotal.get(3) / cycles;
        this.avTravelling = categoriesTotal.get(4) / cycles;
        this.avOther = categoriesTotal.get(5) / cycles;
    }

    double getAvInterest() {
        return averages.get("Total interest");
    }

    double getAvInterestEarned() {
        return averages.get("Total interest earned");
    }

    double getAvIncomeTax() {
        return averages.get("Total income tax");
    }

    double getAvTaxDeducted() {
        return averages.get("Total tax deducted");
    }

    double getAvIncome() {
        return averages.get("Total income");
    }

    double getAvExpense() {
        return averages.get("Total expense");
    }

    double getAvProfit() {
        return averages.get("Total profit");
    }

    double getAvGroceries() {
        return avGroceries;
    }

    double getAvEatingOut() {
        return avEatingOut;
    }

    double getAvMemberships() {
        return avMemberships;
    }

    double getAvBills() {
        return avBills;
    }

    double getAvTravelling() {
        return avTravelling;
    }

    double getAvOther() {
        return avOther;
    }

    @Override
    public String toString() {
        final String SUMMARY = "Summary of averages per %s:\n"
                + "Average interest earned - %.2f\n"
                + "Average tax deducted - %.2f\n"
                + "Average income - %.2f\n"
                + "Average expense - %.2f\n"
                + "Average profit - %.2f\n"
                + "Average groceries - %.2f\n"
                + "Average eating out - %.2f\n"
                + "Average memberships - %.2f\n"
                + "Average bills - %.2f\n"
                + "Average travelling - %.2f\n"
                + "Average Other - %.2f\n";

        return String.format(SUMMARY, this.name().toLowerCase(),
                averages.get("Total interest earned"),
                averages.get("Total tax deducted"),
                averages.get("Total income"),
                averages.get("Total expense"),
                averages.get("Total profit"),
                avGroceries,
                avEatingOut,
                avMemberships,
                avBills,
                avTravelling,
                avOther);
    }
}