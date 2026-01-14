package com.casino.models;

public class Player {
    private final String name; 
    private final Wallet wallet;
    private Hand hand; 

    public Player(String name, double initialBalance) {
        this.name = name; 
        this.wallet = new Wallet(initialBalance);
        this.hand = new Hand(); 
    }

    public String getName() {
        return name; 
    }

    public Wallet getWallet() {
        return wallet;
    }

    public double getBalance() {
        return wallet.getBalance();
    }

    public Hand getHand() {
        return hand; 
    }

    public void resetHand() {
        hand = new Hand();
    }

    public boolean placeBet(double amount) {
        if (wallet.canWithdraw(amount)) {
            wallet.withdraw(amount); 
            return true; 
        }
        return false; 
    }

    public void winBet(double amount) {
        wallet.deposit(amount);
    }

    public boolean isBroke() {
        return wallet.getBalance() == 0; 
    }

    @Override
    public String toString() {
        return String.format("%s - Balance: %s", name, wallet);
    }
}
