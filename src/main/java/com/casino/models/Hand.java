package com.casino.models;

import java.util.ArrayList; 
import java.util.List;

public class Hand {
    private List<Card> cards; 

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    
    public List<Card> getCards() {
        return cards;  
    }

    public int getCardCount() {
        return cards.size();
    }

    public void clear() {
        cards.clear(); 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        for (Card card : cards) {
            sb.append(card).append(" ");
        }
        return sb.toString().trim();
    }
}
