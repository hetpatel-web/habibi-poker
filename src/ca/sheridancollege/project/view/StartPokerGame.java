package ca.sheridancollege.project.view;

import java.util.Scanner;

import ca.sheridancollege.project.controller.PokerGame;
import ca.sheridancollege.project.model.PokerPlayer;

public class StartPokerGame {
    public static void startGame() {
        PokerGame pokerGame = new PokerGame("Habibi Poker");
        Scanner scanner = new Scanner(System.in);

        // Add players to the game
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter player name: ");
            String playerName = scanner.nextLine();
            pokerGame.getPlayers().add(new PokerPlayer(playerName));
        }

        pokerGame.play();

        scanner.close();
    }
}
