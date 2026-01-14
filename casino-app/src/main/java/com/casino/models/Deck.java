package com.casino.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards; 

    public Deck() {
        this.cards = new ArrayList<>(); 
        intializeDeck();
    }

    private void intializeDeck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();

    }
    
    public void shuffle() {
        Collections.shuffle(cards); 
    }

    public Card drawCard() { 
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty. Reshuffle required.");
        }
        return cards.remove(0);
    }

    public int getRemainingCards() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }   

    public void resetDeck() {
        cards.clear();
        intializeDeck();
    }
    
}
