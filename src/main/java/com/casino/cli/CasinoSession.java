package com.casino.cli;

import com.casino.models.Player;
import com.casino.models.GameResult;
import java.util.ArrayList;
import java.util.List;

public class CasinoSession {
    private Player player;
    private List<GameResult> gameHistory;
    private boolean isActive;

    public CasinoSession(Player player) {
        this.player = player;
        this.gameHistory = new ArrayList<>();
        this.isActive = true;
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameResult> getGameHistory() {
        return new ArrayList<>(gameHistory);
    }

    public void addGameResult(GameResult result) {
        gameHistory.add(result);
    }

    public boolean isActive() {
        return isActive;
    }

    public void endSession() {
        this.isActive = false;
    }

    public double getTotalWinnings() {
        return gameHistory.stream()
                .mapToDouble(GameResult::getNetAmount)
                .sum();
    }

    public int getTotalGamesPlayed() {
        return gameHistory.size();
    }

    public void displaySessionSummary() {
        System.out.println("\n=== Session Summary ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Final Balance: $" + String.format("%.2f", player.getBalance()));
        System.out.println("Total Games Played: " + getTotalGamesPlayed());
        System.out.println("Total Winnings/Losses: $" + String.format("%.2f", getTotalWinnings()));
        
        if (!gameHistory.isEmpty()) {
            System.out.println("\n--- Game History ---");
            for (int i = 0; i < gameHistory.size(); i++) {
                System.out.println((i + 1) + ". " + gameHistory.get(i));
            }
        }
    }
}
