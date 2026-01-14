package com.casino.models;

public class BlackjackHand extends Hand {
    private int aceCount; 

    public BlackjackHand() {
        super(); 
        this.aceCount = 0;
    }

    @Override
    public void addCard(Card card) {
        super.addCard(card);
        if (card.getRank() == Card.Rank.ACE) {
            aceCount++;
        }
    }

    /**
     * calculate hand value in blackjack 
     * Ace counts as 1 or 11
     */
    public int getHandValue() {
        int value = 0; 
        int aces = aceCount; 

        //Count all cards
        for (Card card: getCards()) {
            value += card.getRank().getValue(); 
        }
        //Adjust for aces
        while (value + 10 <= 21 && aces > 0) {
            value += 10; 
            aces--;
        }
        return value;
    }

    /**
     * Check if hand is a blackjack 
     */
    public boolean isBlackjack() {
        return getCardCount() == 2 && getHandValue() == 21; 
    }

    /**
     * Check if it is bust
     */
    public boolean isBust() {
        return getHandValue() > 21;    
    }

    /**
     * Check if hand has 21
     */
    public boolean isBlackjackValue() {
        return getHandValue() == 21;
    }

    @Override
    public void clear() {
        super.clear();
        aceCount = 0;
    }

    @Override
    public String toString() {
        return super.toString() + " [Value: " + getHandValue() + "]";
    }




    
}
