import java.util.List;

enum Cycle {
    DAY, WEEK, MONTH;

    private double avInterestEarned;
    private double avIncome;
    private double avExpense;
    private double avProfit;
    private double avGroceries;
    private double avEatingOut;
    private double avMemberships;
    private double avBills;
    private double avTravelling;
    private double avOther;

    void calcAverages(
        double totalInterestEarned, 
        double totalIncome, 
        double totalExpense, 
        double totalProfit, 
        double cycles,
        List<Double> categoriesTotal
    ) {
        if (cycles == 0) {
            return;
        }

        this.avInterestEarned = totalInterestEarned/cycles;
        this.avIncome = totalIncome/cycles;
        this.avExpense = totalExpense/cycles;
        this.avProfit = totalProfit/cycles;
        this.avGroceries = categoriesTotal.get(0)/cycles;
        this.avEatingOut = categoriesTotal.get(1)/cycles;
        this.avMemberships = categoriesTotal.get(2)/cycles;
        this.avBills = categoriesTotal.get(3)/cycles;
        this.avTravelling = categoriesTotal.get(4)/cycles;
        this.avOther = categoriesTotal.get(5)/cycles;
    }
    
    @Override
    public String toString() {
        final String SUMMARY = "Summary of averages per %s:\n" 
        + "Average interest earned - %.2f\n"
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
            avInterestEarned, 
            avIncome, 
            avExpense, 
            avProfit,
            avGroceries,
            avEatingOut,
            avMemberships,
            avBills,
            avTravelling,
            avOther
        ); 
    }   
}