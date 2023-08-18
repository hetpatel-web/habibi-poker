package ca.sheridancollege.project.model;

public class PokerDeck extends GroupOfCards {

    public PokerDeck() {
        super(52); // A standard deck has 52 cards
        initializeDeck();
    }

    private void initializeDeck() {
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                getCards().add(new PokerCard(rank, suit));
            }
        }
    }
}
