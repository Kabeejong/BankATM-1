package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private ATMApp app;

    public LoginPanel (ATMApp APP) {
        this.app = app;
        setLayout(new GridBagLayout());

        //set background color
        setBackground(new Color(51, 102, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        //add logo image
        JLabel logoLabel = new JLabel(new ImageIcon("logo.png"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(logoLabel, gbc);

        JLabel usernameLabel = new JLabel(("Enter Username:"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Enter Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        JButton loginButton = new JButton("login");
        loginButton.addActionListener(new LoginButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(registerButton, gbc);
    }
        private class LoginButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    User user = DatabaseHelper.getUserByUsername(username);
                    if (user != null && user.getPassword().equals(password)) {
                        //app.setUser();
                        app.showPanel("Dashboard");
                    }else {
                        JOptionPane.showMessageDialog(LoginPanel.this, "Invalid Username or Password!", " Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginPanel.this, "Error Logging In", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        private class RegisterButtonListener implements ActionListener {
        @Override
            public void actionPerformed(ActionEvent e){
            app.showPanel("Registration");
        }
    }



    }



















