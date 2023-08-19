package ca.sheridancollege.project;

import ca.sheridancollege.project.Assets.ASCIIArtGenerator;
import ca.sheridancollege.project.Assets.ASCIIArtGenerator.ASCIIArtFont;

public class WelcomeView {

    public static void displayWelcomeMessage() throws Exception {
        ASCIIArtGenerator artGen = new ASCIIArtGenerator();
        artGen.printTextArt("HABIBI POKER", 10,ASCIIArtFont.ART_FONT_DIALOG,"*");
        System.out.println("Welcome to Habibi Poker!");
        System.out.println("==================================");
        System.out.println("Game Rules:");
        System.out.println("- Habibi Poker is a 5-card poker game played with a standard deck of 52 cards.");
        System.out.println("- The game can be played with 2 to 10 players.");
        System.out.println("- Players will compete in a tournament-style format with a starting amount of chips.");
        System.out.println("- There will be 2 betting rounds: one before and one after distributing the cards.");
        System.out.println("- During the betting rounds, players can raise, fold, or call bets.");
        System.out.println("- A player is eliminated if they lose all their chips.");
        System.out.println("- The player with all the chips at the end wins the tournament.");
        System.out.println("==================================");
        System.out.println("Let's start playing Habibi Poker!");
        System.out.println();
    }
}
