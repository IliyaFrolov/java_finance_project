import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Map<String, Double>> categories = new ArrayList<>(Arrays.asList());
        /*
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter your current balance:");
        Double balanceBefore = reader.nextDouble();

        System.out.println("Enter your income:");
        Double income = reader.nextDouble();

        System.out.println("Eating out:");
        categories.put("Eating out", reader.nextDouble());

        reader.close();
        */
        double initialBalance = 2500;
        List<Double> cInt = new ArrayList<>(Arrays.asList(0.02, 0.01, 0.14));
        List<Double> cInc = new ArrayList<>(Arrays.asList(500.00, 400.00, 300.00));
        List<Double> cExp = new ArrayList<>(Arrays.asList(800.00, 500.00, 650.00));
        List<CashFlow> cashFlowList = IntStream.range(0, cInt.size()).mapToObj(i -> new CashFlow(cInc.get(i), cExp.get(i), cInt.get(i))).collect(Collectors.toList());

        Account myAcc = Account.createAccount(initialBalance, 3, Cycle.MONTH, cashFlowList);
        myAcc.printCycleReport();
        myAcc.printFinalReport();
        myAcc.printAverages(Cycle.WEEK);
    }
}
