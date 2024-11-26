import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Class representing a Bank Account
class BankAccount implements Serializable {
    private static int accountCounter = 1000; // To generate unique account numbers
    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;
    private ArrayList<String> transactionHistory;

    public BankAccount(String accountHolderName, String accountType) {
        this.accountNumber = accountCounter++;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0; // Initial balance is 0
        this.accountType = accountType;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial balance: $0.00");
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void deposit(double amount) {
        this.balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds for withdrawal.");
            return false;
        }
        this.balance -= amount;
        transactionHistory.add("Withdrew: $" + amount);
        return true;
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for Account " + accountNumber + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Holder: " + accountHolderName + ", Type: " + accountType +
                ", Balance: $" + balance;
    }
}

// Main Bank Account Management System Class
public class BankAccountSystem {
    private ArrayList<BankAccount> accounts;
    private Scanner scanner;

    public BankAccountSystem() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadAccounts();
    }

    public void start() {
        System.out.println("=== Welcome to the Bank Account System ===");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create a new account");
            System.out.println("2. View all accounts");
            System.out.println("3. Deposit money");
            System.out.println("4. Withdraw money");
            System.out.println("5. Check balance");
            System.out.println("6. View transaction history");
            System.out.println("7. Close an account");
            System.out.println("8. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewAccounts();
                case 3 -> depositMoney();
                case 4 -> withdrawMoney();
                case 5 -> checkBalance();
                case 6 -> viewTransactionHistory();
                case 7 -> closeAccount();
                case 8 -> {
                    System.out.println("Exiting... Thank you for using the Bank Account System!");
                    saveAccounts();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createAccount() {
        System.out.print("Enter account holder's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter account type (Saving/Checking): ");
        String type = scanner.nextLine();
        BankAccount account = new BankAccount(name, type);
        accounts.add(account);
        System.out.println("Account created successfully! Account Number: " + account.getAccountNumber());

        // Prompt for initial deposit
        double initialDeposit = 0.0;
        while (initialDeposit <= 0) {
            System.out.print("Enter the initial deposit amount: $");
            initialDeposit = scanner.nextDouble();
            if (initialDeposit <= 0) {
                System.out.println("Initial deposit must be greater than $0. Please try again.");
            }
        }
        account.deposit(initialDeposit);
        System.out.println("Successfully deposited: $" + initialDeposit);
    }

    private void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            System.out.println("All bank accounts:");
            for (BankAccount account : accounts) {
                System.out.println(account);
            }
        }
    }

    private void depositMoney() {
        System.out.print("Enter account number to deposit into: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccountByNumber(accountNumber);

        if (account != null) {
            System.out.print("Enter amount to deposit: $");
            double amount = scanner.nextDouble();
            account.deposit(amount);
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private void withdrawMoney() {
        System.out.print("Enter account number to withdraw from: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccountByNumber(accountNumber);

        if (account != null) {
            System.out.print("Enter amount to withdraw: $");
            double amount = scanner.nextDouble();
            if (account.withdraw(amount)) {
                System.out.println("Withdrew: $" + amount);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private void checkBalance() {
        System.out.print("Enter account number to check balance: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccountByNumber(accountNumber);

        if (account != null) {
            System.out.println("Account Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private void viewTransactionHistory() {
        System.out.print("Enter account number to view transaction history: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccountByNumber(accountNumber);

        if (account != null) {
            account.printTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }

    private void closeAccount() {
        System.out.print("Enter account number to close: ");
        int accountNumber = scanner.nextInt();
        BankAccount account = findAccountByNumber(accountNumber);

        if (account != null) {
            accounts.remove(account);
            System.out.println("Account closed successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private BankAccount findAccountByNumber(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accounts.dat"))) {
            out.writeObject(accounts);
            System.out.println("Account data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("accounts.dat"))) {
            accounts = (ArrayList<BankAccount>) in.readObject();
            System.out.println("Account data loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BankAccountSystem bankSystem = new BankAccountSystem();
        bankSystem.start();
    }
}
