package santorini;

import santorini.godcards.ArtemisGod;
import santorini.godcards.DemeterGod;
import santorini.godcards.GodCardDeck;
import santorini.screens.*;

import javax.swing.*;

import santorini.screens.ScreenManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Santorini");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            GodCardDeck godCardDeck = new GodCardDeck(new ArtemisGod(), new DemeterGod());

            String godCardPage = "GODCARDS";

            ScreenManager.registerScreen("WELCOME", new WelcomeScreen());
            ScreenManager.registerScreen(godCardPage, new GodCardInfoScreen(godCardDeck));
            ScreenManager.registerScreen("TUTORIAL", new TutorialScreen(godCardPage));
            ScreenManager.registerScreen("GAME", new GameScreen(godCardDeck));

            frame.setContentPane(ScreenManager.getMainPanel());
            frame.setVisible(true);

            ScreenManager.showScreen("WELCOME"); // Start with welcome screen
        });
    }
}
