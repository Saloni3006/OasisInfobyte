import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactions;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public void displayTransactions() {
        if (transactions.isEmpty()) {
            System.out.println();
            System.out.println("No transactions to display...");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(String type, double amount, String timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Type: " + type + ", Amount: " + amount + ", Date/Time: " + timestamp;
    }
}

class Account {
    private User user;

    public Account(User user) {
        this.user = user;
    }

    public void deposit(double amount) {
        user.updateBalance(amount);
        user.addTransaction(new Transaction("Deposit", amount, java.time.LocalDateTime.now().toString()));
        System.out.println("Deposit successful. New Balance: " + user.getBalance());
    }

    public void withdraw(double amount) {
        if (amount <= user.getBalance()) {
            user.updateBalance(-amount);
            user.addTransaction(new Transaction("Withdraw", amount, java.time.LocalDateTime.now().toString()));
            System.out.println("Withdrawal successful. New Balance: " + user.getBalance());
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void displayBalance() {
        System.out.println("Current Balance: " + user.getBalance());
    }
}

class Bank {
    private ArrayList<User> users;

    public Bank() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User authenticateUser(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.validatePin(pin)) {
                return user;
            }
        }
        return null;
    }

    public void transfer(User fromUser, String toUserId, double amount) {
        User toUser = null;
        for (User user : users) {
            if (user.getUserId().equals(toUserId)) {
                toUser = user;
                break;
            }
        }

        if (toUser == null) {
            System.out.println("Transfer failed: Recipient not found.");
        } else if (amount > fromUser.getBalance()) {
            System.out.println("Transfer failed: Insufficient balance.");
        } else {
            fromUser.updateBalance(-amount);
            toUser.updateBalance(amount);
            fromUser.addTransaction(new Transaction("Transfer to " + toUserId, amount, java.time.LocalDateTime.now().toString()));
            toUser.addTransaction(new Transaction("Transfer from " + fromUser.getUserId(), amount, java.time.LocalDateTime.now().toString()));
            System.out.println("Transfer successful. New Balance: " + fromUser.getBalance());
        }
    }
}

public class ATM_Interface{
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();

    public static void main(String[] args) {
        // Sample users for testing
        bank.addUser(new User("Saloni", "1234", 3000.0));
        bank.addUser(new User("Haruka", "5678", 6000.0));

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User currentUser = bank.authenticateUser(userId, pin);

        if (currentUser != null) {
            Account account = new Account(currentUser);
            int option;
            do {
                showMenu();
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (option) {
                    case 1:
                        currentUser.displayTransactions();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient User ID: ");
                        String toUserId = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        bank.transfer(currentUser, toUserId, transferAmount);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (option != 5);
        } else {
            System.out.println("Authentication failed. Please check your User ID and PIN.");
        }
    }

    private static void showMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw ");
        System.out.println("3. Deposit ");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Choose an option: ");
    }
}

