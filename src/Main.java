import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static void printCycleReport(Account account) {
        final String SUMMARY = "Summary for %s %d:\n" 
        + "Initial balance - %.2f\n"
        + "Income - %.2f\n"
        + "Expense - %.2f\n"
        + "Interest earned - %.2f\n"
        + "Tax deducted - %.2f\n"
        + "Profit - %.2f\n"
        + "Balance - %.2f\n";

        for (int i = 0; i < account.getSummaryLog().size(); ++i) {
            ExpCategories categories = account.getCashFlow().get(i).getCategories();

            System.out.println(String.format(
                SUMMARY, 
                account.getCycleType().name().toLowerCase(), 
                i+1, 
                account.getSummaryLog().get(i).get("Previous balance"), 
                account.getSummaryLog().get(i).get("Income"), 
                account.getSummaryLog().get(i).get("Expense"), 
                account.getSummaryLog().get(i).get("Interest earned"), 
                account.getSummaryLog().get(i).get("Tax deducted"), 
                account.getSummaryLog().get(i).get("Profit"),
                account.getSummaryLog().get(i).get("Balance")
            ));

            if (categories != null) {
                System.out.println(categories);
            }
        }
    }

    static void printFinalReport(Account account) {
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
            account.getCycles(), 
            account.getCycleType().name().toLowerCase(), 
            account.getBalance().getInitBalance(), 
            CashFlow.getTotalIncome(), 
            CashFlow.getTotalExpense(), 
            account.getTotalInterestEarned(), 
            account.getTotalTaxDeducted(),
            account.getTotalProfit(), 
            account.getBalance().getNextBalance()
        ));
        
        if (account.getCashFlow().get(0).getCategories() != null) {
            System.out.println(ExpCategories.getSummary());
        }
    }

    static void printAverages(Cycle cycleType) {
        System.out.println(cycleType);
    }

    static double sum(Map<String, Double> cat) {
        return cat.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    public static void main(String[] args) throws Exception {        
        List<ExpCategories> categories = new ArrayList<>();
        categories.add(new ExpCategories(55.92, 92.97, 17.00, 679.66, 75.96, 50.00));
        categories.add(new ExpCategories(118.02, 388.51, 17.00, 891.66, 40.00, 100.00));
        categories.add(new ExpCategories(64.56, 131.12, 17.00, 672.56, 105.51, 50.00));
        
        double initialBalance = 3000.00;
        int cycles = 3;
        List<Double> cInt = new ArrayList<>(Arrays.asList(0.01, 0.02, 0.015));
        List<Double> cIncTax = new ArrayList<>(Arrays.asList(0.03, 0.02, 0.015));
        List<Double> cInc = new ArrayList<>(Arrays.asList(1982.17, 2014.82, 1759.56));
        List<Double> cExp = new ArrayList<>(Arrays.asList(800.00, 500.00, 650.00));

        List<CashFlow> cashFlowList;

        if (categories.isEmpty()) {
            cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), cExp.get(i), cInt.get(i), cIncTax.get(i))).collect(Collectors.toList());
        }
        else{
            cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), categories.get(i), cInt.get(i), cIncTax.get(i))).collect(Collectors.toList());
        }

        Account myAcc = Account.createAccount(initialBalance, cycles, Cycle.MONTH, cashFlowList);
        printCycleReport(myAcc);
        printFinalReport(myAcc);
        printAverages(Cycle.MONTH);

        int afterCycles = 2;
        Cycle cycleType = Cycle.MONTH;
        List<CashFlow> projectedCashList = new ArrayList<>();
        CashFlow projectedCashFLow = new CashFlow(cycleType.getAvIncome(), cycleType.getAvExpense(), 0.00, 0.00);

        for (int j = 0; j < afterCycles; j++) {
            projectedCashList.add(projectedCashFLow);
        }

        Account projectedAcc = Account.createAccount(myAcc.getBalance().getNextBalance(), afterCycles, cycleType, projectedCashList);
        //printCycleReport(projectedAcc);
        //printFinalReport(projectedAcc);
    }
}
