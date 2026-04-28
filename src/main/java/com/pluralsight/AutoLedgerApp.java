package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class AutoLedgerApp {
    //Class Level Field Variables
    //Path for the file I am reading (transactions.csv)
    public static String FILE_PATH = "src/main/resources/transactions.csv";
    //Declaring my Scanner
    public static Scanner myScanner = new Scanner(System.in);
    //Declaring my ArrayList
    public static ArrayList<Transaction> transactionsList = new ArrayList<>();
    //Declaring my File and Buffer readers
    //Declaring my File and Buffered writer
    public static FileWriter fileWriter;
    public static BufferedWriter bufferedWriter;
    //Declaring my Date Time Formatter
    public static DateTimeFormatter dateTimeFormatter;
    public static LocalDate localDate;
    public static LocalTime localTime;

    //All my code that needs to run
    public static void main(String[] args) {
        loadTransactions(FILE_PATH);
        mainMenu();
        System.out.println("End of Application");
    }

    public static void loadTransactions(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                String line = bufferedReader.readLine();
                line = bufferedReader.readLine();
                while (line != null) {
                    String[] parts = line.split("\\|");
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactionsList.add(transaction);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            System.err.println("The file with the path " + fileName + " was not found!");
            throw new RuntimeException(e);
        }
    }

    public static void mainMenu() {
        String mainMenuPrompt = """
            🏁............🏎💨..
            ===== Main Menu =====
            (D) Add Deposit
            (P) Make Payment (Debit)
            (L) Ledger
            (X) Exit
            🏎️. ݁₊ ⊹ . ݁˖ .
            """;
        boolean running = true;
        do {
            System.out.println(mainMenuPrompt);
            String userInput = myScanner.nextLine();
            userInput = userInput.toUpperCase();
            switch (userInput) {
                case "D":
                    addDeposit();
                    System.out.println("Success!");
                    break;
                case "P":
                    makePayment();
                    System.out.println("Success!");
                    break;
                case "L":
                    ledgerMenu();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.err.println("You did not enter a valid input");
            }
        } while (running);
    }

    private static void addDeposit() {
        //Date Formatting
        System.out.println("Please Enter the Date of the Deposit (MM-dd-yyyy) :");
        String userInput = myScanner.nextLine();

        dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        localDate = LocalDate.parse(userInput, dateTimeFormatter);
        //Time Formatting
        System.out.println("Please Enter the Time of the Deposit (HH:mm:ss) :");
        userInput = myScanner.nextLine();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse(userInput, timeFormatter);

        System.out.println("Please Enter a short Description of the Deposit:");
        String description = myScanner.nextLine();

        System.out.println("Please Enter the Vendor Name:");
        String vendor = myScanner.nextLine();
        double amount = 0;
        do {
            System.out.println("Please Enter the Amount of the Payment: ");
            amount = Double.parseDouble(myScanner.nextLine());
            if (amount <= 0) {
                System.err.println("The payment must be a positive value! Try again");
            }
        } while (amount <= 0);
        try {
            fileWriter = new FileWriter(FILE_PATH, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(localDate + "|" + localTime.format(timeFormatter) + "|" + description + "|" + vendor + "|" + amount);
            bufferedWriter.close();
            loadTransactions(FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void makePayment() {
        //Date Formatting
        System.out.println("Please Enter the Date of the Payment (MM-dd-yyyy) :");
        String userInput = myScanner.nextLine();

        dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        localDate = LocalDate.parse(userInput, dateTimeFormatter);
        //Time Formatting
        System.out.println("Please Enter the Time of the Payment (HH:mm:ss) :");
        userInput = myScanner.nextLine();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse(userInput, timeFormatter);

        System.out.println("Please Enter a short Description of the Payment:");
        String description = myScanner.nextLine();

        System.out.println("Please Enter the Vendor Name:");
        String vendor = myScanner.nextLine();
        double amount = 0;
        do {
            System.out.println("Please Enter the Amount of the Payment: ");
            amount = Double.parseDouble(myScanner.nextLine());
            if (amount >= 0) {
                System.err.println("The payment must be a negative value! Try again");
            }
        } while (amount >= 0);
        try {
            fileWriter = new FileWriter(FILE_PATH, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(localDate + "|" + localTime.format(timeFormatter) + "|" + description + "|" + vendor + "|" + amount);
            bufferedWriter.close();
            loadTransactions(FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ledgerMenu() {
        String ledgerMenuPrompt = """
            === Ledger Menu ===
            (A) View All
            (D) View Only Deposits
            (P) View Only Payments
            (R) Custom Report Search
            (H) Back To Home Page
            """;
        boolean running = true;
        do {
            System.out.println(ledgerMenuPrompt);
            String userInput = myScanner.nextLine();
            userInput = userInput.toUpperCase();
            switch (userInput) {
                case "A"://All - Display all entries
                    displayAllTransactions();
                    break;
                case "D"://Deposits - Display only the entries that are deposits into the account
                    displayAllDeposits();
                    break;
                case "P"://Payments - Display only the negative entries (or payments
                    displayAllPayments();
                    break;
                case "R"://R) Reports - A new screen that allows the user to run pre-defined reports or to run a custom search
                    customReportSearch();
                    break;
                case "H":
//              backToHomePage();
                    running = false;
                    break;
                default:
                    System.err.println("You did not enter a valid input");
            }
        } while (running);
    }

    private static void displayAllTransactions() {

        printOutHeader();

        transactionsList.sort(Comparator.comparing(Transaction::getDate).reversed());
        for (Transaction t : transactionsList) {
            formatedOutput(t);
        }
        endOfTransactions();
    }

    private static void displayAllDeposits() {
        //For only Deposits
        printOutHeader();
        for (Transaction t : transactionsList) {
            if (t.getAmount() > 0) {
                formatedOutput(t);

            }
        }
        endOfTransactions();
    }

    private static void displayAllPayments() {
        //For only Payments
        printOutHeader();
        for (Transaction t : transactionsList) {
            if (t.getAmount() < 0) {
                formatedOutput(t);
            }
        }
        endOfTransactions();
    }

    private static void customReportSearch() {
        String customReportPrompt = """
            === Custom Report ===
            (1) Month to Date
            (2) Previous Month
            (3) Year to Date
            (4) Previous Year
            (5) Search By Vendor
            (0) Back to Ledger
            """;
        boolean running = true;
        do {
            System.out.println(customReportPrompt);
            String userInput = myScanner.nextLine();
            userInput = userInput.toUpperCase();
            switch (userInput) {
                case "1":
                    displayThisMonthsTrans();
                    break;
                case "2":
                    displayLastMonthTrans();
                    break;
                case "3":
                    displayCurrentYearTrans();
                    break;
                case "4":
                    displayLastYearTrans();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.err.println("You did not enter a valid input");
            }
        } while (running);
    }

    private static void displayThisMonthsTrans() {
        LocalDate today = LocalDate.now();
        printOutHeader();
        boolean found = false;
        for (Transaction t : transactionsList) {
            LocalDate transDate = t.getDate();
            if (transDate.getMonth() == today.getMonth() && transDate.getYear() == today.getYear()) {
                formatedOutput(t);
            }
            found = true;
        }
        if (!found) {
            System.out.println("There is no Transactions this month");
        }
        endOfTransactions();
    }

    private static void displayLastMonthTrans() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonthDate = today.minusMonths(1);
        printOutHeader();
        boolean found = false;
        for (Transaction t : transactionsList) {
            LocalDate transDate = t.getDate();
            if (transDate.getMonth() == lastMonthDate.getMonth() && transDate.getYear() == lastMonthDate.getYear()) {
                formatedOutput(t);
                found = true; // ✅ mark that we found something
            }
        }
        if (!found) {
            System.out.println("There is no Transactions this month");
        }
        endOfTransactions();
    }

    private static void displayCurrentYearTrans() {
        LocalDate today = LocalDate.now();
        LocalDate january = today.withMonth(1).withDayOfMonth(1);
        printOutHeader();
        boolean found = false;
        for (Transaction t : transactionsList) {
            LocalDate transDate = t.getDate();
            /* Checking if trans date is = january of this year and checking if its after january
             * then checking at the same time if the trans date is on today or before. All nested in an if statement.
             * If params are meet anything in between Jan 1st and today will print*/
            if ((transDate.isEqual(january) || transDate.isAfter(january)) && (transDate.isBefore(today)) || transDate.isEqual(today)) {
                formatedOutput(t);

                found = true; // ✅ mark that we found something
            }
        }

        if (!found) {
            System.out.println("There is no Transactions this year");
        }
        endOfTransactions();
    }

    private static void displayLastYearTrans() {
        LocalDate today = LocalDate.now();
        int lastYear = today.getYear() - 1;
        LocalDate january = today.withMonth(1).withDayOfMonth(1);
        printOutHeader();
        boolean found = false;
        for (Transaction t : transactionsList) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getYear() == lastYear) {
                LocalDate transDate = t.getDate();
                LocalTime transTime = t.getTime();
                formatedOutput(t);
                found = true; // ✅ mark that we found something

            }
        }
        if (!found) {
            System.out.println("There is no Transactions this year");
        }
        endOfTransactions();
    }

    private static void endOfTransactions() {
        System.out.println("\n");
        System.out.println("====== End of Transactions ======");
        System.out.println("\n");
    }

    private static void searchByVendor() {
        System.out.println("Please Enter Vendor Name :");
        String useInput = myScanner.nextLine();
        boolean found = false;
        for (Transaction t : transactionsList) {
            if (t.getVendor().toLowerCase().contains(useInput.toLowerCase())) {
                formatedOutput(t);
                found = true;
            }
        }
        if (!found) {
            System.err.println("That Vendor is not on the Ledger");
        }
        endOfTransactions();
    }

    private static void printOutHeader() {
        System.out.printf("%-10s %-10s %-28s %-22s %s %n", "Date", "Time", " Description", "Vendor", "Amount");
        System.out.println("=================================================================================");
    }

    private static void formatedOutput(Transaction t) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String localTimeWithSec = t.getTime().format(timeFormatter);
        System.out.printf("%-10s %-10s %-28s %-22s %.2f %n", t.getDate(), localTimeWithSec, t.getDescription(), t.getVendor(), t.getAmount());
    }
}
