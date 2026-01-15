package com.casino.cli;

import com.casino.games.BlackjackGame;
import com.casino.utils.InputValidator;

public class CasinoMenuHandler {
    private CasinoSession session;

    public CasinoMenuHandler(CasinoSession session) {
        this.session = session;
    }

    public void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        WELCOME TO CASINO APP           ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Player: " + session.getPlayer().getName());
        System.out.println("Balance: $" + String.format("%.2f", session.getPlayer().getBalance()));
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Play Blackjack");
        System.out.println("2. View Session Stats");
        System.out.println("3. Exit Casino");
    }

    public int getMainMenuChoice() {
        return InputValidator.getIntInput("Enter your choice (1-3): ", 1, 3);
    }

    public void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                playBlackjack();
                break;
            case 2:
                displaySessionStats();
                break;
            case 3:
                exitCasino();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void playBlackjack() {
        if (session.getPlayer().isBroke()) {
            System.out.println("\nYou're out of money! Thanks for playing!");
            session.endSession();
            return;
        }

        BlackjackGame game = new BlackjackGame(session.getPlayer());
        game.play();
    }

    private void displaySessionStats() {
        System.out.println("\n=== Session Statistics ===");
        System.out.println("Total Games Played: " + session.getTotalGamesPlayed());
        System.out.println("Total Winnings/Losses: $" + String.format("%.2f", session.getTotalWinnings()));
        System.out.println("Current Balance: $" + String.format("%.2f", session.getPlayer().getBalance()));
        
        if (session.getTotalGamesPlayed() > 0) {
            System.out.println("\n--- Recent Games ---");
            session.getGameHistory().stream()
                    .skip(Math.max(0, session.getTotalGamesPlayed() - 5))
                    .forEach(result -> System.out.println("  " + result));
        }
    }

    private void exitCasino() {
        System.out.println("\n=== Thank You For Playing ===");
        session.displaySessionSummary();
        session.endSession();
    }
}
