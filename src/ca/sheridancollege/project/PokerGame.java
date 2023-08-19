package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;

public class PokerGame extends Game {

    private PokerDeck deck;
    private int blind = 20; // minium bet amount
    private int dealIndex; // Index of the player whose turn it is
    private int pot; // Amount of chips in the pot
    private int currentBet = blind; // Minimum bet is the big blind

    public PokerGame(String name) {
        super(name);
    }

    @Override
    public void play() {
        deck = new PokerDeck(); // Create a new deck
        while (activePlayers() != 1) { // Continue playing rounds until only one player remains
            playGameRound(); // Play a betting round
        }
        declareFinalWinner(); // Declare the final winner
    }

    private void playGameRound() {
        boolean allBetsMatch = false;

        dealCards();// Deal cards to players
        dealIndex = (this.dealIndex + 1) % getPlayers().size(); // Move the deal to next player

        while (!allBetsMatch) {
            allBetsMatch = true; // Assume all bets match until proven otherwise

            for (Player player : getPlayers()) {
                PokerPlayer pokerPlayer = (PokerPlayer) player;

                if (!pokerPlayer.isEliminated() && !pokerPlayer.hasFolded()) {
                    // Ask player to choose an action
                    int choice = PlayerActionView.getPlayerAction(pokerPlayer, currentBet);

                    switch (choice) {
                        case 1: // Bet
                            bet(pokerPlayer, currentBet, pot);
                            break;
                        case 2: // Raise
                            raise(pokerPlayer, currentBet, pot);
                            break;
                        case 3: // Call
                            call(pokerPlayer, currentBet, pot);
                            break;
                        case 4: // Fold
                            fold(pokerPlayer);
                            break;
                        default:
                            PlayerActionView.displayInvalidInputMessage();
                            continue;
                    }

                    // Check if the player's bet matches the current bet
                    if (!pokerPlayer.hasCalled(currentBet)) {
                        allBetsMatch = false;
                    }
                }
            }
        }

        // Declare a round winner
        declareRoundWinner();

        // Eliminate players with no chips left
        eliminatePlayersWithNoChips();

    }

    private void dealCards() {
        deck.reset(); // Reset the deck
        deck.shuffle(); // Shuffle the deck before dealing cards
        for (Player player : getPlayers()) {
            List<PokerCard> hand = new ArrayList<PokerCard>();
            for (int i = 0; i < 5; i++) { // Deal 5 cards to each player
                PokerCard card = (PokerCard) deck.getCards().remove(0);
                hand.add(card);
            }
            ((PokerPlayer) player).setHand(new PokerHand(hand));
        }
    }

    private void declareRoundWinner() {
        PokerPlayer roundWinner = null;
        PokerHandRank winningHandRank = PokerHandRank.HIGH_CARD; // Default value

        for (Player player : getPlayers()) {
            PokerPlayer pokerPlayer = (PokerPlayer) player;

            if (!pokerPlayer.isEliminated()) {
                PokerHandRank handRank = pokerPlayer.getHand().evaluateHand();

                if (handRank.compareTo(winningHandRank) > 0) {
                    roundWinner = pokerPlayer;
                    winningHandRank = handRank;
                }
            }
        }

        if (roundWinner != null) {
            roundWinner.addChips(pot); // Award the pot to the round winner
            System.out.println("Round winner: " + roundWinner.getName() + " with " + winningHandRank.getLabel());
        } else {
            System.out.println("No winner in this round.");
        }

        // Reset the pot for the next round
        pot = 0;
    }

    private void eliminatePlayersWithNoChips() {
        for (Player player : getPlayers()) {
            PokerPlayer pokerPlayer = (PokerPlayer) player;

            if (!pokerPlayer.isEliminated() && pokerPlayer.getChips() <= 0) {
                pokerPlayer.setEliminated(true);
                System.out.println(pokerPlayer.getName() + " has been eliminated.");
            }
        }
    }

    private void declareFinalWinner() {
        PokerPlayer finalWinner = null;
        int maxChips = 0;

        for (Player player : getPlayers()) {
            PokerPlayer pokerPlayer = (PokerPlayer) player;

            if (!pokerPlayer.isEliminated() && pokerPlayer.getChips() > maxChips) {
                finalWinner = pokerPlayer;
                maxChips = pokerPlayer.getChips();
            }
        }

        if (finalWinner != null) {
            System.out.println("Final winner: " + finalWinner.getName() + " with " + finalWinner.getChips() + " chips");
        } else {
            System.out.println("No final winner.");
        }
    }

    private int activePlayers() {
        int count = 0;
        for (Player player : getPlayers()) {
            if (!player.isEliminated()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void declareWinner() {
        PokerPlayer winner = null;
        PokerHandRank winningHandRank = PokerHandRank.HIGH_CARD; // Default value

        for (Player player : getPlayers()) {
            if (!player.isEliminated()) {
                PokerHandRank handRank = ((PokerPlayer) player).getHand().evaluateHand();
                if (handRank.compareTo(winningHandRank) > 0) {
                    winner = (PokerPlayer) player;
                    winningHandRank = handRank;
                }
            }
        }

        if (winner != null) {
            System.out.println("Winner: " + winner.getName() + " with " + winningHandRank.getLabel());
        } else {
            System.out.println("No winner.");
        }
    }

    private void bet(PokerPlayer pokerPlayer, int currentBet, int pot) {
        int betAmount = PlayerActionView.getBetAmount();
        if (betAmount >= currentBet && betAmount <= pokerPlayer.getChips()) {
            pokerPlayer.deductChips(betAmount);
            pot += betAmount;
            updateCurrentBetAndLastCalled(pokerPlayer, betAmount);
        } else {
            PlayerActionView.displayInvalidInputMessage();
        }
    }

    private void raise(PokerPlayer pokerPlayer, int currentBet, int pot) {
        int raiseAmount = PlayerActionView.getBetAmount();
        if (raiseAmount >= currentBet && raiseAmount <= pokerPlayer.getChips()) {
            pokerPlayer.deductChips(raiseAmount);
            pot += raiseAmount;
            updateCurrentBetAndLastCalled(pokerPlayer, raiseAmount);
        } else {
            PlayerActionView.displayInvalidInputMessage();
        }
    }

    private void call(PokerPlayer pokerPlayer, int currentBet, int pot) {
        int callAmount = currentBet;
        if (callAmount <= pokerPlayer.getChips()) {
            pokerPlayer.deductChips(callAmount);
            pot += callAmount;
            updateCurrentBetAndLastCalled(pokerPlayer, callAmount);
        } else {
            PlayerActionView.displayInvalidInputMessage();
        }
    }

    private void fold(PokerPlayer pokerPlayer) {
        pokerPlayer.fold();
        System.out.println(pokerPlayer.getName() + " has folded.");
    }

    private void updateCurrentBetAndLastCalled(PokerPlayer pokerPlayer, int betAmount) {
        currentBet = betAmount;
        pokerPlayer.call(currentBet); // Update the last called bet
    }
}
