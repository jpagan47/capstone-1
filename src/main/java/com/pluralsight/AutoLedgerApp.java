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
    public static String filePath = "src/main/resources/transactions.csv";
    //Declaring my Scanner
    public static Scanner myScanner = new Scanner(System.in);
    //Declaring my ArrayList
    public static ArrayList<Transaction> transactionsList = new ArrayList<>();
    //Declaring my ArrayList
//    public static HashMap<String, Transaction> transMap = new HashMap<String, Transaction>();
    //Declaring my File and Buffer readers
    public static FileReader fileReader;
    public static BufferedReader bufferedReader;
    //Declaring my File and Buffered writer
    public static FileWriter fileWriter;
    public static BufferedWriter bufferedWriter;
    //Declaring my Date Time Formatter
    public static DateTimeFormatter dateTimeFormatter;
    public static LocalDate localDate ;
    public static LocalTime localTime ;
    //Declaring my Prompts
    public static String mainMenuPrompt = """
            🏁............🏎💨..
            ===== Main Menu =====
            (D) Add Deposit
            (P) Make Payment (Debit)
            (L) Ledger
            (X) Exit
            🏎️. ݁₊ ⊹ . ݁˖ .
            """;
    public static String ledgerMenuPrompt = """
            === Ledger Menu ===
            (A) View All
            (D) View Only Deposits
            (P) View Only Payments
            (R) Custom Report Search
            (H) Back To Home Page
            """;
    public static String customReportPrompt = """
            === Custom Report ===
            (1) Month to Date
            (2) Previous Month
            (3) Year to Date
            (4) Previous Year
            (5) Search By Vendor
            (H) Back to Home Page
            """;

    //All my code that needs to run
    public static void main(String[] args) {
        loadTransactions(filePath);
        mainMenu();
        System.out.println("End of Application");

    }

    public static void loadTransactions(String fileName) {
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            try {
                String line = bufferedReader.readLine();
                line = bufferedReader.readLine();
                while (line != null) {
                    String[] parts = line.split("\\|");
                    String date = parts[0];
                    String time = parts[1];
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
        String date = myScanner.nextLine();
        dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        localDate = LocalDate.parse(date, dateTimeFormatter);
        //Time Formatting
        System.out.println("Please Enter the Time of the Deposit (HH:mm:ss) :");
        String time = myScanner.nextLine();
        dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse(time, dateTimeFormatter);

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
            fileWriter = new FileWriter(filePath, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            bufferedWriter.close();
            loadTransactions(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void makePayment() {
        //Date Formatting
        System.out.println("Please Enter the Date of the Payment (MM-dd-yyyy) :");
        String date = myScanner.nextLine();
        dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        localDate = LocalDate.parse(date, dateTimeFormatter);
        //Time Formatting
        System.out.println("Please Enter the Time of the Payment (HH:mm:ss) :");
        String time = myScanner.nextLine();
        dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.parse(time, dateTimeFormatter);

        System.out.println("Please Enter a short Description of the Payment:");
        String description = myScanner.nextLine();

        System.out.println("Please Enter the Vendor Name:");
        String vendor = myScanner.nextLine();
        double amount = 0;
        do {
            System.out.println("Please Enter the Amount of the Payment:");
            amount = Double.parseDouble(myScanner.nextLine());
            if (amount >= 0) {
                System.err.println("The payment must be a negative value! Try again");
            }
        } while (amount >= 0);
        try {
            fileWriter = new FileWriter(filePath, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            bufferedWriter.close();
            loadTransactions(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void ledgerMenu() {
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
                break;
            default:
                System.err.println("You did not enter a valid input");
        }
    }

    private static void displayAllTransactions() {
        //todo - Display all entries SORTED
//        transactionsList.sort(Comparator.comparing(Transaction::getDate));
        printOutHeader();
//        transactionsList.sort(Comparator.comparing(transaction -> LocalDate.parse(transaction.getDate(),DateTimeFormatter.ofPattern("MM/dd/yyyy"))).thenComparing(t -> LocalTime.parse(t.getTime(),DateTimeFormatter.ofPattern("HH:mm:ss"))));
        for (Transaction t: transactionsList) {
            System.out.printf("%-10s %-10s %-28s %-22s %.2f %n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }

    private static void displayAllDeposits() {
        //For only Deposits
        printOutHeader();
        for (Transaction t : transactionsList) {
            if (t.getAmount() > 0) {
                System.out.printf("%-10s %-10s %-28s %-22s $%.2f %n", t.getDate() , t.getTime() , t.getDescription() , t.getVendor() , t.getAmount());

            }
        }
    }
    private static void displayAllPayments() {
        //For only Payments
        printOutHeader();
        for (Transaction t : transactionsList) {
            if (t.getAmount() < 0) {
                System.out.printf("%-10s %-10s %-28s %-22s $%.2f %n", t.getDate() , t.getTime() , t.getDescription() , t.getVendor() , t.getAmount());
            }
        }
    }

    private static void customReportSearch() {
        //todo- A new screen that allows the user to run pre-defined reports or to run a custom search
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
                break;

            default:
                System.err.println("You did not enter a valid input");
        }
    }

    private static void displayThisMonthsTrans() {
    }

    private static void displayLastMonthTrans() {
    }

    private static void displayCurrentYearTrans() {
    }

    private static void displayLastYearTrans() {
    }

    private static void searchByVendor() {
    }
    private static void printOutHeader(){
        System.out.printf("%-10s %-10s %-28s %-22s %s %n","Date" , "Time" , " Description" , "Vendor", "Amount");
        System.out.println("=================================================================================");
    }


}
