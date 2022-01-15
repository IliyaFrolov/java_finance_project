import java.util.HashMap;
import java.util.Map;

class ExpCategories {
    private Map<String, Double> catFlow = new HashMap<>();
    private static Map<String, Double> catTotals = new HashMap<>();

    public ExpCategories(double groceries, double eatingOut, double memberships, double bills, double travelling, double other) {
        catFlow.put("Groceries", groceries);
        catFlow.put("Eating out", eatingOut);
        catFlow.put("Memberships", memberships);
        catFlow.put("Bills", bills);
        catFlow.put("Travelling",  travelling);
        catFlow.put("Other", other);
        catFlow.entrySet().stream().forEach(entry -> ExpCategories.catTotals.merge(entry.getKey(), entry.getValue(), Double::sum));
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
            catFlow.get("Groceries"),
            catFlow.get("Eating out"),
            catFlow.get("Memberships"),
            catFlow.get("Bills"),
            catFlow.get("Travelling"),
            catFlow.get("Other")
        );
    }

    double getExpense() {
        return catFlow.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    static Map<String, Double> getCatTotals() {
        return catTotals;
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
            catTotals.get("Groceries"),
            catTotals.get("Eating out"),
            catTotals.get("Memberships"),
            catTotals.get("Bills"),
            catTotals.get("Travelling"),
            catTotals.get("Other")
        );
    }

}
