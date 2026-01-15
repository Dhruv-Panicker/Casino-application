package com.casino.games;

import com.casino.models.BlackjackHand;
import com.casino.models.BlackjackGameState;
import com.casino.models.Bet;
import com.casino.models.Card;
import com.casino.models.DealerAI;
import com.casino.models.Deck;
import com.casino.models.Player;
import com.casino.exceptions.InvalidGameActionException;
import com.casino.exceptions.InsufficientFundsException;
import com.casino.utils.InputValidator;

public class BlackjackGame extends Game {
    private BlackjackHand playerHand;
    private BlackjackHand dealerHand;
    private Deck deck;
    private BlackjackGameState gameState;
    private DealerAI dealerAI;

    public BlackjackGame(Player player) {
        super("Blackjack", player);
        this.deck = new Deck();
        this.gameState = new BlackjackGameState();
        this.dealerAI = new DealerAI();
    }

    @Override
    public void play() {
        System.out.println("\n=== " + getGameName() + " ===");
        displayRules();

        try {
            // Betting Phase
            Bet bet = placeBet();
            gameState.setCurrentBet(bet);
            System.out.println("Bet placed: " + bet);

            // Deal Phase
            gameState.setPhase(BlackjackGameState.GamePhase.INITIAL_DEAL);
            deal();

            // Check for blackjack
            if (playerHand.isBlackjack() && dealerHand.getCards().get(0).getRank() != Card.Rank.ACE) {
                handlePlayerBlackjack();
                gameState.setPhase(BlackjackGameState.GamePhase.GAME_OVER);
                return;
            }

            // Check for insurance (if dealer shows Ace)
            if (dealerHand.getCards().get(0).getRank() == Card.Rank.ACE) {
                offerInsurance();
            }

            // Check if dealer has blackjack
            if (dealerHand.isBlackjack()) {
                handleDealerBlackjack();
                gameState.setPhase(BlackjackGameState.GamePhase.GAME_OVER);
                return;
            }

            // Player Turn
            gameState.setPhase(BlackjackGameState.GamePhase.PLAYER_TURN);
            playerTurn();

            if (playerHand.isBust()) {
                System.out.println("\n Bust You went over 21. Dealer wins!");
                gameState.getCurrentBet().setResult(Bet.BetResult.LOSS);
                gameState.setPhase(BlackjackGameState.GamePhase.GAME_OVER);
                return;
            }

            // Dealer Turn
            gameState.setPhase(BlackjackGameState.GamePhase.DEALER_TURN);
            dealerTurn();

            // Determine Winner
            determineWinner();
            gameState.setPhase(BlackjackGameState.GamePhase.GAME_OVER);

        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidGameActionException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            gameState.reset();
        }
    }

    private Bet placeBet() throws InsufficientFundsException {
        System.out.println("\n--- Betting Phase ---");
        System.out.println("Current balance: $" + String.format("%.2f", player.getBalance()));
        
        double betAmount = InputValidator.getDoubleInput(
            "Enter your bet amount: $", 
            1, 
            player.getBalance()
        );

        player.placeBet(betAmount);
        return new Bet(betAmount, Bet.BetType.STANDARD);
    }

    private void deal() {
        playerHand = new BlackjackHand();
        dealerHand = new BlackjackHand();

        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        System.out.println("\n--- Initial Deal ---");
        System.out.println("Dealer's hand: " + dealerHand.getCards().get(0) + " [Hidden]");
        System.out.println("Your hand: " + playerHand);
    }

    private void handlePlayerBlackjack() {
        System.out.println("\nBlackjack! You win!");
        double winAmount = gameState.getCurrentBet().getPayout(2.5);
        player.winBet(winAmount);
        gameState.getCurrentBet().setResult(Bet.BetResult.WIN);
        System.out.println("You won: $" + winAmount);
    }

    private void handleDealerBlackjack() {
        System.out.println("\nDealer has Blackjack!");
        System.out.println("Dealer's hand: " + dealerHand);
        System.out.println("It's a push! Your bet is returned.");
        player.winBet(gameState.getCurrentBet().getAmount());
        gameState.getCurrentBet().setResult(Bet.BetResult.PUSH);
    }

    private void offerInsurance() throws InsufficientFundsException {
        boolean takeInsurance = InputValidator.getYesNoInput("Dealer shows Ace. Take insurance");
        
        if (takeInsurance) {
            double insuranceAmount = gameState.getCurrentBet().getAmount() / 2;
            if (player.placeBet(insuranceAmount)) {
                gameState.setInsuranceBet(insuranceAmount);
                gameState.setHasInsurance(true);
                System.out.println("Insurance bet placed: $" + insuranceAmount);
            } else {
                System.out.println("Insufficient funds for insurance");
            }
        }
    }

