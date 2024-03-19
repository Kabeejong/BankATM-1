package org.example;

public class Accounts {
    //attributes
    private int accountId;
    private String accountNumber;
    private double balance;
    private int userId;

    //constructor

    public Accounts(int accountId, String accountNumber, double balance, int userId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}





























