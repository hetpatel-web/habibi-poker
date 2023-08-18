package ca.sheridancollege.project.model;

public class PokerPlayer extends Player {

    private PokerHand hand;
    private int chips;

    public PokerPlayer(String name) {
        super(name);
        this.chips = 1000; // Initial chips value
    }

    public PokerHand getHand() {
        return hand;
    }

    public void setHand(PokerHand hand) {
        this.hand = hand;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public void deductChips(int amount) {
        chips -= amount;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    // Returns true if the player has no chips left
    public boolean isEliminated() {
        return chips <= 0;
    }

    @Override
    public void play() {
        // Implement the play() method for a poker player's actions
    }
}
