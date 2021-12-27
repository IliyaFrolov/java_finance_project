import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

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
        Account myAcc = new Account(1000.00, 0.25, 500.00, categories);
        myAcc.printReport();
    }
}
