package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bankDB";
    private static final String DB_USER ="postgres";
    private static final String DB_PASSWORD ="Kabeejong";

    //a method to get a user by their username
    public static User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userid = rs.getInt("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");

                return new User(userid, username, password, name);

            }
        }
        return null;
    }

    //a method to get an account by user_id
    public static List<Accounts> getAccountByUserid(int userId)throws SQLException{
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        List<Accounts> accounts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("accountId");
                String accountNumber = rs.getString("account_number");
                double balance = rs.getDouble("balance");

                accounts.add(new Accounts(accountId, accountNumber, balance, userId));
            }
        }
        return accounts;
    }
    //a method to create a transaction
    public static void createTransaction(int accountId, double amount, String transactionType)throws SQLException{
        String sql = "INSERT INTO transactions (account_id, amount, transaction_type, timestamp) VALUES(?, ?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            stmt.setDouble(2, amount);
            stmt.setString(3, transactionType);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        }
    }
    //a method to update an account balance
    public static void updateAccountBalance(int accountId, double newBalance)throws SQLException{

        String sql = "UPDATE accounts SET BALANCE = ? WHERE account_id =?";

        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }

    //a method to get transaction history
    public static  List<Transactions> getTransactionHistory(int accountId)throws  SQLException{
        String sql = "SELECT * FROM transactions WHERE account_id =?";
        List<Transactions> transactions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                double amount = rs.getDouble("amount");
                String transactionType = rs.getString("transaction_type");
                LocalDateTime timeStamp = rs.getTimestamp("timestamp").toLocalDateTime();
                transactions.add(new Transactions(transactionId, accountId, amount, transactionType, timeStamp));
            }
        }
        return transactions;
    }

    //a method to get an account by account number
    public static Accounts getAccountByNumber(String accountNumber)throws SQLException{
        String sql = "SELECT * FROM accounts WHERE account_number  = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();


                if (rs.next()) {
                    int accountId = rs.getInt("account_id");
                    double balance = rs.getDouble("balance");
                    int userId = rs.getInt("user_id");
                    return  new Accounts(accountId, accountNumber, balance, userId);
            }
        }
        return null;
    }

    //a method to create a user
    public static void createUser(String username, String password, String name)throws SQLException{
        String sql = "INSERT INTO users (username, password, name) VALUES(?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        }
    }

    //a method to create  a user account
    public static void createAccount(int userId, String accountNumber)throws SQLException{
        String sql = "INSERT INTO accounts (user_id, account_number, balance) VALUES(?, ?, 0.0)";

        try(Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
        }
    }

}





















