package com.casino.models;

public class Bet {
    private final double amount; 
    private final BetType type; 
    private BetResult result; 

    public enum BetType {
        STANDARD, INSURANCE, DOUBLE_DOWN, SPLIT
    }

    public enum BetResult {
        WIN, LOSS, PUSH, PENDING
    }

    public Bet(double amount, BetType type) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Bet amount must be positive.");
        }
        this.amount = amount;
        this.type = type;
        this.result = BetResult.PENDING;
    }

    public double getAmount() {
        return amount;
    }

    public BetType getType() {
        return type;
    }

    public BetResult getResult() {
        return result;
    }

    public void setResult(BetResult result) {
        this.result = result;
    }

    public double getPayout(double multiplier) {
        return amount * multiplier;
    }

    @Override
    public String toString() {
        return String.format("Bet: $%.2f [%s] - Result: %s", amount, type, result);
    }


}
