import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

class Expense {
    private int id;
    private String category;
    private double amount;
    private String description;
    private String date;

    public Expense(int id, String category, double amount, String description, String date) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Category: " + category + ", Amount: " + amount + ", Description: " + description + ", Date: " + date;
    }
}

public class ExpenseTrackerApp {
    private ArrayList<Expense> expenses;
    private Scanner scanner;
    private int expenseIdCounter;

    public ExpenseTrackerApp() {
        expenses = new ArrayList<>();
        scanner = new Scanner(System.in);
        expenseIdCounter = 1;  // Initializing with 1 for first expense
    }

    public void start() {
        System.out.println("=== Welcome to the Expense Tracker Application ===");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new expense");
            System.out.println("2. Add multiple expenses");
            System.out.println("3. View all expenses");
            System.out.println("4. Delete an expense");
            System.out.println("5. Search expenses by category");
            System.out.println("6. Sort expenses by amount");
            System.out.println("7. Calculate total expenses");
            System.out.println("8. Generate expense report");
            System.out.println("9. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> addMultipleExpenses();
                case 3 -> viewAllExpenses();
                case 4 -> deleteExpense();
                case 5 -> searchExpensesByCategory();
                case 6 -> sortExpensesByAmount();
                case 7 -> calculateTotalExpenses();
                case 8 -> generateExpenseReport();
                case 9 -> {
                    System.out.println("Exiting... Thank you for using the Expense Tracker!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addExpense() {
        System.out.print("Enter category (e.g., Food, Entertainment, Transport): ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        Expense expense = new Expense(expenseIdCounter++, category, amount, description, date);
        expenses.add(expense);
        System.out.println("Expense added successfully with ID: " + expense.getId());
    }

    private void addMultipleExpenses() {
        System.out.print("Enter number of expenses to add: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 0; i < n; i++) {
            addExpense();
        }
    }

    private void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("List of all expenses:");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    private void deleteExpense() {
        System.out.print("Enter the ID of the expense to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Expense expenseToRemove = null;
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenseToRemove = expense;
                break;
            }
        }

        if (expenseToRemove != null) {
            expenses.remove(expenseToRemove);
            System.out.println("Expense with ID " + id + " has been deleted.");
        } else {
            System.out.println("Expense with ID " + id + " not found.");
        }
    }

    private void searchExpensesByCategory() {
        System.out.print("Enter category to search (e.g., Food, Entertainment, Transport): ");
        String category = scanner.nextLine();

        boolean found = false;
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                System.out.println(expense);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found for category: " + category);
        }
    }

    private void sortExpensesByAmount() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("Choose sorting order:");
            System.out.println("1. Sort by amount (Ascending)");
            System.out.println("2. Sort by amount (Descending)");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                expenses.sort(Comparator.comparingDouble(Expense::getAmount));
            } else if (choice == 2) {
                expenses.sort(Comparator.comparingDouble(Expense::getAmount).reversed());
            } else {
                System.out.println("Invalid choice. Sorting by ascending order.");
                expenses.sort(Comparator.comparingDouble(Expense::getAmount));
            }

            System.out.println("Expenses sorted by amount:");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    private void calculateTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        System.out.println("Total Expenses: " + total);
    }

    private void generateExpenseReport() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("=== Expense Report ===");
            double total = 0;
            for (Expense expense : expenses) {
                System.out.println(expense);
                total += expense.getAmount();
            }
            System.out.println("Total Expenses: " + total);
            System.out.println("=== End of Report ===");
        }
    }

    public static void main(String[] args) {
        ExpenseTrackerApp app = new ExpenseTrackerApp();
        app.start();
    }
}
