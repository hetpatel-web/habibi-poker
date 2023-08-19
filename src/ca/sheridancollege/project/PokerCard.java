package ca.sheridancollege.project;

public class PokerCard extends Card implements Comparable<PokerCard> {

    private int rank;
    private int suit;

    public PokerCard(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    @Override
    public int compareTo(PokerCard card) {
        return Integer.compare(this.rank, card.rank);
    }

    @Override
    public String toString() {
        String rankString = Rank.values()[rank - 1].toString();
        String suitString = Suit.values()[suit].toString();
        return rankString + " of " + suitString;
    }

    private enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    private enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }
}