    private void playerTurn() throws InvalidGameActionException, InsufficientFundsException {
        System.out.println("\n--- Your Turn ---");

        while (playerHand.getHandValue() < 21) {
            System.out.println("Your hand: " + playerHand);
            
            String prompt = "Hit (h), Stand (s)";
            if (gameState.canDoubleDown() && playerHand.getCardCount() == 2) {
                prompt += ", or Double Down (d)";
            }
            
            String action = InputValidator.getStringInput(prompt + "? ").toLowerCase();

            if (action.equals("h")) {
                playerHand.addCard(deck.drawCard());
                System.out.println("You drew: " + playerHand.getCards().get(playerHand.getCardCount() - 1));

                if (playerHand.isBust()) {
                    System.out.println("Your hand: " + playerHand);
                    return;
                }
            } else if (action.equals("s")) {
                System.out.println("You stand with: " + playerHand);
                return;
            } else if (action.equals("d") && gameState.canDoubleDown() && playerHand.getCardCount() == 2) {
                doubleDown();
                return;
            } else {
                System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void doubleDown() throws InsufficientFundsException {
        double doubleAmount = gameState.getCurrentBet().getAmount();
        if (player.placeBet(doubleAmount)) {
            gameState.setPhase(BlackjackGameState.GamePhase.PLAYER_DOUBLE_DOWN);
            System.out.println("You doubled down! Additional bet: $" + doubleAmount);

            playerHand.addCard(deck.drawCard());
            System.out.println("You drew: " + playerHand.getCards().get(playerHand.getCardCount() - 1));
            System.out.println("Your hand: " + playerHand);
        } else {
            System.out.println("Insufficient funds to double down");
        }
    }

    private void dealerTurn() {
        System.out.println("\n--- Dealer's Turn ---");
        System.out.println("Dealer's hand: " + dealerHand);

        while (dealerAI.shouldHit(dealerHand)) {
            System.out.println("Dealer hits...");
            dealerHand.addCard(deck.drawCard());
            System.out.println("Dealer's hand: " + dealerHand);
        }

        if (dealerHand.isBust()) {
            System.out.println("Dealer busts!");
        } else {
            System.out.println("Dealer stands with: " + dealerHand);
        }
    }

    private void determineWinner() {
        int playerValue = playerHand.getHandValue();
        int dealerValue = dealerHand.getHandValue();

        System.out.println("\n--- Final Results ---");
        System.out.println("Your hand: " + playerHand);
        System.out.println("Dealer's hand: " + dealerHand);

        double payout = 0;

        if (dealerHand.isBust() || playerValue > dealerValue) {
            System.out.println("\nYou win!");
            payout = gameState.getCurrentBet().getPayout(2);
            gameState.getCurrentBet().setResult(Bet.BetResult.WIN);
        } else if (playerValue < dealerValue) {
            System.out.println("\nDealer wins!");
            gameState.getCurrentBet().setResult(Bet.BetResult.LOSS);
        } else {
            System.out.println("\nPush! It's a tie!");
            payout = gameState.getCurrentBet().getAmount();
            gameState.getCurrentBet().setResult(Bet.BetResult.PUSH);
        }

        if (payout > 0) {
            player.winBet(payout);
            System.out.println("You won: $" + payout);
        }

        System.out.println("Current balance: $" + player.getBalance());
    }

    @Override
    public String getGameRules() {
        return """
                1. Goal: Get a hand value closer to 21 than the dealer
                2. Card Values: Number cards = face value, Face cards = 10, Ace = 1 or 11
                3. Blackjack: Ace + 10-value card on first 2 cards (pays 3:2)
                4. Player can Hit, Stand, Double Down, or take Insurance
                5. Dealer must hit on 16 or less, stand on 17 or more
                6. Bust: Going over 21 means you lose
                7. Win: Higher hand value than dealer wins (pays 2:1)
                8. Insurance: Protects against dealer blackjack (pays 2:1)
                """;
    }

    public BlackjackHand getPlayerHand() {
        return playerHand;
    }

    public BlackjackHand getDealerHand() {
        return dealerHand;
    }

    public BlackjackGameState getGameState() {
        return gameState;
    }

    public DealerAI getDealerAI() {
        return dealerAI;
    }
}