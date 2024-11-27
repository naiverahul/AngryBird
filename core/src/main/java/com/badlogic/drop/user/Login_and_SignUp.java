package com.badlogic.drop.user;

import com.badlogic.drop.MyGame;

import javax.swing.*;

public class Login_and_SignUp {
    private final DataManager dataManager;
    private int loginAttempts;
    private static final int MAX_ATTEMPTS = 3;
    private final User[] currentUser;

    public Login_and_SignUp(User[] currentUser) {
        this.dataManager = DataManager.getInstance();
        this.loginAttempts = 0;
        this.currentUser = currentUser;

        // Ensure data is saved when the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dataManager.saveData();
            System.out.println("Data saved during shutdown.");
        }));
    }

    public void showLoginSignupDialog() {
        while (currentUser[0] == null) { // Keep looping until a user logs in
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

            switch (choice) {
                case 0: // Login
                    if (handleLogin()) {
                        return; // Exit loop after successful login
                    }
                    break;
                case 1: // Signup
                    handleSignup();
                    break;
                case 2: // Exit
                    JOptionPane.showMessageDialog(
                        null,
                        "Data saved. You can continue later or close the application.",
                        "Exit",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    dataManager.saveData();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private boolean handleLogin() {
        String name = JOptionPane.showInputDialog(null, "Enter your name:", "Login", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (dataManager.userExists(name)) {
            loginAttempts = 0;
            JOptionPane.showMessageDialog(
                null,
                "Login successful! Welcome back, " + name + ". Current Level: " + dataManager.getUser(name).getLevel() + ". Score: " + dataManager.getUser(name).getScore(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            currentUser[0] = dataManager.getUser(name);
            return true; // Successful login
        } else {
            loginAttempts++;
            if (loginAttempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(
                    null,
                    "Maximum login attempts exceeded. Please contact the admin.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                dataManager.saveData();
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Login failed. Attempt " + loginAttempts + " of " + MAX_ATTEMPTS,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        return false; // Login unsuccessful
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
            JOptionPane.showMessageDialog(
                null,
                "Signup successful! Welcome, " + name + ". Current Level: 1" + ". Score: 0",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
