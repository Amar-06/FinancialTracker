package FinancialTracker;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

public class PersonalFinacialTracker {
    static Scanner sc = new Scanner(System.in);
    private static List<Transaction> list = new ArrayList<>();
    private static final String file_name = "data.csv";
    private static final String backup_file = "backup.csv";
    private static final String passwordFile = "password.txt";  
    private static String password = "admin123";  
    private static int tries = 3;

    public static void main(String[] args) {
        loadPassword();
        if (!authenticate()) {
            return;
        }
        importFromCSV();

        while (true) {
            System.out.println("\n1. Change password.");
            System.out.println("2. Add Transaction");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. Category Summary");
            System.out.println("6. Monthly Summary");
            System.out.println("7. Search Transactions");
            System.out.println("8. Delete Transaction");
            System.out.println("9. Update Transaction");
            System.out.println("10. Export to CSV");
            System.out.println("11. Import from CSV");
            System.out.println("12. Backup Transactions");
            System.out.println("13. Restore Backup");
            System.out.println("14. Sort Transactions");
            System.out.println("15. Insights");
            System.out.println("16. Budgeting Tips");
            System.out.println("17. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> changePassword();
                case 2 -> addTransaction();
                case 3 -> viewTransaction();
                case 4 -> viewBalance();
                case 5 -> categorySummary();
                case 6 -> monthlySummary();
                case 7 -> searchTransaction();
                case 8 -> deleteTransaction();
                case 9 -> updateTransaction();
                case 10 -> toCSV();
                case 11 -> importFromCSV();
                case 12 -> backup();
                case 13 -> restore();
                case 14 -> sortTransactions();
                case 15 -> showInsights();
                case 16 -> budgetingTips();
                case 17 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static boolean authenticate() {
        for (int i = 0; i < tries; i++) {
            System.out.print("Enter password: ");
            String input = sc.nextLine();
            if (input.equals(password)) {
                System.out.println("Access granted.");
                return true;
            } else {
                System.out.println("Wrong password. You tried " + (i + 1) + " times.");
                if (i < tries - 1) {
                    System.out.println("Try again.");
                }
                if (i == tries - 1) {
                    System.out.println("You exceeded total tries.");
                }
            }
        }
        return false;
    }

    static void loadPassword() {
        try (BufferedReader br = new BufferedReader(new FileReader(passwordFile))) {
            String savedPassword = br.readLine();
            if (savedPassword != null && !savedPassword.isBlank()) {
                password = savedPassword.trim();
            }
        } catch (IOException e) {
            
            password = "admin123";
        }
    }

    static void savePassword(String newPassword) {
        try (FileWriter fw = new FileWriter(passwordFile)) {
            fw.write(newPassword);
        } catch (IOException e) {
            System.out.println("Error saving password: " + e.getMessage());
        }
    }

    private static boolean changePassword() {
        System.out.println("To change password, verify your old password.");
        boolean accessGranted = false;
        for (int i = 0; i < tries; i++) {
            System.out.print("Enter old password: ");
            String oldPassword = sc.nextLine();
            if (oldPassword.equals(password)) {
                System.out.println("Access granted. You can change password.");
                accessGranted = true;
                break;
            } else {
                System.out.println("Wrong password. You tried " + (i + 1) + " times.");
                if (i < tries - 1) {
                    System.out.println("Try again.");
                }
                if (i == tries - 1) {
                    System.out.println("You exceeded total tries.");
                    return false;
                }
            }
        }

        if (accessGranted) {
            System.out.println("Enter new password (exactly 8 characters): ");
            while (true) {
                String newPassword = sc.nextLine();
                if (Pattern.matches("^.{8}$", newPassword)) {
                    password = newPassword;
                    savePassword(password);
                    System.out.println("Password changed successfully.");
                    break;
                } else {
                    System.out.println("Password must contain exactly eight characters. Try again:");
                }
            }
        }
        return accessGranted;
    }
    static void addTransaction() {
        String date, type, category, note;
        double amount = 0.0;

        while (true) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            date = sc.next();
            try {
                LocalDate.parse(date);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format or value!");
            }
        }

        while (true) {
            System.out.print("Enter type (Expense/Income): ");
            type = sc.next();
            if (type.equalsIgnoreCase("Expense") || type.equalsIgnoreCase("Income")) break;
            else System.out.println("Invalid type!");
        }

        sc.nextLine();
        while (true) {
            System.out.print("Enter category (letters and spaces only): ");
            category = sc.nextLine();
            if (Pattern.matches("[a-zA-Z ]+", category)) break;
            else System.out.println("Invalid category!");
        }

        while (true) {
            System.out.print("Enter amount ₹: ");
            try {
                amount = sc.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid amount!");
                sc.next();
            }
        }

        sc.nextLine();
        System.out.print("Enter a note: ");
        note = sc.nextLine();

        list.add(new Transaction(date, type, category.trim(), amount, note));
        System.out.println("Transaction added.");
    }

    static void viewTransaction() {
        if (list.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        System.out.printf("%-5s %-12s %-10s %-15s %-10s %-20s\n", "No", "Date", "Type", "Category", "Amount", "Note");
        int i = 1;
        for (Transaction t : list) {
            System.out.printf("%-5d %-12s %-10s %-15s ₹%-9.2f %-20s\n", i++, t.date, t.type, t.category, t.amount, t.note);
        }
    }

    static void viewBalance() {
        double income = 0, expense = 0;
        for (Transaction t : list) {
            if (t.type.equalsIgnoreCase("income")) income += t.amount;
            else expense += t.amount;
        }
        System.out.printf("Income: ₹%.2f | Expense: ₹%.2f | Balance: ₹%.2f\n", income, expense, income - expense);
    }

    static void categorySummary() {
        System.out.println("Category Summary (Expenses only):");
        Set<String> seen = new HashSet<>();
        for (Transaction t : list) {
            if (t.type.equalsIgnoreCase("expense") && !seen.contains(t.category)) {
                double sum = 0;
                for (Transaction t1 : list) {
                    if (t1.category.equalsIgnoreCase(t.category) && t1.type.equalsIgnoreCase("expense")) {
                        sum += t1.amount;
                    }
                }
                seen.add(t.category);
                System.out.printf("%s: ₹%.2f\n", t.category, sum);
            }
        }
    }

    static void monthlySummary() {
        System.out.println("Monthly Summary:");
        Set<String> seen = new HashSet<>();
        for (Transaction t : list) {
            String month = t.date.substring(0, 7);
            if (!seen.contains(month)) {
                double sum = 0;
                for (Transaction t1 : list) {
                    if (t1.date.startsWith(month)) {
                        sum += (t1.type.equalsIgnoreCase("income") ? t1.amount : -t1.amount);
                    }
                }
                seen.add(month);
                System.out.printf("%s: ₹%.2f\n", month, sum);
            }
        }
    }

    static void searchTransaction() {
        System.out.println("Search by 1. Date or 2. Category:");
        int ch = sc.nextInt();
        sc.nextLine();
        boolean found = false;
        if (ch == 1) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            for (Transaction t : list) {
                if (t.date.equals(date)) {
                    System.out.println(t);
                    found = true;
                }
            }
        } else if (ch == 2) {
            System.out.print("Enter category: ");
            String category = sc.nextLine();
            for (Transaction t : list) {
                if (t.category.equalsIgnoreCase(category)) {
                    System.out.println(t);
                    found = true;
                }
            }
        }
        if (!found) System.out.println("No match found.");
    }

    static void deleteTransaction() {
        viewTransaction();
        System.out.print("Enter transaction no. to delete: ");
        int i = sc.nextInt();
        sc.nextLine();
        if (i > 0 && i <= list.size()) {
            Transaction removed = list.remove(i - 1);
            System.out.println("Deleted: " + removed);
        } else System.out.println("Invalid index.");
    }

    static void updateTransaction() {
        viewTransaction();
        System.out.print("Enter transaction no. to update: ");
        int i = sc.nextInt();
        sc.nextLine();
        if (i > 0 && i <= list.size()) {
            list.remove(i - 1);
            System.out.println("Re-enter transaction:");
            addTransaction();
        } else System.out.println("Invalid index.");
    }

    static void toCSV() {
        try (FileWriter fw = new FileWriter(file_name)) {
            for (Transaction t : list) {
                fw.write(t.toCSV() + "\n");
            }
            System.out.println("Exported to " + file_name);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    static void importFromCSV() {
        list.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5)
                    list.add(new Transaction(data[0], data[1], data[2], Double.parseDouble(data[3]), data[4]));
            }
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }

    static void backup() {
        try (FileWriter fw = new FileWriter(backup_file)) {
            for (Transaction t : list) fw.write(t.toCSV() + "\n");
            System.out.println("Backup created.");
        } catch (Exception e) {
            System.out.println("Backup failed.");
        }
    }

    static void restore() {
        System.out.print("This will overwrite current data. Are you sure? (yes/no): ");
        String confirm = sc.nextLine().toLowerCase();
        if (!confirm.equals("yes")) return;
        try (BufferedReader br = new BufferedReader(new FileReader(backup_file))) {
            String line;
            list.clear();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                list.add(new Transaction(d[0], d[1], d[2], Double.parseDouble(d[3]), d[4]));
            }
            System.out.println("Backup restored.");
        } catch (Exception e) {
            System.out.println("No backup to restore.");
        }
    }

    static void sortTransactions() {
        System.out.println("Sort by:\n1. Date\n2. Amount\n3. Category");
        int c = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                boolean swap = false;
                if (c == 1 && list.get(i).date.compareTo(list.get(j).date) > 0) swap = true;
                else if (c == 2 && list.get(i).amount > list.get(j).amount) swap = true;
                else if (c == 3 && list.get(i).category.compareTo(list.get(j).category) > 0) swap = true;
                if (swap) Collections.swap(list, i, j);
            }
        }
        viewTransaction();
    }

    static void showInsights() {
        if (list.isEmpty()) return;
        Transaction maxInc = null, maxExp = null;
        for (Transaction t : list) {
            if (t.type.equalsIgnoreCase("income")) {
                if (maxInc == null || t.amount > maxInc.amount) maxInc = t;
            } else {
                if (maxExp == null || t.amount > maxExp.amount) maxExp = t;
            }
        }
        if (maxInc != null)
            System.out.println("Highest income: " + maxInc);
        if (maxExp != null)
            System.out.println("Highest expense: " + maxExp);
    }

    static void budgetingTips() {
        double income = 0, entertainment = 0;
        for (Transaction t : list) {
            if (t.type.equalsIgnoreCase("income")) income += t.amount;
            else if (t.category.equalsIgnoreCase("entertainment")) entertainment += t.amount;
        }
        if (income > 0) {
            double perc = (entertainment / income) * 100;
            if (perc > 40) System.out.printf("\u26a0 You spent %.1f%% of your income on entertainment. Try reducing it.\n", perc);
            else System.out.printf("\u2705 Entertainment spending is within limits (%.1f%%).\n", perc);
        }
    }
}

class Transaction {
    String date, type, category, note;
    double amount;

    Transaction(String date, String type, String category, double amount, String note) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.note = note;
    }

    public String toString() {
        return date + "\t" + type + "\t" + category + "\t₹" + amount + "\t" + note;
    }

    public String toCSV() {
        return date + "," + type + "," + category + "," + amount + "," + note;
    }
}