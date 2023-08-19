package ca.sheridancollege.project;

public class PokerPlayer extends Player {

    private PokerHand hand;
    private int chips;
    private boolean eliminated;
    private boolean folded;
    private int lastCalledBet = 0;

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
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    // Returns true if the player has folded
    public boolean hasFolded() {
        return folded;
    }

    public void fold() {
        folded = true;
    }

    public void call(int betAmount) {
        lastCalledBet = betAmount;
    }

    public boolean hasCalled(int betAmount) {
        return lastCalledBet >= betAmount;
    }

    @Override
    public void play() {
        // Implement the play() method for a poker player's actions
    }
}
