package ca.sheridancollege.project;

public class PokerCard extends Card {

    private int rank;
    private int suit;

    public PokerCard(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        // Implement the toString() method to display card information
        return "StandardCard [rank=" + rank + ", suit=" + suit + "]";
    }
}
