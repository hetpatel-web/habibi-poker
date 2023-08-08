package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerHand {

    private List<PokerCard> cards;

    public PokerHand(List<PokerCard> cards) {
        if (cards.size() != 5) {
            throw new IllegalArgumentException("A poker hand must have exactly 5 cards.");
        }
        this.cards = new ArrayList<>(cards);
        Collections.sort(this.cards);
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

    private boolean isRoyalFlush() {
        // Implement logic to check for a royal flush
    }

    private boolean isStraightFlush() {
        // Implement logic to check for a straight flush
    }

    private boolean isFourOfAKind() {
        // Implement logic to check for four of a kind
    }

    private boolean isFullHouse() {
        // Implement logic to check for a full house
    }

    private boolean isFlush() {
        // Implement logic to check for a flush
    }

    private boolean isStraight() {
        // Implement logic to check for a straight
    }

    private boolean isThreeOfAKind() {
        // Implement logic to check for three of a kind
    }

    private boolean isTwoPair() {
        // Implement logic to check for two pair
    }

    private boolean isOnePair() {
        // Implement logic to check for one pair
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
