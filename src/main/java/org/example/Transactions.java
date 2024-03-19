package org.example;

import java.time.LocalDateTime;

public class Transactions {
    //ATTRIBUTES
    private int transactionId;
    private int accountId;
    private double amount;
    private String transactionType;
    private LocalDateTime timestamp;

    //constructor


    public Transactions(int transactionId, int accountId, double amount, String transactionType, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


}























