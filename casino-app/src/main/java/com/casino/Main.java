package com.casino;

import com.casino.games.BlackjackGame;
import com.casino.models.BlackjackHand;
import com.casino.models.Card;
import com.casino.models.Deck;
import com.casino.models.GameResult;
import com.casino.models.Hand;
import com.casino.models.Player;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Casino Application ===\n");

        testCardAndDeck(); 
        testPlayerandWallet();
        testGameResult(); 
        testBlackjackHand();
        testBlackjackGame();
    }
    

    private static void testCardAndDeck() {
        System.out.println("--Testing Card and Deck--");
        Deck deck = new Deck();
        System.out.println("Deck created with " + deck.getRemainingCards()  + " cards.");

        //Draw 5 cards
        System.out.println("Drawing 5 cards:");
        for (int i = 0; i < 5; i++) {
            Card card = deck.drawCard();
            System.out.println("Drawn Card: " + card);
        }
        System.out.println("Remaining cards: " + deck.getRemainingCards() + "\n");
    }


    private static void testPlayerandWallet() {
        System.out.println("--- Testing Player with Wallet ---");
        Player player = new Player("John Doe", 1000.0);
        System.out.println("Created: " + player);

        Deck deck = new Deck();
        Hand hand = player.getHand();

        // Deal 5 cards
        System.out.println("\nDealing 5 cards to player:");
        for (int i = 0; i < 5; i++) {
            hand.addCard(deck.drawCard());
        }

        System.out.println("Player's hand: " + hand);
        System.out.println("Card count: " + hand.getCardCount());

        // Test betting
        System.out.println("\n--- Testing Betting System ---");
        System.out.println("Initial balance: " + player.getWallet());

        if (player.placeBet(100)) {
            System.out.println("Successfully bet $100");
            System.out.println("Balance after bet: " + player.getWallet());
        }

        player.winBet(250);
        System.out.println("Won bet of $250");
        System.out.println("Balance after win: " + player.getWallet());

        // Test insufficient funds
        System.out.println("\n--- Testing Insufficient Funds ---");
        if (!player.placeBet(2000)) {
            System.out.println("Cannot bet $2000 - Insufficient funds!");
        }
        System.out.println("Current balance: " + player.getWallet() + "\n");


    }

    private static void testGameResult() {
        System.out.println("-- Testing GameResult --");
        GameResult result1 = new GameResult("Blackjack", "WIN", 150, 100);
        GameResult result2 = new GameResult("Roulette", "LOSS", 0, 50);
        GameResult result3 = new GameResult("Blackjack", "PUSH", 0, 0);


        System.out.println("Game Results:");
        System.out.println("  " + result1);
        System.out.println("  " + result2);
        System.out.println("  " + result3);


        System.out.println("\nResult Details:");
        System.out.println("  Result 1 - Net: $" + String.format("%.2f", result1.getNetAmount()));
        System.out.println("  Result 2 - Net: $" + String.format("%.2f", result2.getNetAmount()));
        System.out.println("  Result 3 - Net: $" + String.format("%.2f", result3.getNetAmount()) + "\n");     

    }

    private static void testBlackjackHand() {
        System.out.println("-- Testing BlackjackHand --");
        BlackjackHand hand = new BlackjackHand();

        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.KING));
        System.out.println("Hand (Ace + King): " + hand);
        System.out.println("Is Blackjack? " + hand.isBlackjack());
        System.out.println("Hand Value: " + hand.getHandValue());

        BlackjackHand hand2 = new BlackjackHand(); 
        hand2.addCard(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        hand2.addCard(new Card(Card.Suit.SPADES, Card.Rank.SIX));
        hand2.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN));
        System.out.println("\nHand (5 + 6 + Q): " + hand2);
        System.out.println("Is Bust? " + hand2.isBust());
        System.out.println("Hand Value: " + hand2.getHandValue() + "\n");

    }

    private static void testBlackjackGame() {
        System.out.println("-- Testing BlackjackGame --");
        Player player = new Player("Alice", 500.0); 

        BlackjackGame game = new BlackjackGame(player);
        System.out.println("Starting a new Blackjack game for " + player.getName());
    }
}
        