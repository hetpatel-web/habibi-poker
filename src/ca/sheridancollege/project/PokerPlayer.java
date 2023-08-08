package ca.sheridancollege.project;

public class PokerPlayer extends Player {

    private PokerHand hand;

    public PokerPlayer(String name) {
        super(name);
    }

    public PokerHand getHand() {
        return hand;
    }

    public void setHand(PokerHand hand) {
        this.hand = hand;
    }

    @Override
    public void play() {
        // Implement the play() method for a poker player's actions

    }
}
