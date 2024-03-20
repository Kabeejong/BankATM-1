package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DashboardPanel extends JPanel {

    private ATMApp app;
    private User user;
    private List<Accounts> accounts;

    private JLabel welcomeLabel;
    private JComboBox<String> accountComboBox;
    private JLabel accountNumberLabel;
    private JLabel balanceLabel;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton transferButton;
    private JButton statementButton;
    private JButton logoutButton;

    public DashboardPanel(ATMApp app,User user){
        this.app = app;
        this.user = user;
        setLayout(new BorderLayout());
        setBackground(new Color(51, 102, 255));

        //welcome label
        welcomeLabel = new JLabel("welcome, " + user.getUsername() + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        //Create account details panel
        JPanel accountDetailsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        accountDetailsPanel.setBackground(new Color(51, 102, 255));
        accountDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //account label
        JLabel accountLabel = new JLabel("Account: ");
        accountLabel.setForeground(Color.WHITE);
        accountDetailsPanel.add(accountLabel);

        //combobox (dropdown list)
        accountComboBox = new JComboBox<>();

        try{
            accounts = DatabaseHelper.getAccountByUserid(user.getUserid());
            for (Accounts account : accounts) {
                accountComboBox.addItem(account.getAccountNumber());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        accountComboBox.addActionListener(new AccountComboBoxListener());
        accountDetailsPanel.add(accountComboBox);

        //accountNumber Label
        JLabel accountNumberLabelText = new JLabel("Account Number");
        accountNumberLabelText.setForeground(Color.WHITE);
        accountDetailsPanel.add(accountNumberLabelText);

        //accountNumber Label
        accountNumberLabel = new JLabel();
        accountNumberLabel.setForeground(Color.WHITE);
        accountDetailsPanel.add(accountNumberLabel);

        //balance Label text
        JLabel balanceLabelText = new JLabel("Balance: ");
        balanceLabelText.setForeground(Color.WHITE);
        accountDetailsPanel.add(balanceLabelText);

        //balance label
        balanceLabel = new JLabel();
        balanceLabel.setForeground(Color.WHITE);
        accountDetailsPanel.add(balanceLabel);

        add(accountDetailsPanel, BorderLayout.CENTER);

        //create operation button panel
        JPanel operationButtonsPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        operationButtonsPanel.setBackground(new Color(52, 102, 255));
        operationButtonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //withdraw button
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new WithdrawButtonListener());
        operationButtonsPanel.add(withdrawButton);

        //deposit button
        depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositButtonListener());
        operationButtonsPanel.add(depositButton);

        //transfer button
        transferButton = new JButton("Transfer");
        transferButton.addActionListener(new TransferButtonListener());
        operationButtonsPanel.add(transferButton);

        //statement button
       statementButton = new JButton("Statement");
       statementButton.addActionListener(new StatementButtonListener());
        operationButtonsPanel.add(statementButton);

        //logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new LogoutButtonListener());
        operationButtonsPanel.add(logoutButton);

        //add the operations button panel to thr container
        add(operationButtonsPanel, BorderLayout.SOUTH);

        //update account details if there is at least one account
        if (!accounts.isEmpty()) {
            updateAccountsDetails(0);
        }
    }
    //create the update account method
    private void updateAccountsDetails(int accountIndex){
        Accounts account = accounts.get(accountIndex);
        accountNumberLabel.setText(account.getAccountNumber());
        balanceLabel.setText(String.format("%.2f", account.getBalance()));
    }

    private class AccountComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int accountIndex = accountComboBox.getSelectedIndex();
            updateAccountsDetails(accountIndex);
        }
    }
    private  class LogoutButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            app.showPanel("Login");
        }
    }
    private class WithdrawButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int accountIndex = accountComboBox.getSelectedIndex();

            Accounts account = accounts.get(accountIndex);


            String amountInput = JOptionPane.showInputDialog(DashboardPanel.this, "Enter Amount You Wish To Withdraw:");

            if (amountInput != null) {
                try{
                    double amount = Double.parseDouble(amountInput);
                    if (amount > 0 && amount <= account.getBalance()) {
                        double newBalance = account.getBalance() - amount;
                        DatabaseHelper.updateAccountBalance(account.getAccountId(), newBalance);
                        DatabaseHelper.createTransaction(account.getAccountId(), amount, "Withdrawal");
                        account.setBalance(newBalance);
                        updateAccountsDetails(accountIndex);
                        JOptionPane.showMessageDialog(DashboardPanel.this, "Withdrawal Successful!");
                    }else{
                        JOptionPane.showMessageDialog(DashboardPanel.this, "Invalid Amount or Insufficient Balance", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    private class DepositButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int accountIndex = accountComboBox.getSelectedIndex();

            Accounts account = accounts.get(accountIndex);


            String amountInput = JOptionPane.showInputDialog(DashboardPanel.this, "Enter Amount You Wish To Deposit:");

            if (amountInput != null) {
                try{
                    double amount = Double.parseDouble(amountInput);
                    if (amount > 0 ) {
                        double newBalance = account.getBalance() + amount;
                        DatabaseHelper.updateAccountBalance(account.getAccountId(), newBalance);
                        DatabaseHelper.createTransaction(account.getAccountId(), amount, "Deposit");
                        account.setBalance(newBalance);
                        updateAccountsDetails(accountIndex);
                        JOptionPane.showMessageDialog(DashboardPanel.this, "Deposit Successful!");
                    }else{
                        JOptionPane.showMessageDialog(DashboardPanel.this, "Invalid Amount ", "Deposit Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    private class TransferButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int accountIndex = accountComboBox.getSelectedIndex();

            Accounts account = accounts.get(accountIndex);


            String amountInput = JOptionPane.showInputDialog(DashboardPanel.this, "Enter Amount You Wish To Transfer:");

            if (amountInput != null) {
                try{
                    double amount = Double.parseDouble(amountInput);
                    if (amount > 0 && amount <= account.getBalance()) {
                        String destinationAccountNumber = JOptionPane.showInputDialog(DashboardPanel.this, "Enter Destination Account Number:");
                        if(destinationAccountNumber != null) {
                            Accounts destinationAccount = DatabaseHelper.getAccountByNumber(destinationAccountNumber);
                            if (destinationAccount != null) {
                                double newSourceBalance = account.getBalance() - amount;
                                double newDestinationBalance = account.getBalance() - amount;
                                DatabaseHelper.updateAccountBalance(account.getAccountId(), newSourceBalance);
                                DatabaseHelper.updateAccountBalance(destinationAccount.getAccountId(), newDestinationBalance);
                                DatabaseHelper.createTransaction(account.getAccountId(), amount, "Transfer To " + destinationAccountNumber);
                                DatabaseHelper.createTransaction(destinationAccount.getAccountId(), amount, "Transfer From " + account.getAccountNumber());
                                account.setBalance(newSourceBalance);
                                updateAccountsDetails(accountIndex);
                                JOptionPane.showMessageDialog(DashboardPanel.this, "Transfer Successful!");
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(DashboardPanel.this, "Invalid Amount or Insufficient Balance", "Transfer Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    private class StatementButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
                int accountIndex = accountComboBox.getSelectedIndex();
                Accounts account = accounts.get(accountIndex);

                try{
                    List<Transactions> transactions = DatabaseHelper.getTransactionHistory(account.getAccountId());

                    StringBuilder statement = new StringBuilder();

                    statement.append("Transaction History For: ").append(account.getAccountNumber()).append("\n\n");

                    for (Transactions transaction : transactions) {
                        statement.append((transaction.getTimestamp())).append(" | ");
                        statement.append((transaction.getTransactionType())).append(" | ");
                        statement.append(String.format("%.2f", transaction.getAmount())).append("\n");
                    }
                    JTextArea textArea = new JTextArea(statement.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(DashboardPanel.this, scrollPane, "Transaction Statement", JOptionPane.PLAIN_MESSAGE);
                }catch(SQLException ex){
                    ex.printStackTrace();
                }

        }
    }
}


























