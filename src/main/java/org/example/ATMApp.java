package org.example;

import javax.swing.*;
import java.awt.*;

public class ATMApp extends JFrame {
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private RegistrationPanel registrationPanel;
    private User user;

    public ATMApp() {
        setTitle("CPL Bank ATM");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new CardLayout());
        loginPanel = new LoginPanel(this);
        registrationPanel = new RegistrationPanel();

        mainPanel.add(loginPanel, "login");
        // mainPanel.add(registrationPanel, "Registration");

        add(mainPanel, BorderLayout.CENTER);
        showPanel("Login");
    }

    public void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, panelName);
    }

    public void setUser(User user) {
        this.user = user;

        if (user != null) {
            if (dashboardPanel != null) {
                mainPanel.remove(dashboardPanel);
            }

            dashboardPanel = new DashboardPanel();
            mainPanel.add(dashboardPanel, "Dashboard");
            showPanel("Dashboard");
        }
    }
}
























