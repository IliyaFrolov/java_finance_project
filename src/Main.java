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
        ExpCategories cat1 = new ExpCategories(90.00, 40.00, 70.00, 300.00, 100.00, 170.00);
        ExpCategories cat2 = new ExpCategories(80.00, 60.00, 70.00, 300.00, 40.00, 400.00);
        ExpCategories cat3 = new ExpCategories(50.00, 70.00, 70.00, 300.00, 0.00, 110.00);

        List<ExpCategories> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
    
        double initialBalance = 2500;
        List<Double> cInt = new ArrayList<>(Arrays.asList(0.02, 0.01, 0.14));
        List<Double> cInc = new ArrayList<>(Arrays.asList(500.00, 400.00, 300.00));
        List<Double> cExp = new ArrayList<>(Arrays.asList(800.00, 500.00, 650.00));
        List<CashFlow> cashFlowList;

        if (categories.isEmpty()) {
            cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), cExp.get(i), cInt.get(i))).collect(Collectors.toList());
        }
        else{
            cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), categories.get(i), cInt.get(i))).collect(Collectors.toList());
        }

        Account myAcc = Account.createAccount(initialBalance, 3, Cycle.MONTH, cashFlowList);
        myAcc.printCycleReport();
        myAcc.printFinalReport();
        myAcc.printAverages(Cycle.WEEK);
    }
}
