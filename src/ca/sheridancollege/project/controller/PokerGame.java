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

public class PokerGame extends Game {

    private PokerDeck deck;
    private int smallBlind = 10; // Example small blind amount
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
            collectBlinds();
            dealInitialCards();
            playBettingRound();
            declareRoundWinner();
        }
        declareFinalWinner();
    }

    private boolean isGameOver() {
        return getActivePlayersCount() == 1;
    }

    private void collectBlinds() {
        for (Player player : getPlayers()) {
            PokerPlayer pokerPlayer = (PokerPlayer) player; // Cast to PokerPlayer

            // Deduct small blind from player chips and add to the pot
            pokerPlayer.deductChips(smallBlind);
            pot += smallBlind;

            // Move to the next player for big blind
            currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        }

        // Deduct big blind from the current player's chips and add to the pot
        PokerPlayer bigBlindPlayer = (PokerPlayer) getPlayers().get(currentPlayerIndex);
        bigBlindPlayer.deductChips(bigBlind);
        pot += bigBlind;
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
        // Implement logic for a betting round (bet, raise, call, fold)
        // Update player chip counts, pot, and handle player actions
    }

    private void declareRoundWinner() {
        // Logic to determine and display the round winner
    }

    private void declareFinalWinner() {
        // Logic to determine and display the final winner of the game
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
