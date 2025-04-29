package santorini.screens;

import santorini.Main;
import santorini.screens.ScreenManager;

import javax.swing.*;
import java.awt.*;

public class TutorialScreen implements Screen {

  @Override
  public JPanel getPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    JTextArea tutorialText = new JTextArea();
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
