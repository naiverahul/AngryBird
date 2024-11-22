package com.badlogic.drop.user;

import javax.swing.*;

public class Login_and_SignUp {
    private DataManager dataManager;
    private int loginAttempts;
    private static final int MAX_ATTEMPTS = 3;
    private User[] currentUser;

    public Login_and_SignUp(User[] currentUser) {
        this.dataManager = DataManager.getInstance();
        this.loginAttempts = 0;
        this.currentUser = currentUser;
    }

    public void showLoginSignupDialog() {
        while (true) {
            String[] options = {"Login", "Signup", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an option:",
                "Login/Signup",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) { // Login
                handleLogin();
            } else if (choice == 1) { // Signup
                handleSignup();
            } else { // Exit
                DataManager.getInstance().saveData();
                System.exit(0);
            }
        }
    }

    private void handleLogin() {
        String name = JOptionPane.showInputDialog(null, "Enter your name:", "Login", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dataManager.userExists(name)) {
            loginAttempts = 0;
            JOptionPane.showMessageDialog(null, "Login successful! Welcome back, " + name + " Current_level: " + dataManager.getUser(name).getLevel(), "Success", JOptionPane.INFORMATION_MESSAGE);
            currentUser[0] = dataManager.getUser(name);
            return;
        } else {
            loginAttempts++;
            if (loginAttempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(null, "Maximum login attempts exceeded. Please contact the admin.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Attempt " + loginAttempts + " of " + MAX_ATTEMPTS, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleSignup() {
        String name = JOptionPane.showInputDialog(null, "Enter your name:", "Signup", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dataManager.userExists(name)) {
            JOptionPane.showMessageDialog(null, "User already exists. Please login.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            dataManager.addUser(name);
            JOptionPane.showMessageDialog(null, "Signup successful! Welcome, " + name + " Current level : 1", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
