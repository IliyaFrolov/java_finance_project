import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Account {
    private Balance balance;
    private List<CashFlow> cashFlowList;
    private List<Double> profit = new ArrayList<>();
    private double totalInterestEarned = 0;
    private double totalTaxDeducted = 0;
    private double totalProfit = 0;
    private int cycles = 1;
    private Cycle cycleType;
    private List<List<Double>> summaryLog = new ArrayList<>();

    private Account(Balance balance, int cycles, Cycle cycleType, List<CashFlow> cashFlowList)  {
        this.balance = balance;
        this.cycles = cycles;
        this.cycleType = cycleType;
        this.cashFlowList = cashFlowList;
    }

    static Account createAccount(double initBalance, int cycles, Cycle cycleType, List<CashFlow> cashFlowList) {
        var instance = new Account(new Balance(initBalance), cycles, cycleType, cashFlowList);
        instance.run();

        return instance;
    }

    Balance getBalance() {
        return balance;
    }

    List<CashFlow> getCashFlow() {
        return cashFlowList;
    }
    
    private void calcFinances(CashFlow cashFlow) {
        double prevBalance = balance.getPrevBalance();
        double currIntEarned = cashFlow.getInterestEarned(prevBalance);
        double currTaxDeducted = cashFlow.getTaxDeducted(prevBalance);
        double currProfit = cashFlow.getProfit(prevBalance);

        profit.add(currProfit);
        totalInterestEarned += currIntEarned;
        totalTaxDeducted += currTaxDeducted;
        totalProfit += currProfit;
        balance.updateBalance(currProfit);

        summaryLog.add(Arrays.asList(
            prevBalance, 
            cashFlow.getIncome(), 
            cashFlow.getExpense(), 
            currIntEarned, 
            currTaxDeducted,
            currProfit, 
            balance.getNextBalance()
        ));
    }

    void printCycleReport() {
        final String SUMMARY = "Summary for %s %d:\n" 
        + "Initial balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expense - %.2f\n"
        + "Interest earned - %.2f\n"
        + "Tax deducted - %.2f\n"
        + "Profit - %.2f\n"
        + "Balance - %.2f\n";

        for (int i = 0; i < summaryLog.size(); ++i) {
            ExpCategories categories = cashFlowList.get(i).getCategories();

            System.out.println(String.format(
                SUMMARY, 
                cycleType.name().toLowerCase(), 
                i+1, 
                summaryLog.get(i).get(0), 
                summaryLog.get(i).get(1), 
                summaryLog.get(i).get(2), 
                summaryLog.get(i).get(3), 
                summaryLog.get(i).get(4), 
                summaryLog.get(i).get(5),
                summaryLog.get(i).get(6)
            ));

            if (categories != null) {
                System.out.println(categories);
            }
        }
    }
    
    void printFinalReport() {
        final String SUMMARY = "Final Summary of finances after %d %s(s):\n" 
        + "Initial balance - %.2f\n"
        + "Total income - %.2f\n"
        + "Total expense - %.2f\n"
        + "Total interest earned - %.2f\n"
        + "Total tax deducted - %.2f\n"
        + "Total profit - %.2f\n"
        + "Total balance - %.2f\n";

        System.out.println(String.format(
            SUMMARY, 
            cycles, 
            cycleType.name().toLowerCase(), 
            balance.getInitBalance(), 
            CashFlow.getTotalIncome(), 
            CashFlow.getTotalExpense(), 
            totalInterestEarned, 
            totalTaxDeducted,
            totalProfit, 
            balance.getNextBalance()
        ));
        
        if (cashFlowList.get(0).getCategories() != null) {
            System.out.println(ExpCategories.getSummary());
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
        
        Cycle.DAY.calcAverages(totalInterestEarned, totalTaxDeducted, CashFlow.getTotalIncome(), CashFlow.getTotalExpense(), totalProfit, dayCycles, ExpCategories.getTotalsList());
        Cycle.WEEK.calcAverages(totalInterestEarned, totalTaxDeducted, CashFlow.getTotalIncome(), CashFlow.getTotalExpense(), totalProfit, weekCycles, ExpCategories.getTotalsList());
        Cycle.MONTH.calcAverages(totalInterestEarned, totalTaxDeducted, CashFlow.getTotalIncome(), CashFlow.getTotalExpense(), totalProfit, monthCycles, ExpCategories.getTotalsList());
    }
}
