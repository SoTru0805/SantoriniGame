package santorini.screens;

import santorini.board.Board;
import santorini.board.BoardGUI;
import santorini.engine.Game;
import santorini.engine.Player;
import santorini.godcards.GodCard;
import santorini.godcards.GodCardDeck;

import javax.swing.*;
import java.awt.*;

public class GameScreen implements Screen {

  private JPanel panel = new JPanel(new BorderLayout());
  private boolean setupDone = false;
  private GodCardDeck godCardDeck;

  public GameScreen(GodCardDeck godCardDeck) {
    this.godCardDeck = godCardDeck;
  }

  @Override
  public JPanel getPanel() {
    return panel;
  }

  public void startGameSetup() {
    if (setupDone) return;
    setupDone = true;
    setupGame();
  }

  private void setupGame() {
    // Setup players
    String name1 = askName("Player 1");
    String name2 = askName("Player 2");

    Player player1 = new Player(name1);
    Player player2 = new Player(name2);

    godCardDeck.shuffle();

    GodCard player1Card = godCardDeck.draw();
    GodCard player2Card = godCardDeck.draw();

    player1.setGodCard(player1Card);
    player2.setGodCard(player2Card);

    showCardAssignment(player1);
    showCardAssignment(player2);

    // Setup board and logic
    Board board = new Board();
    Game.initializeGame(player1, player2, board);

    // Create GUI
    BoardGUI boardGUI = new BoardGUI(board);
    JPanel boardPanel = boardGUI.getBoardPanel();

    JTextArea gameLog = new JTextArea("Game’s Log:\n• " + player1.getName() + " starts the game...");
    gameLog.setEditable(false);
    JScrollPane logScroll = new JScrollPane(gameLog);
    logScroll.setPreferredSize(new Dimension(300, 120));

    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(boardPanel, BorderLayout.CENTER);
    leftPanel.add(logScroll, BorderLayout.SOUTH);

    // Right panel
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setPreferredSize(new Dimension(250, 0));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel godCardInfo = new JPanel();
    godCardInfo.setLayout(new BoxLayout(godCardInfo, BoxLayout.Y_AXIS));
    godCardInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel cardTitle = new JLabel(player1.getName() + "’s Card");
    cardTitle.setFont(new Font("Arial", Font.BOLD, 16));
    cardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel cardName = new JLabel(player1.getGodCard().getName());
    cardName.setFont(new Font("Arial", Font.PLAIN, 14));
    cardName.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel cardDescription = new JLabel("<html><div style='text-align:center;'>" +
            player1.getGodCard().getDescription() + "</div></html>");
    cardDescription.setFont(new Font("Arial", Font.PLAIN, 12));
    cardDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

    godCardInfo.add(cardTitle);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardName);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardDescription);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    JButton undoButton = new JButton("Undo");
    undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton endTurnButton = new JButton("End Turn");
    endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    endTurnButton.setPreferredSize(new Dimension(100, 40));

    endTurnButton.addActionListener(e -> {
      Game.endTurn();
      Player current = Game.getCurrentPlayer();
      cardTitle.setText(current.getName() + "’s Card");
      cardName.setText(current.getGodCard().getName());
      cardDescription.setText("<html><div style='text-align:center;'>" +
              current.getGodCard().getDescription() + "</div></html>");
      gameLog.append("\n• " + current.getGodCardName() + "'s Turn");
    });

    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(undoButton);
    buttonPanel.add(Box.createVerticalStrut(15));
    buttonPanel.add(endTurnButton);
    buttonPanel.add(Box.createVerticalGlue());

    rightPanel.add(godCardInfo, BorderLayout.NORTH);
    rightPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Final layout
    panel.add(leftPanel, BorderLayout.CENTER);
    panel.add(rightPanel, BorderLayout.EAST);
  }

  private void showCardAssignment(Player player) {
    JOptionPane.showMessageDialog(
            null,
            player.getName() + ", you have been assigned the God Card: " +
                    player.getGodCard().getName() + "\n\n" +
                    "Power: " + player.getGodCard().getDescription(),
            "God Card Assignment",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  private String askName(String prompt) {
    String name;
    do {
      name = JOptionPane.showInputDialog(null, "Enter " + prompt + " Name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
    } while (name == null || name.trim().isEmpty());
    return name.trim();
  }
}
