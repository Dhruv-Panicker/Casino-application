package com.casino.cli;

import com.casino.models.Player;
import com.casino.utils.InputValidator;

public class CasinoApplication {
    private CasinoSession session;
    private CasinoMenuHandler menuHandler;

    public CasinoApplication() {
        initializeGame();
    }

    private void initializeGame() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   🎰 WELCOME TO CASINO APPLICATION 🎰  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Get player name
        String playerName = InputValidator.getStringInput("Enter your name: ");

        // Get initial balance
        System.out.println("\nChoose your starting balance:");
        System.out.println("1. $100");
        System.out.println("2. $500");
        System.out.println("3. $1000");
        System.out.println("4. Custom amount");

        int choice = InputValidator.getIntInput("Enter your choice (1-4): ", 1, 4);
        double initialBalance = getInitialBalance(choice);

        // Create player and session
        Player player = new Player(playerName, initialBalance);
        this.session = new CasinoSession(player);
        this.menuHandler = new CasinoMenuHandler(session);

        System.out.println("\nWelcome, " + playerName + "! You start with $" + 
                String.format("%.2f", initialBalance));
    }

    private double getInitialBalance(int choice) {
        return switch (choice) {
            case 1 -> 100.0;
            case 2 -> 500.0;
            case 3 -> 1000.0;
            case 4 -> InputValidator.getDoubleInput("Enter your starting balance: $", 10, 10000);
            default -> 500.0;
        };
    }

    public void run() {
        while (session.isActive()) {
            menuHandler.displayMainMenu();
            int choice = menuHandler.getMainMenuChoice();
            menuHandler.handleMenuChoice(choice);
        }
        
        InputValidator.closeScanner();
    }

    public static void main(String[] args) {
        CasinoApplication app = new CasinoApplication();
        app.run();
    }
}
