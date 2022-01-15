import java.util.HashMap;
import java.util.Map;

class ExpCategories {
    private Map<String, Double> catFlow = new HashMap<>();
    private static Map<String, Double> catTotals = new HashMap<>() {{
        put("Total groceries", 0.00);
        put("Total eating out", 0.00);
        put("Total memberships", 0.00);
        put("Total bills", 0.00);
        put("Total travelling", 0.00);
        put("Total other", 0.00);
    }};

    public ExpCategories(double groceries, double eatingOut, double memberships, double bills, double travelling, double other) {
        catFlow.put("Groceries", groceries);
        catFlow.put("Eating out", eatingOut);
        catFlow.put("Memberships", memberships);
        catFlow.put("Bills", bills);
        catFlow.put("Travelling",  travelling);
        catFlow.put("Other", other);
        ExpCategories.catTotals.merge("Total groceries", groceries, Double::sum);
        ExpCategories.catTotals.merge("Total eating out", eatingOut, Double::sum);  
        ExpCategories.catTotals.merge("Total memberships", memberships, Double::sum); 
        ExpCategories.catTotals.merge("Total bills", bills, Double::sum);
        ExpCategories.catTotals.merge("Total travelling", travelling, Double::sum); 
        ExpCategories.catTotals.merge("Total other", other, Double::sum);
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
            catTotals.get("Total groceries"),
            catTotals.get("Total eating out"),
            catTotals.get("Total memberships"),
            catTotals.get("Total bills"),
            catTotals.get("Total travelling"),
            catTotals.get("Total other")
        );
    }

}
