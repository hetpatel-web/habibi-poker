package ca.sheridancollege.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ca.sheridancollege.project.model.Game;
import ca.sheridancollege.project.model.GroupOfCards;
import ca.sheridancollege.project.model.Player;
import ca.sheridancollege.project.model.PokerCard;
import ca.sheridancollege.project.model.PokerHandRank;
import ca.sheridancollege.project.model.PokerPlayer;

public class PokerGame extends Game {

    private GroupOfCards deck;
    private int smallBlind = 10; // Example small blind amount
    private int bigBlind = 20; // Example big blind amount

    private int currentPlayerIndex;
    private int pot;

    public PokerGame(String name) {
        super(name);
        deck = new GroupOfCards(52); // Create a deck of 52 cards
        deck.shuffle();
    }

    @Override
    public void play() {
        dealInitialCards();

        while (getActivePlayersCount() > 1) {
            playBettingRound();
        }

        declareWinner();
    }

    private void dealInitialCards() {
        for (Player player : getPlayers()) {
            List<PokerCard> hand = new ArrayList<>();
            for (int i = 0; i < 5; i++) { // Deal 5 cards to each player
                PokerCard card = (PokerCard) deck.getCards().get(0); // Get the first card
                hand.add(card);
                deck.getCards().remove(card); // Remove the card from the deck
            }
            ((PokerPlayer) player).setHand(new PokerHand(hand)); // Cast to PokerPlayer and set the hand
        }
    }

    private void playBettingRound() {
        // Implement logic for a betting round (bet, raise, call, fold)
        // Update player chip counts, pot, and handle player actions
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
