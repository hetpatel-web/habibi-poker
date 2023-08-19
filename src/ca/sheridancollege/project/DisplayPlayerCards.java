package ca.sheridancollege.project;

public class DisplayPlayerCards {

    public static void displayPlayerCards(PokerPlayer player) {
        System.out.println(player.getName() + "'s Cards:");
        PokerHand hand = player.getHand();
        for (PokerCard card : hand.getCards()) {
            System.out.print(card + "| ");
        }
        System.out.println();
    }
}
