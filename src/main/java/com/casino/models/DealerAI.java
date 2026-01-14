package com.casino.models;

public class DealerAI {
    private static final int DEALER_HIT_THRESHOLD = 17;
    private static final int DEALER_SOFT_17_THRESHOLD = 17;

    /**
     * Determines whether the dealer should hit or not based on their hand 
     * Hit on 16 or less stand on 17 or more 
     */
    public boolean shouldHit(BlackjackHand dealerHand) { 
        int handValue = dealerHand.getHandValue(); 
        return handValue < DEALER_HIT_THRESHOLD; 
    }

    /**
     * Determine if dealer has a soft hand (Ace counted as 11)
     */
    public boolean hasSoftHand(BlackjackHand dealerHand) {
        int value = dealerHand.getHandValue(); 
        int hardValue = calculateHardValue(dealerHand);
        return value != hardValue;
    }


    /**
     * Calculate hand without using Ace as 11 
     */
    private int calculateHardValue(BlackjackHand dealerHand) {
        int value = 0; 
        for(int i = 0; i < dealerHand.getCards().size(); i++) {
            Card card = dealerHand.getCards().get(i);
            if (card.getRank() == Card.Rank.ACE) {
                value += 1; 
            } else {
                value += card.getRank().getValue(); 
            }
        }
        return value;
    }

    public String getStrategy() {
        return "Dealer follows standard Las Vegas rules: Hit on 16 or less, stand on 17 or more";
    }
    
}
