package santorini;

import santorini.godcards.ArtemisGod;
import santorini.godcards.DemeterGod;
import santorini.godcards.GodCardDeck;
import santorini.screens.*;

import javax.swing.*;

import santorini.screens.ScreenManager;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Santorini");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            // Assign color to player's workers
            Color firstPlayerColor = Color.RED;
            Color secondPlayerColor = Color.BLUE;

            GodCardDeck godCardDeck = new GodCardDeck(new ArtemisGod(), new DemeterGod());

            String godCardPage = "GODCARDS";

            ScreenManager.registerScreen("WELCOME", new WelcomeScreen());
            ScreenManager.registerScreen(godCardPage, new GodCardInfoScreen(godCardDeck));
            ScreenManager.registerScreen("TUTORIAL", new TutorialScreen(godCardPage));
            ScreenManager.registerScreen("GAME", new GameScreen(godCardDeck, firstPlayerColor, secondPlayerColor));
            GameScreen gameScreen = new GameScreen(godCardDeck, firstPlayerColor, secondPlayerColor);
            ScreenManager.registerScreen("GAME", gameScreen);

            frame.setContentPane(ScreenManager.getMainPanel());
            frame.setVisible(true);

            ScreenManager.showScreen("WELCOME"); // Start with the menu
        });
    }
}
