package ca.sheridancollege.project;

import java.util.Scanner;

public class PlayerActionView {

    public static int getPlayerAction(PokerPlayer player, int currentBet) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(player.getName() + ", it's your turn.");
        System.out.println("Your hand:");
        DisplayPlayerCards.displayPlayerCards(player);

        System.out.println("Current bet: " + currentBet);
        System.out.println("Your chips: " + player.getChips());
        System.out.println("Choose an action:");
        System.out.println("1. Bet");
        System.out.println("2. Raise");
        System.out.println("3. Call");
        System.out.println("4. Fold");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }

    public static int getBetAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the bet amount: ");
        return scanner.nextInt();
    }

    public static void displayInvalidInputMessage() {
        System.out.println("Invalid input. Please try again.");
    }

}
