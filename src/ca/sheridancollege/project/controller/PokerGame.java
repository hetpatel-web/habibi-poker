package ca.sheridancollege.project.controller;

import java.util.ArrayList;
import java.util.List;
import ca.sheridancollege.project.model.Game;
import ca.sheridancollege.project.model.Player;
import ca.sheridancollege.project.model.PokerCard;
import ca.sheridancollege.project.model.PokerDeck;
import ca.sheridancollege.project.model.PokerHand;
import ca.sheridancollege.project.model.PokerHandRank;
import ca.sheridancollege.project.model.PokerPlayer;
import ca.sheridancollege.project.view.DisplayPlayerCards;
import ca.sheridancollege.project.view.PlayerActionView;

public class PokerGame extends Game {

    private PokerDeck deck;

    private int bigBlind = 20; // Example big blind amount

    private int currentPlayerIndex; // Index of the player whose turn it is
    private int pot; // Amount of chips in the pot

    public PokerGame(String name) {
        super(name);
        deck = new PokerDeck();
        deck.shuffle();
    }

    @Override
    public void play() {
        while (!isGameOver()) { // Continue playing rounds until only one player remains
            dealInitialCards();
            playBettingRound();
            declareRoundWinner();
        }
        declareFinalWinner();
    }

    private boolean isGameOver() {
        return getActivePlayersCount() == 1;
    }

    private void dealInitialCards() {
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
        int currentPlayerIndex = (this.currentPlayerIndex + 1) % getPlayers().size(); // Start from the player after the
        while (true) {
            PokerPlayer currentPlayer = (PokerPlayer) getPlayers().get(currentPlayerIndex);

            if (!currentPlayer.isEliminated()) {
                // Display player's cards and chip count
                DisplayPlayerCards.displayPlayerCards(currentPlayer);
                System.out.println("Chips: " + currentPlayer.getChips());

                // Get player action
                int choice = PlayerActionView.getPlayerAction(currentPlayer, currentBet);

                switch (choice) {
                    case 1: // Bet
                        int betAmount = PlayerActionView.getBetAmount();
                        if (betAmount >= currentBet && betAmount <= currentPlayer.getChips()) {
                            // Update chip counts and pot
                            currentPlayer.deductChips(betAmount);
                            pot += betAmount;
                            currentBet = betAmount;
                            currentPlayer.call(currentBet); // Update the last called bet
                        } else {
                            PlayerActionView.displayInvalidInputMessage();
                            continue;
                        }
                        break;
                    case 2: // Raise
                        int raiseAmount = PlayerActionView.getBetAmount();
                        if (raiseAmount >= currentBet && raiseAmount <= currentPlayer.getChips()) {
                            // Update chip counts and pot
                            currentPlayer.deductChips(raiseAmount);
                            pot += raiseAmount;
                            currentBet = raiseAmount;
                            currentPlayer.call(currentBet); // Update the last called bet
                        } else {
                            PlayerActionView.displayInvalidInputMessage();
                            continue;
                        }
                        break;
                    case 3: // Call
                        int callAmount = currentBet;
                        if (callAmount <= currentPlayer.getChips()) {
                            // Update chip counts and pot
                            currentPlayer.deductChips(callAmount);
                            pot += callAmount;
                            currentPlayer.call(currentBet); // Update the last called bet
                        } else {
                            PlayerActionView.displayInvalidInputMessage();
                            continue;
                        }
                        break;
                    case 4: // Fold
                        currentPlayer.fold(); // Set fold status
                        System.out.println(currentPlayer.getName() + " has folded.");
                        break;
                    default:
                        PlayerActionView.displayInvalidInputMessage();
                        continue;
                }

                // Move to the next player
                currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();

                // Check if the round is over (all active players have called the current bet or
                // folded)
                boolean roundOver = true;
                for (Player player : getPlayers()) {
                    PokerPlayer pokerPlayer = (PokerPlayer) player;
                    if (!pokerPlayer.hasFolded() && pokerPlayer.getChips() > 0 && !pokerPlayer.hasCalled(currentBet)) {
                        roundOver = false;
                        break;
                    }
                }

                if (roundOver) {
                    // Perform necessary actions for the end of the betting round
                    break; // Exit the loop
                }
            }

        }

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
