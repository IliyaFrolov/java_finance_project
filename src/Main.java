import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static double sum(Map<String, Double> cat) {
        return cat.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    public static void main(String[] args) throws Exception {        
        List<ExpCategories> categories = new ArrayList<>();/*
        categories.add(new ExpCategories(55.92, 92.97, 17.00, 679.66, 75.96, 50.00));
        categories.add(new ExpCategories(118.02, 388.51, 17.00, 891.66, 40.00, 100.00));
        categories.add(new ExpCategories(64.56, 131.12, 17.00, 672.56, 105.51, 50.00));
        */
        double initialBalance = 3000.00;
        List<Double> cInt = new ArrayList<>(Arrays.asList(0.00, 0.00, 0.00));
        List<Double> cIncTax = new ArrayList<>(Arrays.asList(0.00, 0.00, 0.00));
        List<Double> cInc = new ArrayList<>(Arrays.asList(1982.17, 2014.82, 1759.56));
        List<Double> cExp = new ArrayList<>(Arrays.asList(800.00, 500.00, 650.00));

        List<CashFlow> cashFlowList;

        if (categories.isEmpty()) {
            cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), cExp.get(i), cInt.get(i), cIncTax.get(i))).collect(Collectors.toList());
        }
        else{
            cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), categories.get(i), cInt.get(i), cIncTax.get(i))).collect(Collectors.toList());
        }

        Account myAcc = Account.createAccount(initialBalance, 3, Cycle.MONTH, cashFlowList);
        myAcc.printCycleReport();
        myAcc.printFinalReport();
        myAcc.printAverages(Cycle.MONTH);

        Account projectedAcc = Account.createAccount(myAcc.getBalance().getNextBalance(), 2, Cycle.MONTH, myAcc.getCashFlow());
        projectedAcc.printCycleReport();
        projectedAcc.printFinalReport();
        projectedAcc.printAverages(Cycle.MONTH);
    }
}
