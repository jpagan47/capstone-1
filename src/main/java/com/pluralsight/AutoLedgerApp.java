package com.pluralsight;

import java.util.Scanner;

public class AutoLedgerApp {
    //Class Level Field Variables
    //Path for the file I am reading (transactions.csv)
    public static String filePath = "src/main/resources/transactions.csv";
    public static Scanner myScanner = new Scanner(System.in);
    public static String mainMenuPrompt = """
            🏁............🏎💨..
            ===== Main Menu =====
            (D) Add Deposit
            (P) Make Payment (Debit)
            (L) Ledger
            (X) Exit
            """;
    public static String ledgerMenuPrompt = """
            === Ledger Menu ===
            (A) View All
            (D) View  Only Deposits
            (P) View Only Payments
            (R) Custom Report Search
            (H) Back To Home Page
            """;

    //All my code that needs to run
    public static void main(String[] args) {
        mainMenu();
        System.out.println("End of Application");

    }

    public static void mainMenu() {
        boolean running = true;
        do {
            System.out.println(mainMenuPrompt);
            //todo Add a .ignorecase somehow into the usersInput
            String userInput = myScanner.nextLine();
            switch (userInput) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
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
        //todo prompt user for the deposit information and save it to the csv file
    }

    private static void makePayment() {
        //todo prompt user for the debit information and save it to the csv file
    }

    private static void ledgerMenu() {
        System.out.println(ledgerMenuPrompt);
        //todo Add a .ignorecase somehow into the usersInput
        String userInput = myScanner.nextLine();
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
        }


    }

    private static void displayAllTransactions() {
        //todo - Display all entries
    }

    private static void displayAllDeposits() {
       //todo - Display only the entries that are deposits into the account
    }

    private static void displayAllPayments() {
        //todo  - Display only the negative entries (or payments)
    }

    private static void customReportSearch() {
        //todo- A new screen that allows the user to run pre-defined reports or to run a custom search
        String customReportPrompt = """
                === Custom Report ===
                (1) Month to Date
                (2) Previous Month
                (3) Year to Date
                (4) Previous Year
                (5) Search By Vendor
                (H) Back to Home Page
                """;
        System.out.println(customReportPrompt);
        String userInput  = myScanner.nextLine();
        //todo Add a .ignorecase somehow into the usersInput
        switch (userInput){
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


}
