package com.casino.models;

public class Card {
    public enum Suit {
        HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣"), SPADES("♠");

        private final String symbol;

        Suit(String symbol) {
            this.symbol = symbol;
        }
        public String getSymbol() {
            return symbol; 
        }
    }

    public enum Rank {
        ACE("A", 1),
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 10),
        QUEEN("Q", 10),
        KING("K", 10);

        private final String displayValue;
        private final int value;


        Rank(String displayValue, int value) {
            this.displayValue = displayValue;
            this.value = value;
        }

        public String getDisplayValue() {
            return displayValue; 
        }

        public int getValue() {
            return value; 
        }

    }

    private final Suit suit; 
    private final Rank rank; 

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit; 
    }

    public Rank getRank() {
        return rank; 
    }

    @Override
    public String toString() {
        return rank.getDisplayValue() + suit.getSymbol();
    }

}