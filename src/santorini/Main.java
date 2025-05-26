package santorini;

import santorini.engine.Player;
import santorini.godcards.ArtemisGod;
import santorini.godcards.DemeterGod;
import santorini.godcards.GodCardDeck;
import santorini.godcards.ZeusGod;
import santorini.screens.*;

import javax.swing.*;

import santorini.screens.ScreenManager;
import santorini.utils.PlayerUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Santorini");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);

            // Assign color to player's workers
            Player player1 = new Player(Color.RED);
            Player player2 = new Player(Color.BLUE);

            // Put all players into a list
            List<Player> playerList = Arrays.asList(player1, player2);

            // Create a deck for all god cards
            GodCardDeck godCardDeck = new GodCardDeck(new ArtemisGod(), new DemeterGod(), new ZeusGod());

            // Create screens to ScreenManager
            String godCardPage = "GODCARDS";

            ScreenManager.registerScreen("WELCOME", new WelcomeScreen());
            ScreenManager.registerScreen(godCardPage, new GodCardInfoScreen(godCardDeck));
            ScreenManager.registerScreen("TUTORIAL", new TutorialScreen(godCardPage));
            ScreenManager.registerScreen("GAME", new GameScreen(godCardDeck, playerList));

            frame.setContentPane(ScreenManager.getMainPanel());
            frame.setVisible(true);

            // Start with the welcome menu
            ScreenManager.showScreen("WELCOME");
        });
    }
}
