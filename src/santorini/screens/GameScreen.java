package santorini.screens;

import santorini.board.Board;
import santorini.board.BoardEventHandler;
import santorini.board.BoardGUI;
import santorini.engine.Game;
import santorini.engine.GameLogicManager;
import santorini.engine.Player;
import santorini.godcards.GodCard;
import santorini.godcards.GodCardDeck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreen implements Screen {

  private JPanel panel = new JPanel(new BorderLayout());
  private boolean setupDone = false;
  private GodCardDeck godCardDeck;
  private Board board;
  private BoardGUI boardGUI;
  private Player player1, player2;
  private GameLogicManager logicManager;
  public static JTextArea gameLog;
  private JLabel cardTitle, cardName, cardDescription;

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
    String name1 = askName("Player 1");
    String name2 = askName("Player 2");

    player1 = new Player(name1);
    player2 = new Player(name2);

    godCardDeck.shuffle();
    player1.setGodCard(godCardDeck.draw());
    player2.setGodCard(godCardDeck.draw());

    showCardAssignment(player1);
    showCardAssignment(player2);

    // FIX: Create the board FIRST!
    board = new Board();

    // Now randomize workers safely
    randomizeWorkers();

    // GUI setup
    gameLog = new JTextArea("Game’s Log:\n• " + player1.getName() + " starts the game...");
    gameLog.setEditable(false);
    JScrollPane logScroll = new JScrollPane(gameLog);
    logScroll.setPreferredSize(new Dimension(300, 120));

    boardGUI = new BoardGUI(board);

    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
    leftPanel.add(logScroll, BorderLayout.SOUTH);

    JPanel rightPanel = createRightPanel();
    panel.add(leftPanel, BorderLayout.CENTER);
    panel.add(rightPanel, BorderLayout.EAST);

    // Setup Logic Manager AFTER BoardGUI created
    logicManager = new GameLogicManager(board, boardGUI, player1, player2, gameLog, cardTitle, cardName, cardDescription);

    // Setup Board Click Listener
    BoardEventHandler eventHandler = new BoardEventHandler(logicManager);
    boardGUI.setCellClickListener(eventHandler);
  }


  private void randomizeWorkers() {
    List<Point> emptySpots = new ArrayList<>();
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (board.getCell(i, j).getWorker() == null) {
          emptySpots.add(new Point(i, j));
        }
      }
    }
    Collections.shuffle(emptySpots);

    for (int i = 0; i < 2; i++) {
      Point p = emptySpots.remove(0);
      board.getCell(p.x, p.y).setWorker(player1);
    }
    for (int i = 0; i < 2; i++) {
      Point p = emptySpots.remove(0);
      board.getCell(p.x, p.y).setWorker(player2);
    }
  }

  private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setPreferredSize(new Dimension(250, 0));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel godCardInfo = new JPanel();
    godCardInfo.setLayout(new BoxLayout(godCardInfo, BoxLayout.Y_AXIS));
    godCardInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardTitle = new JLabel(player1.getName() + "’s Card");
    cardTitle.setFont(new Font("Arial", Font.BOLD, 16));
    cardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardName = new JLabel(player1.getGodCard().getName());
    cardName.setFont(new Font("Arial", Font.PLAIN, 14));
    cardName.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardDescription = new JLabel("<html><div style='text-align:center;'>" +
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
    undoButton.addActionListener(e -> logicManager.undoLastAction());

    JButton endTurnButton = new JButton("End Turn");
    endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    endTurnButton.setPreferredSize(new Dimension(100, 40));
    endTurnButton.addActionListener(e -> logicManager.endTurn());

    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(undoButton);
    buttonPanel.add(Box.createVerticalStrut(15));
    buttonPanel.add(endTurnButton);
    buttonPanel.add(Box.createVerticalGlue());

    rightPanel.add(godCardInfo, BorderLayout.NORTH);
    rightPanel.add(buttonPanel, BorderLayout.SOUTH);

    return rightPanel;
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

  public static void logMessage(String message) {
    if (gameLog != null) {
      gameLog.append("\n• " + message);
      gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
  }
}
