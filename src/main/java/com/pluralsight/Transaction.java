package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.setDescription(description);
        this.setVendor(vendor);
        this.setAmount(amount);
    }

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    public LocalDate getDate() {
        return date;
    }


    public LocalTime getTime() {
        return time;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
