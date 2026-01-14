package com.casino.games;

import com.casino.models.BlackjackHand;
import com.casino.models.Deck;
import com.casino.models.Player;
import com.casino.exceptions.InvalidGameActionException;
import com.casino.exceptions.InsufficientFundsException;

public class BlackjackGame extends Game {
    private BlackjackHand playerHand; 
    private BlackjackHand dealerHand;
    private Deck deck; 
    private double currentBet; 
    private GameState gameState; 

    public enum GameState {
        BETTING, PLAYING, DEALER_TURN, GAME_OVER
    }

    public BlackjackGame(Player player) {
        super("Blackjack", player); 
        this.deck = new Deck(); 
        this.gameState = GameState.BETTING;
    }

    @Override
    public void play() {
        System.out.println("\n=== " + getGameName() + " ===");
        displayRules();

        try {
            //Betting phase 
            currentBet = getBetAmount(); 
            player.placeBet(currentBet); 
            System.out.println("Bet placed: $" + currentBet);

            //Deal
            deal(); 

            //Check for blackjack
            if(playerHand.isBlackjack()) {
                System.out.println("Blackjack! You win your bet.");
                player.winBet(currentBet * 2.5); 
                gameState = GameState.GAME_OVER;
                System.out.println("You won: $" + (currentBet * 2.5));
                return;
            }

            //Player turn 
            gameState = GameState.PLAYING;
            playerTurn();

            if (playerHand.isBust()){
                System.out.println("You busted! You lose your bet.");
                gameState = GameState.GAME_OVER;
                return;
            }

            //Dealer turn 
            gameState = GameState.DEALER_TURN;
            dealerTurn();

            //Determine winner 
            determineWinner();
            gameState = GameState.GAME_OVER;

        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidGameActionException e) {
            System.out.println("Invalid action: " + e.getMessage());
        }    
    }

    private double getBetAmount() throws InsufficientFundsException {
        System.out.println("\nCurrent balance: $" + player.getBalance());
        System.out.print("Enter your bet amount: $");
        double bet = Double.parseDouble(System.console().readLine()); 

        if(bet <= 0) {
            throw new InsufficientFundsException("Bet must be greater than 0");
        }
        if (bet > player.getBalance()) {
            throw new InsufficientFundsException("You do not have enough funds to place that bet.");
        }
        return bet;

    }

    private void deal() {
        playerHand = new BlackjackHand(); 
        dealerHand = new BlackjackHand(); 

        //Deal 2 cards each 
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        System.out.println("\n--- Initial Deal ---");
        System.out.println("Dealer's hand: " + dealerHand.getCards().get(0) + " [Hidden]");
        System.out.println("Your hand: " + playerHand);
    }

    private void playerTurn() throws InvalidGameActionException {
        System.out.println("\n--- Your Turn ---");

        while (playerHand.getHandValue() < 21) {
            System.out.println("Your hand: " + playerHand);
            System.out.print("Hit (h) or Stand (s)? ");
            String action = System.console().readLine().toLowerCase();

            if (action.equals("h")) {
                playerHand.addCard(deck.drawCard());
                System.out.println("You drew: " + playerHand.getCards().get(playerHand.getCardCount() - 1));
                if (playerHand.isBust()) {
                    System.out.println("Your hand: " + playerHand);
                    return;
                }
            } else if (action.equals("s")) {
                System.out.println("You chose to stand.");
                return;
            } else {
                throw new InvalidGameActionException("Invalid action. Please enter 'h' or 's'.");
            }

        }
    }

    private void dealerTurn() {
        System.out.println("\n--- Dealer's Turn ---");
        System.out.println("Dealer's hand: " + dealerHand);

        while (dealerHand.getHandValue() < 17) {
            System.out.println("Dealer hits...");
            dealerHand.addCard(deck.drawCard());
             System.out.println("Dealer's hand: " + dealerHand);
        }

        if (dealerHand.isBust()) {
            System.out.println("Dealer busts!");
        } else {
            System.out.println("Dealer stands with: " + dealerHand);
        }

        System.out.println("Dealer's final hand: " + dealerHand);
    }


    private void determineWinner() {
        int playerValue = playerHand.getHandValue();
        int dealerValue = dealerHand.getHandValue(); 

        System.out.println("\n--- Game Result ---");
        System.out.println("Your hand value: " + playerValue);
        System.out.println("Dealer's hand value: " + dealerValue);

        if (dealerHand.isBust() || playerValue > dealerValue) {
            System.out.println("You win! You receive $" + (currentBet * 2));
            player.winBet(currentBet * 2); 
        } else if (playerValue < dealerValue) {
            System.out.println("You lose! Dealer wins.");
        } else {
            System.out.println("Push! Your bet is returned.");
            player.winBet(currentBet);
        }
        System.out.println("Current balance: $" + player.getBalance());
    }


    @Override
    public String getGameRules() {
        return """
                1. Goal: Get a hand value closer to 21 than the dealer
                2. Card Values: Number cards = face value, Face cards = 10, Ace = 1 or 11
                3. Blackjack: Ace + 10-value card on first 2 cards (pays 3:2)
                4. Player hits (draws cards) or stands
                5. Dealer must hit on 16 or less, stand on 17 or more
                6. Bust: Going over 21 means you lose
                7. Win: Higher hand value than dealer wins (pays 2:1)
                """;
    }

    public BlackjackHand getPlayerHand() {
        return playerHand;
    }

    public BlackjackHand getDealerHand() {
        return dealerHand;
    }

    public GameState getGameState() {
        return gameState;
    }


    
}
