import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Double> categories = new HashMap<>();
        categories.put("Entertainment", 30.00);
        categories.put("Memberships", 70.00);
        categories.put("Eating out", 90.00);
        categories.put("Groceries", 100.00);
        categories.put("Other", 5.00);
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
        List<Double> cyclicInterest = new ArrayList<>(Arrays.asList(0.00, 0.00, 0.00));
        List<Double> cyclicIncome = new ArrayList<>(Arrays.asList(500.00, 400.00, 300.00));
        List<Double> cyclicExpenses = new ArrayList<>(Arrays.asList(200.00, 150.00, 100.00));
        Account myAcc = Account.createAccount(initialBalance, cyclicInterest, cyclicIncome, cyclicExpenses, 3, Cycle.MONTH);
        myAcc.printCycleReport();
        myAcc.printFinalReport();
        myAcc.printAverages(Cycle.MONTH);
    }
}
