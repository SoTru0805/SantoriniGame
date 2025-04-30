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
  public static JTextArea gameLog;

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
    Board board = new Board(5,5);
    Game.initializeGame(player1, player2, board);
    Game.getCurrentPlayer().getGodCard().onTurnStart();

    java.util.List<Point> emptySpots = new java.util.ArrayList<>(); //Random placiong worker

    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (board.getCell(i, j).getWorker() == null) {
          emptySpots.add(new Point(i, j));
        }
      }
    }

    java.util.Collections.shuffle(emptySpots);

    for (int i = 0; i < 2; i++) {
      Point p = emptySpots.remove(0);
      board.getCell(p.x, p.y).setWorker(player1);
    }

    for (int i = 0; i < 2; i++) {
      Point p = emptySpots.remove(0);
      board.getCell(p.x, p.y).setWorker(player2);
    }

    BoardGUI boardGUI = new BoardGUI(board);
    JPanel boardPanel = boardGUI.getBoardPanel();

    gameLog = new JTextArea("Game’s Log:\n• " + player1.getName() + " starts the game...");
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

    // Load and display the GodCard image
    JLabel cardImageLabel = new JLabel();
    updateCardImage(cardImageLabel, player1.getGodCard());

    godCardInfo.add(cardTitle);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardName);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardDescription);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardImageLabel);

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
      current.getGodCard().onTurnEnd();

      cardTitle.setText(current.getName() + "’s Card");
      cardName.setText(current.getGodCard().getName());
      cardDescription.setText("<html><div style='text-align:center;'>" +
              current.getGodCard().getDescription() + "</div></html>");
      updateCardImage(cardImageLabel, currentCard);
      gameLog.append("\n• " + current.getGodCardName() + "'s Turn");
    });

    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(undoButton);
    buttonPanel.add(Box.createVerticalStrut(15));
    buttonPanel.add(endTurnButton);
    buttonPanel.add(Box.createVerticalGlue());

    rightPanel.add(godCardInfo, BorderLayout.NORTH);
    rightPanel.add(buttonPanel, BorderLayout.SOUTH);

    panel.add(leftPanel, BorderLayout.CENTER);
    panel.add(rightPanel, BorderLayout.EAST);
  }

  private void showCardAssignment(Player player) {
    String cardName = player.getGodCard().getName();
    String imagePath = "images/GodCards/" + cardName + ".jpg";
    ImageIcon icon = new ImageIcon(imagePath);

    // Resize the image
    Image image = icon.getImage();
    Image newimg = image.getScaledInstance(450, 300,  java.awt.Image.SCALE_SMOOTH);
    ImageIcon resizedIcon = new ImageIcon(newimg);

    JLabel imageLabel = new JLabel(resizedIcon);
    imageLabel.setAlignmentX(SwingConstants.CENTER);
    imageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

    JPanel messagePanel = new JPanel();
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
    messagePanel.add(new JLabel("<html>" + player.getName() + ", you have been assigned the God Card: <strong>" + cardName + "</strong><br></html>"));
    messagePanel.add(new JLabel("<html><br>Power:<br><strong>" + player.getGodCard().getDescription() + "</strong></html>"));
    messagePanel.add(imageLabel);

    JOptionPane.showMessageDialog(
            null,
            messagePanel,
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

  private void updateCardImage(JLabel label, GodCard card) {
    String imagePath = card.getImagePath();
    ImageIcon icon = new ImageIcon(imagePath);

    // Resize the image
    Image image = icon.getImage();
    Image newimg = image.getScaledInstance(120, 200,  java.awt.Image.SCALE_SMOOTH);
    icon = new ImageIcon(newimg);

    label.setIcon(icon);
  }

  public static void logMessage(String message) {
    if (gameLog != null) {
      gameLog.append("\n• " + message);
      gameLog.setCaretPosition(gameLog.getDocument().getLength()); // auto-scroll
    }
  }
}
