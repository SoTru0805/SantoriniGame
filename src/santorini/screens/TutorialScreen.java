package santorini.screens;

import santorini.Main;
import santorini.screens.ScreenManager;

import javax.swing.*;
import java.awt.*;

public class TutorialScreen implements Screen {

  private static String getHowToPlayText() {
    return "How to Play Santorini\n\n" +
            "Goal:\n" +
            "Be the first player to move one of your Workers onto the third level.\n\n" +
            "Setup:\n" +
            "1. Assemble the board.\n" +
            "2. Each player chooses a color and takes the two workers of that color.\n" +
            "3. Players take turns placing their workers on unoccupied spaces on the first level.\n\n" +
            "Gameplay:\n" +
            "Each turn, a player must first move one of their Workers, and then build with the same Worker.\n" +
            "Move: Move your Worker one space in any direction (horizontally, vertically, or diagonally) to an adjacent unoccupied space.\n" +
            "You can move up a level, down any number of levels, or stay on the same level.\n" +
            "You can move up a level, down any number of levels, or stay on the same level.\n" +
            "Build: Build a level on an unoccupied space adjacent to the Worker that just moved.\n" +
            "Build one level higher than the current level (up to the third level).\n\n" +
            "Winning:\n" +
            "You win if one of your Workers moves onto the third level.\n\n" +
            "Losing:\n" +
            "You lose if you cannot make a move or build on your turn.\n\n" +
            "God Powers:\n" +
            "Each player will choose a God Card, which grants a unique power that changes the standard rules of the game.";
  }

  @Override
  public JPanel getPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    JTextArea tutorialText = new JTextArea(getHowToPlayText());
    tutorialText.setEditable(false);
    tutorialText.setLineWrap(true);
    tutorialText.setWrapStyleWord(true);
    tutorialText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JScrollPane scrollPane = new JScrollPane(tutorialText);
    scrollPane.setPreferredSize(new Dimension(600, 400));

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JButton backButton = new JButton("←");
    backButton.addActionListener(e -> ScreenManager.showScreen("WELCOME"));

    JButton godCardButton = new JButton("God Cards Info →");
    godCardButton.addActionListener(e -> showGodCardInfo());

    bottomPanel.add(backButton, BorderLayout.WEST);
    bottomPanel.add(godCardButton, BorderLayout.EAST);

    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(bottomPanel, BorderLayout.SOUTH);

    return panel;
  }

  private void showGodCardInfo() {

  }
}
