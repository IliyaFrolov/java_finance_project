import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Account {
    private Balance balance;
    private List<CashFlow> cashFlowList;
    private List<Double> profit = new ArrayList<>();
    private double totalInterestEarned = 0;
    private double totalTaxDeducted = 0;
    private double totalProfit = 0;
    private int cycles = 1;
    private Cycle cycleType;
    private List<Map<String, Double>> summaryLog = new ArrayList<>();

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

    List<Double> getProfit() {
        return profit;
    }

    double getTotalInterestEarned() {
        return totalInterestEarned;
    }

    double getTotalTaxDeducted() {
        return totalTaxDeducted;
    }

    double getTotalProfit() {
        return totalProfit;
    }

    int getCycles() {
        return cycles;
    }

    Cycle getCycleType() {
        return cycleType;
    }

    List<Map<String, Double>> getSummaryLog() {
        return summaryLog;
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

        summaryLog.add(Map.ofEntries(
            Map.entry("Previous balance", prevBalance),
            Map.entry("Income", cashFlow.getIncome()), 
            Map.entry("Expense", cashFlow.getExpense()), 
            Map.entry("Interest earned", currIntEarned), 
            Map.entry("Tax deducted", currTaxDeducted),
            Map.entry("Profit", currProfit), 
            Map.entry("Balance", balance.getNextBalance()
        )));
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
