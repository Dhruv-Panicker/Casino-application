package com.casino.games;

import com.casino.models.Player;

public abstract class Game {
    protected Player player; 
    protected String gameName;

    public Game(String gameName, Player player) {
        this.gameName = gameName;
        this.player = player;
    }

    public String getGameName() {
        return gameName;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Abstract method that all games must implement to start the game.
     */

    public abstract void play(); 

    /**
     * Abstract method to get all game rules 
     */
    public abstract String getGameRules(); 

    public void displayRules() {
        System.out.println("Game Rules for " + gameName + ":");
        System.out.println(getGameRules());
        System.out.println(); 
    }
    
}
