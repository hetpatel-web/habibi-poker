package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerHand {
    private List<PokerCard> cards; // The cards in the hand

    public PokerHand(List<PokerCard> cards) {
        if (cards.size() != 5) {
            throw new IllegalArgumentException("A poker hand must have exactly 5 cards.");
        }
        this.cards = new ArrayList<>(cards);
        Collections.sort(this.cards);
    }

    public List<PokerCard> getCards() {
        return cards;
    }

    public PokerHandRank evaluateHand() {
        if (isRoyalFlush())
            return PokerHandRank.ROYAL_FLUSH;
        if (isStraightFlush())
            return PokerHandRank.STRAIGHT_FLUSH;
        if (isFourOfAKind())
            return PokerHandRank.FOUR_OF_A_KIND;
        if (isFullHouse())
            return PokerHandRank.FULL_HOUSE;
        if (isFlush())
            return PokerHandRank.FLUSH;
        if (isStraight())
            return PokerHandRank.STRAIGHT;
        if (isThreeOfAKind())
            return PokerHandRank.THREE_OF_A_KIND;
        if (isTwoPair())
            return PokerHandRank.TWO_PAIR;
        if (isOnePair())
            return PokerHandRank.ONE_PAIR;
        return PokerHandRank.HIGH_CARD;
    }

    // Method to check if the hand contains a specific rank
    private boolean containsRank(int rank) {
        for (PokerCard card : cards) {
            if (card.getRank() == rank) {
                return true;
            }
        }
        return false;
    }

    // Method to check for Royal Flush
    private boolean isRoyalFlush() {
        // Check if the hand is a straight flush and contains Ace, King, Queen, Jack,
        // and Ten
        return isStraightFlush() && containsRank(1) && containsRank(13) && containsRank(12) && containsRank(11)
                && containsRank(10);
    }

    // Method to check for Straight Flush
    private boolean isStraightFlush() {
        return isStraight() && isFlush();
    }

    // Method to check for Four of a Kind
    private boolean isFourOfAKind() {
        // Count the occurrences of each rank
        int[] rankCount = new int[14]; // Index 0 is not used, ranks 1 to 13
        for (PokerCard card : cards) {
            rankCount[card.getRank()]++;
        }

        // Check if there is exactly one four of a kind
        for (int count : rankCount) {
            if (count == 4) {
                return true;
            }
        }

        return false;
    }

    // Method to check for Full House
    private boolean isFullHouse() {
        // Count the occurrences of each rank
        int[] rankCount = new int[14]; // Index 0 is not used, ranks 1 to 13
        for (PokerCard card : cards) {
            rankCount[card.getRank()]++;
        }

        boolean hasThreeOfAKind = false;
        boolean hasPair = false;

        // Check if there is a three of a kind and a pair
        for (int count : rankCount) {
            if (count == 3) {
                hasThreeOfAKind = true;
            } else if (count == 2) {
                hasPair = true;
            }
        }

        return hasThreeOfAKind && hasPair;
    }

    // Method to check for Flush
    private boolean isFlush() {
        // Count the occurrences of each suit
        int[] suitCount = new int[5]; // Index 0 is not used, suits 1 to 4
        for (PokerCard card : cards) {
            suitCount[card.getSuit()]++;
        }

        // Check if there is a flush (all cards have the same suit)
        for (int count : suitCount) {
            if (count == 5) {
                return true;
            }
        }

        return false;
    }

    private boolean isStraight() {
        // Sort the cards in ascending order by rank
        Collections.sort(cards);

        // Check for a five-card sequence
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i + 1).getRank() - cards.get(i).getRank() != 1) {
                return false;
            }
        }

        // Special case: Ace can be considered low for a straight (A-2-3-4-5)
        if (cards.get(0).getRank() == 1 && cards.get(cards.size() - 1).getRank() == 13) {
            return true;
        }

        return true;
    }

    private boolean isThreeOfAKind() {
        // Count the occurrences of each rank
        int[] rankCount = new int[14]; // Index 0 is not used, ranks 1 to 13
        for (PokerCard card : cards) {
            rankCount[card.getRank()]++;
        }

        // Check if there is exactly one three of a kind
        for (int count : rankCount) {
            if (count == 3) {
                return true;
            }
        }

        return false;
    }

    private boolean isTwoPair() {
        // Count the occurrences of each rank
        int[] rankCount = new int[14]; // Index 0 is not used, ranks 1 to 13
        for (PokerCard card : cards) {
            rankCount[card.getRank()]++;
        }

        // Check if there are two pairs
        int pairCount = 0;
        for (int count : rankCount) {
            if (count == 2) {
                pairCount++;
            }
        }

        return pairCount == 2;
    }

    private boolean isOnePair() {
        // Count the occurrences of each rank
        int[] rankCount = new int[14]; // Index 0 is not used, ranks 1 to 13
        for (PokerCard card : cards) {
            rankCount[card.getRank()]++;
        }

        // Check if there is exactly one pair
        int pairCount = 0;
        for (int count : rankCount) {
            if (count == 2) {
                pairCount++;
            }
        }

        return pairCount == 1;
    }

    @Override
    public String toString() {
        StringBuilder handString = new StringBuilder();
        for (PokerCard card : cards) {
            handString.append(card).append(" ");
        }
        return handString.toString();
    }
}
