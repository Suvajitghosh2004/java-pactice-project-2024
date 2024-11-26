import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String description;
    private double amount;
    private String type; // "income" or "expense"

    public Transaction(String description, double amount, String type) {
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toUpperCase() + ": " + description + " - $" + amount;
    }
}

class PersonalFinanceTracker {
    private ArrayList<Transaction> transactions;
    private double balance;

    public PersonalFinanceTracker() {
        transactions = new ArrayList<>();
        balance = 0.0;
    }

    public void addIncome(String description, double amount) {
        transactions.add(new Transaction(description, amount, "income"));
        balance += amount;
        System.out.println("Income added: " + description + " - $" + amount);
    }

    public void addExpense(String description, double amount) {
        transactions.add(new Transaction(description, amount, "expense"));
        balance -= amount;
        System.out.println("Expense added: " + description + " - $" + amount);
    }

    public void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public void viewBalance() {
        System.out.println("Current Balance: $" + balance);
    }

    public void viewSummary() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("income")) {
                totalIncome += transaction.getAmount();
            } else {
                totalExpense += transaction.getAmount();
            }
        }

        System.out.println("Summary:");
        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpense);
        System.out.println("Net Worth: $" + (totalIncome - totalExpense));
    }
}

public class PersonalFinanceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersonalFinanceTracker tracker = new PersonalFinanceTracker();

        while (true) {
            System.out.println("\nPersonal Finance Tracker");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. View Summary");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter description of income: ");
                    String incomeDescription = scanner.nextLine();
                    System.out.print("Enter amount of income: ");
                    double incomeAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    tracker.addIncome(incomeDescription, incomeAmount);
                    break;

                case 2:
                    System.out.print("Enter description of expense: ");
                    String expenseDescription = scanner.nextLine();
                    System.out.print("Enter amount of expense: ");
                    double expenseAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    tracker.addExpense(expenseDescription, expenseAmount);
                    break;

                case 3:
                    tracker.viewTransactions();
                    break;

                case 4:
                    tracker.viewBalance();
                    break;

                case 5:
                    tracker.viewSummary();
                    break;

                case 6:
                    System.out.println("Exiting the Personal Finance Tracker. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
