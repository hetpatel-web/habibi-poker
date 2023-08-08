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
        // Implement the toString() method to display card information
        return "StandardCard [rank=" + rank + ", suit=" + suit + "]";
    }
}
