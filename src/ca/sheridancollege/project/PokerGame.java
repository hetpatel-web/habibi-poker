package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;

public class PokerGame extends Game {

    private PokerDeck deck;

    private int bigBlind = 20; // Example big blind amount

    private int currentPlayerIndex; // Index of the player whose turn it is
    private int pot; // Amount of chips in the pot

    public PokerGame(String name) {
        super(name);
    }

    @Override
    public void play() {
        deck = new PokerDeck();
        while (!isGameOver()) { // Continue playing rounds until only one player remains
            playBettingRound();
        }
        declareFinalWinner();
    }

    private boolean isGameOver() {
        return getActivePlayersCount() == 1;
    }

    private void dealCards() {
        deck.reset();
        deck.shuffle(); // Shuffle the deck before dealing cards

        for (Player player : getPlayers()) {
            List<PokerCard> hand = new ArrayList<>();
            for (int i = 0; i < 5; i++) { // Deal 5 cards to each player
                PokerCard card = (PokerCard) deck.getCards().remove(0);
                hand.add(card);
            }
            ((PokerPlayer) player).setHand(new PokerHand(hand));
        }
    }

    private void playBettingRound() {
        int currentBet = bigBlind; // Minimum bet is the big blind
        currentPlayerIndex = (this.currentPlayerIndex + 1) % getPlayers().size(); // Start from the player after the big
                                                                                  // blind
        dealCards();
        boolean allBetsMatch = false;

        while (!allBetsMatch) {
            allBetsMatch = true; // Assume all bets match until proven otherwise

            for (Player player : getPlayers()) {
                PokerPlayer pokerPlayer = (PokerPlayer) player;

                if (!pokerPlayer.isEliminated() && !pokerPlayer.hasFolded()) {
                    // Display player's cards and chip count
                    DisplayPlayerCards.displayPlayerCards(pokerPlayer);
                    System.out.println("Chips: " + pokerPlayer.getChips());

                    // Get player action
                    int choice = PlayerActionView.getPlayerAction(pokerPlayer, currentBet);

                    switch (choice) {
                        case 1: // Bet
                            int betAmount = PlayerActionView.getBetAmount();
                            if (betAmount >= currentBet && betAmount <= pokerPlayer.getChips()) {
                                // Update chip counts and pot
                                pokerPlayer.deductChips(betAmount);
                                pot += betAmount;
                                currentBet = betAmount;
                                pokerPlayer.call(currentBet); // Update the last called bet
                            } else {
                                PlayerActionView.displayInvalidInputMessage();
                                continue;
                            }
                            break;
                        case 2: // Raise
                            int raiseAmount = PlayerActionView.getBetAmount();
                            if (raiseAmount >= currentBet && raiseAmount <= pokerPlayer.getChips()) {
                                // Update chip counts and pot
                                pokerPlayer.deductChips(raiseAmount);
                                pot += raiseAmount;
                                currentBet = raiseAmount;
                                pokerPlayer.call(currentBet); // Update the last called bet
                            } else {
                                PlayerActionView.displayInvalidInputMessage();
                                continue;
                            }
                            break;
                        case 3: // Call
                            int callAmount = currentBet;
                            if (callAmount <= pokerPlayer.getChips()) {
                                // Update chip counts and pot
                                pokerPlayer.deductChips(callAmount);
                                pot += callAmount;
                                pokerPlayer.call(currentBet); // Update the last called bet
                            } else {
                                PlayerActionView.displayInvalidInputMessage();
                                continue;
                            }
                            break;
                        case 4: // Fold
                            pokerPlayer.fold(); // Set fold status
                            System.out.println(pokerPlayer.getName() + " has folded.");
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

            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        }

        // Declare a round winner
        declareRoundWinner();

        // Eliminate players with no chips left
        eliminatePlayersWithNoChips();
        // Distribute the pot to the winning player(s) if necessary
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

    private int getActivePlayersCount() {
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

}
