package com.casino.models;

import com.casino.games.Game;

public class BlackjackGameState {
    private Bet currentBet; 
    private double insuranceBet;
    private boolean canDoubleDown;
    private boolean canSplit;
    private boolean hasInsurance;
    private GamePhase phase; 


    public enum GamePhase {
        INITIAL_DEAL, PLAYER_TURN, PLAYER_DOUBLE_DOWN, DEALER_TURN, GAME_OVER

    }

    public BlackjackGameState() {
        this.insuranceBet = 0; 
        this.canDoubleDown = true; 
        this.canSplit = true;
        this.hasInsurance = false;
        this.phase = GamePhase.INITIAL_DEAL;
    }

    public Bet getCurrentBet() {
        return currentBet; 
    }

    public void setCurrentBet(Bet bet) {
        this.currentBet = bet;
    }
    public double getInsuranceBet() {
        return insuranceBet;
    }
    public void setInsuranceBet(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Insurance bet cannot be negative");
        }
        this.insuranceBet = amount;
    }
    public boolean canDoubleDown() {
        return canDoubleDown && phase == GamePhase.PLAYER_TURN;
    }
    public void disableDoubleDown() {
        this.canDoubleDown = false;
    }
    public boolean canSplit() {
        return canSplit && phase == GamePhase.PLAYER_TURN;
    }
    public void setCanSplit(boolean canSplit) {
        this.canSplit = canSplit;
    }
    public boolean hasInsurance() {
        return hasInsurance;
    }
    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;   
    }
    public GamePhase getPhase() {
        return phase;
    }
    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }
    public void reset() {
        this.currentBet = null;
        this.insuranceBet = 0;
        this.canDoubleDown = true;
        this.canSplit = false;
        this.hasInsurance = false;
        this.phase = GamePhase.INITIAL_DEAL;
    }
}
