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
  public GodCardDeck godCardDeck;

  public GameScreen(GodCardDeck godCardDeck){
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

// Shuffle the God deck
    godCardDeck.shuffle();

// Randomly assign
    GodCard player1Card = godCardDeck.draw();
    GodCard player2Card = godCardDeck.draw();

    player1.setGodCard(player1Card);
    player2.setGodCard(player2Card);

// Show pop-up for each player
    JOptionPane.showMessageDialog(
            null,
            name1 + ", you have been assigned the God Card: " + player1Card.getName() + "\n\n" +
                    "Power: " + player1Card.getDescription(),
            "God Card Assignment",
            JOptionPane.INFORMATION_MESSAGE
    );

    JOptionPane.showMessageDialog(
            null,
            name2 + ", you have been assigned the God Card: " + player2Card.getName() + "\n\n" +
                    "Power: " + player2Card.getDescription(),
            "God Card Assignment",
            JOptionPane.INFORMATION_MESSAGE
    );


    // Setup board and logic
    Board board = new Board();
    Game.initializeGame(player1, player2, board);

    // Create GUI
    BoardGUI boardGUI = new BoardGUI(board);
    JPanel boardPanel = boardGUI.getBoardPanel();

    JTextArea gameLog = new JTextArea("Game’s Log:\n• " + player1.getName() + " starts the game...");
    gameLog.setEditable(false);
    JScrollPane logScroll = new JScrollPane(gameLog);
    logScroll.setPreferredSize(new Dimension(300, 100));

    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(boardPanel, BorderLayout.CENTER);
    leftPanel.add(logScroll, BorderLayout.SOUTH);

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel cardTitle = new JLabel(player1.getName() + "’s Card");
    cardTitle.setFont(new Font("Arial", Font.BOLD, 16));
    cardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel cardName = new JLabel(player1.getGodCard().getName());
    cardName.setFont(new Font("Arial", Font.PLAIN, 14));
    cardName.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel cardDescription = new JLabel(player1.getGodCard().getDescription());
    cardName.setFont(new Font("Arial", Font.PLAIN, 12));
    cardName.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton undoButton = new JButton("Undo");
    undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton endTurnButton = new JButton("End Turn");
    endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    endTurnButton.addActionListener(e -> {
      Game.endTurn();
      Player current = Game.getCurrentPlayer();
      cardTitle.setText(current.getName() + "’s Card");
      cardName.setText(current.getGodCard().getName());
      gameLog.append("\n• " + current.getGodCardName() + "'s Turn");
    });

    rightPanel.add(cardTitle);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(cardName);
    rightPanel.add(Box.createVerticalStrut(6));
    rightPanel.add(cardDescription);
    rightPanel.add(Box.createVerticalStrut(20));
    rightPanel.add(undoButton);
    rightPanel.add(Box.createVerticalStrut(10));
    rightPanel.add(endTurnButton);

    // Combine
    this.getPanel().add(leftPanel, BorderLayout.CENTER);
    this.getPanel().add(rightPanel, BorderLayout.EAST);
  }

  private String askName(String prompt) {
    String name;
    do {
      name = JOptionPane.showInputDialog(null, "Enter " + prompt + " Name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
    } while (name == null || name.trim().isEmpty());
    return name.trim();
  }
}
