package santorini;

import santorini.godcards.ArtemisGod;
import santorini.godcards.DemeterGod;
import santorini.godcards.GodCardDeck;
import santorini.screens.*;

import javax.swing.*;

import santorini.screens.ScreenManager;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Santorini");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            GodCardDeck godCardDeck = new GodCardDeck(new ArtemisGod(), new DemeterGod());

            // Developer can see what screens exist:
            ScreenManager.registerScreen("WELCOME", new WelcomeScreen());
            ScreenManager.registerScreen("TUTORIAL", new TutorialScreen());
            ScreenManager.registerScreen("GAME", new GameScreen(godCardDeck));

            frame.setContentPane(ScreenManager.getMainPanel());
            frame.setVisible(true);

            ScreenManager.showScreen("WELCOME"); // Start with welcome screen
        });
    }
}
