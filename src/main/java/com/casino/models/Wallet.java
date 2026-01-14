package com.casino.models;

public class Wallet {
    private double balance; 

    public Wallet(double intialBalance) {
        if(intialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = intialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount; 
    }

    public void withdraw(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if(amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        }
        this.balance -= amount; 
    }

    public boolean canWithdraw(double amount) {
        return amount <= this.balance && amount > 0;
    }

    public void reset(double newBalance) {
        if(newBalance < 0) {
            throw new IllegalArgumentException("New balance cannot be negative");
        }
        this.balance = newBalance;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", balance);
    }  
}
