import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

enum Cycle {
    DAY, WEEK, MONTH;

    private Map<String, Double> averages = new HashMap<>();
    private Map<String, Double> catAverages = new HashMap<>();

    void calcAverages(Map<String, Double> totals, double cycles, Map<String, Double> catTotals) {
        if (cycles == 0) {
            return;
        }
        
        averages = totals.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()/cycles));
        catAverages = catTotals.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()/cycles));
    }

    Map<String, Double> getAverages() {
        return averages;
    }

    Map<String, Double> getCatAverages() {
        return catAverages;
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
                averages.get("Interest earned"),
                averages.get("Tax deducted"),
                averages.get("Income"),
                averages.get("Expense"),
                averages.get("Profit"),
                catAverages.get("Groceries"),
                catAverages.get("Eating out"),
                catAverages.get("Memberships"),
                catAverages.get("Bills"),
                catAverages.get("Travelling"),
                catAverages.get("Other")
                );
    }
}