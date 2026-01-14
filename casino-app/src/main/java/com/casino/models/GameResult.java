package com.casino.models;

import java.time.LocalDateTime;

public class GameResult {
    private final String gameName;
    private final String outcome;
    private final double amountWon;
    private final double amountLost;
    private final LocalDateTime timestamp;

    public GameResult(String gameName, String outcome, double amountWon, double amountLost) {
        this.gameName = gameName;
        this.outcome = outcome;
        this.amountWon = amountWon;
        this.amountLost = amountLost;
        this.timestamp = LocalDateTime.now();
    }

    public String getGameName() {
        return gameName;
    }

    public String getOutcome() {
        return outcome;
    }

    public double getAmountWon() {
        return amountWon;
    }

    public double getAmountLost() {
        return amountLost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getNetAmount() {
        return amountWon - amountLost;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (Won: $%.2f, Lost: $%.2f)", 
                timestamp, gameName, outcome, amountWon, amountLost);
    }
}
