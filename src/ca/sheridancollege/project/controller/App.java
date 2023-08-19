package ca.sheridancollege.project.controller;

import ca.sheridancollege.project.view.StartPokerGame;
import ca.sheridancollege.project.view.WelcomeView;

public class App {

    public static void main(String[] args) {
        WelcomeView.displayWelcomeMessage();
        StartPokerGame.startGame();
    }

}
