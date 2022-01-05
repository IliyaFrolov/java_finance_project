enum Cycle {
    DAY, WEEK, MONTH;

    private double avInterestEarned;
    private double avIncome;
    private double avExpense;
    private double avProfit;

    void calcAverages(double totalInterestEarned, double totalIncome, double totalExpense, double totalProfit, double cycles) {
        if (cycles == 0) {
            return;
        }

        this.avInterestEarned = totalInterestEarned/cycles;
        this.avIncome = totalIncome/cycles;
        this.avExpense = totalExpense/cycles;
        this.avProfit = totalProfit/cycles;
    }
    
    @Override
    public String toString() {
        final String SUMMARY = "Summary of averages per %s:\n" 
        + "Average interest earned - %.2f\n"
        + "Average income - %.2f\n"
        + "Average expense - %.2f\n"
        + "Average profit - %.2f";

        return String.format(SUMMARY, this.name().toLowerCase(), avInterestEarned, avIncome, avExpense, avProfit); 
    }
    
}