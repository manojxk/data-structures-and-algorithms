package cools;

import java.util.*;

// Enum to represent the suits in a deck of cards
enum Suit {
  HEARTS,
  DIAMONDS,
  CLUBS,
  SPADES
}

// Enum to represent the rank/face value of a card
enum Rank {
  TWO,
  THREE,
  FOUR,
  FIVE,
  SIX,
  SEVEN,
  EIGHT,
  NINE,
  TEN,
  JACK,
  QUEEN,
  KING,
  ACE
}

// Class to represent a card
class Card {
  private final Suit suit;
  private final Rank rank;

  // Constructor to initialize a card with suit and rank
  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  // Getter for suit
  public Suit getSuit() {
    return suit;
  }

  // Getter for rank
  public Rank getRank() {
    return rank;
  }

  // Method to represent the card as a string
  @Override
  public String toString() {
    return rank + " of " + suit;
  }
}

// Class to represent a deck of cards

public class Deck {
  private List<Card> cards; // List to hold the deck of cards

  // Constructor to initialize a deck of 52 cards
  public Deck() {
    cards = new ArrayList<>();
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        cards.add(new Card(suit, rank));
      }
    }
  }

  // Method to shuffle the deck
  public void shuffle() {
    Collections.shuffle(cards);
  }

  // Method to deal a card from the deck
  public Card dealCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("No cards left in the deck.");
    }
    return cards.remove(cards.size() - 1);
  }

  // Method to get the number of cards remaining in the deck
  public int cardsRemaining() {
    return cards.size();
  }

  // Method to reset the deck (rebuild and shuffle)
  public void reset() {
    cards.clear();
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        cards.add(new Card(suit, rank));
      }
    }
    shuffle();
  }
}
