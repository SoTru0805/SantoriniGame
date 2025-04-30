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
    tutorialText.setText("How to Play Santorini (Quick Tutorial)\n" +
            "\n" +
            "1. Objective\n" +
            "Be the first player to move your worker onto the third level of any building.\n" +
            "\n" +
            "2. Setup\n" +
            "Place the board in the center.\n" +
            "Each player chooses a color and takes 2 workers.\n" +
            "Players take turns placing their workers on any empty spaces on the board.\n" +
            "\n" +
            "3. Your Turn (Every Turn)\n" +
            "On your turn, you must do TWO actions in order:\n" +
            "\n" +
            "A. Move\n" +
            "Move one of your workers to an adjacent space (including diagonally).\n" +
            "You may move up 1 level, any number down, or stay on the same level.\n" +
            "You cannot move into a space with another worker or a dome.\n" +
            "\n" +
            "B. Build\n" +
            "After moving, build a level on an adjacent space.\n" +
            "Levels go Ground → 1st → 2nd → 3rd → Dome.\n" +
            "If a building already has 3 levels, build a dome to cap it.\n" +
            "\n" +
            "4. Winning the Game\n" +
            "You win immediately if your worker moves onto the 3rd level.\n" +
            "If you can't move or build on your turn, you lose.\n"); // You can paste your tutorial content here later
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

  private static void showGodCardInfo() {
    Object[] options = {"Artemis", "Demeter"};

    String selectedCard = (String) JOptionPane.showInputDialog(
            null,
            "Choose a God Card to view:",
            "God Cards",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
    );

    if (selectedCard != null) {
      ImageIcon cardImage = null;
      String description = "";

      if (selectedCard.equals("Artemis")) {
        cardImage = new ImageIcon(GameScreen.class.getResource("/cards/artemis.png"));
        description = "<html><h2>Artemis</h2>"
                + "<p>Your Worker may move one additional time, but not back to its initial space.</p></html>";
      } else if (selectedCard.equals("Demeter")) {
        cardImage = new ImageIcon(GameScreen.class.getResource("/cards/demeter.png"));
        description = "<html><h2>Demeter</h2>"
                + "<p>Your Worker may build one additional time, but not on the same space.</p></html>";
      }

      JOptionPane.showMessageDialog(
              null,
              description,
              "God Card - " + selectedCard,
              JOptionPane.INFORMATION_MESSAGE,
              cardImage
      );
    }
  }

}
