package ca.sheridancollege.project.view;

import ca.sheridancollege.project.model.PokerPlayer;
import ca.sheridancollege.project.model.PokerCard;
import ca.sheridancollege.project.model.PokerHand;

public class DisplayPlayerCards {

    public static void displayPlayerCards(PokerPlayer player) {
        System.out.println(player.getName() + "'s Cards:");
        PokerHand hand = player.getHand();
        for (PokerCard card : hand.getCards()) {
            System.out.println(card);
        }
        System.out.println();
    }
}
