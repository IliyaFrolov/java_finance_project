import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class Account {
    private Balance balance;
    private List<CashFlow> cashFlowList;
    private List<Double> profit = new ArrayList<>();
    private double totalInterestEarned = 0;
    private double totalIncome = 0;
    private double totalExpense = 0;
    private double totalProfit = 0;
    private int cycles = 1;
    private Cycle cycleType;
    private List<Map<String, Double>> categories;
    private List<List<Double>> summaryLog = new ArrayList<>();

    private Account(Balance balance, List<Map<String, Double>> categories, int cycles, Cycle cycleType, List<CashFlow> cashFlowList)  {
        this.balance = balance;
        this.categories = categories;
        this.cycles = cycles;
        this.cycleType = cycleType;
        this.cashFlowList = cashFlowList;
    }

    static Account createAccount(double initBalance, int cycles, Cycle cycleType, List<CashFlow> cashFlowList) {
        var instance = new Account(new Balance(initBalance), new ArrayList<>(), cycles, cycleType, cashFlowList);
        instance.run();

        return instance;
    }
    /*
    static Account createAccount(double balanceBefore, int cycles, Cycle cycleType, List<Map<String, Double>> categories, List<Double> interest, List<Double> income, boolean isCat) {
        var instance = new Account(balanceBefore, categories, cycles, cycleType, interest, income, new ArrayList<>());
        instance.run();

        return instance;
    }*/

    private void calcExpenses() {
        /*for (double expense : categories.values()) {
            expenses += expense;
        }*/
    }
    
    private void calcFinances(CashFlow cashFlow) {
        double prevBalance = balance.getPrevBalance();
        double currIncome = cashFlow.getIncome();
        double currExpense = cashFlow.getExpense();
        double currIntEarned = cashFlow.getInterestEarned(prevBalance);
        double currProfit = cashFlow.getProfit(prevBalance);

        profit.add(currProfit);
        totalInterestEarned += currIntEarned;
        totalProfit += currProfit;
        totalIncome += currIncome;
        totalExpense += currExpense;
        balance.updateBalance(currProfit);

        summaryLog.add(Arrays.asList(
            prevBalance, 
            currIncome, 
            currExpense, 
            currIntEarned, 
            currProfit, 
            balance.getNextBalance()
        ));
    }

    private void printCatBreakdown() {
        /*System.out.println("\nCategory Breakdown:");

        for (var category : categories.entrySet()) {
            System.out.println(String.format("%s - %.2f", category.getKey(), category.getValue()));
        }

        System.out.println();*/
    }

    void printCycleReport() {
        final String SUMMARY = "Summary for %s %d:\n" 
        + "Initial balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expense - %.2f\n"
        + "Interest earned - %.2f\n"
        + "Profit - %.2f\n"
        + "Balance - %.2f\n";

        for (int i = 0; i < summaryLog.size(); ++i) {
            System.out.println(String.format(
                SUMMARY, 
                cycleType.name().toLowerCase(), 
                i+1, 
                summaryLog.get(i).get(0), 
                summaryLog.get(i).get(1), 
                summaryLog.get(i).get(2), 
                summaryLog.get(i).get(3), 
                summaryLog.get(i).get(4), 
                summaryLog.get(i).get(5)
            ));

            if (!categories.isEmpty()) {
                printCatBreakdown();
            }
        }
    }
    
    void printFinalReport() {
        final String SUMMARY = "Final Summary of finances after %d %s(s):\n" 
        + "Initial balance - %.2f\n"
        + "Total income - %.2f\n"
        + "Total expense - %.2f\n"
        + "Total interest earned - %.2f\n"
        + "Total profit - %.2f\n"
        + "Total balance - %.2f\n";

        System.out.println(String.format(
            SUMMARY, 
            cycles, 
            cycleType.name().toLowerCase(), 
            balance.getInitBalance(), 
            totalIncome, 
            totalExpense, 
            totalInterestEarned, 
            totalProfit, 
            balance.getNextBalance()
        ));

        if (!categories.isEmpty()) {
            printCatBreakdown();
        }
    }

    void printAverages(Cycle cycleType) {
        System.out.println(cycleType);
    }

    private void run() {
        for (int i = 0; i < cycles; i++) {
            CashFlow cashFlow = cashFlowList.get(i);

            calcFinances(cashFlow);
        }

        double dayCycles = 0.00;
        double weekCycles = 0.00;
        double monthCycles = 0.00;

        switch (cycleType) {
            case DAY:
                dayCycles = cycles;
                monthCycles = cycles/30;
                weekCycles = cycles/7;
                break;
        
            case WEEK:
                dayCycles = cycles*7;
                weekCycles = cycles;
                monthCycles = cycles/(30.00/7.00);
                break;
            
            case MONTH:
                dayCycles = cycles*30;
                weekCycles = cycles*(30.00/7.00);
                monthCycles = cycles;
        }

        Cycle.DAY.calcAverages(totalInterestEarned, totalIncome, totalExpense, totalProfit, dayCycles);
        Cycle.WEEK.calcAverages(totalInterestEarned, totalIncome, totalExpense, totalProfit, weekCycles);
        Cycle.MONTH.calcAverages(totalInterestEarned, totalIncome, totalExpense, totalProfit, monthCycles);
    }
}
