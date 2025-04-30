package santorini.screens;

import santorini.board.Board;
import santorini.board.BoardEventHandler;
import santorini.board.BoardGUI;
import santorini.elements.Worker;
import santorini.engine.Game;
import santorini.engine.GameLog;
import santorini.engine.GameLogicManager;
import santorini.engine.Player;
import santorini.godcards.GodCard;
import santorini.godcards.GodCardDeck;
import santorini.utils.ImageUtils;
import santorini.utils.PlayerUtils;

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
  private Player player1, player2, randomPlayer;
  private GameLogicManager logicManager;
  public static JTextArea gameLog;
  private JLabel cardTitle, cardName, cardDescription;
  private JLabel godCardImage;
  private Color firstPlayerColor, secondPlayerColor;

  public GameScreen(GodCardDeck godCardDeck, Color firstPlayerColor, Color secondPlayerColor) {
    this.godCardDeck = godCardDeck;
    this.firstPlayerColor = firstPlayerColor;
    this.secondPlayerColor= secondPlayerColor;
  }

  @Override
  public JPanel getPanel() {
    return panel;
  }

  @Override
  public JScrollPane getScrollPanel() {
    return null;
  }

  public void startGameSetup() {
    if (setupDone) return;
    setupDone = true;
    setupGame();
  }

  private void setupGame() {
    String name1 = askName("Player 1");
    String name2 = askName("Player 2");

    player1 = new Player(name1, firstPlayerColor);
    player2 = new Player(name2, secondPlayerColor);

    board = new Board();
    PlayerUtils.randomizeWorkers(board, player1, player2);

    godCardDeck.shuffle();
    player1.setGodCard(godCardDeck.draw());
    player2.setGodCard(godCardDeck.draw());

    showCardAssignment(player1);
    showCardAssignment(player2);

    randomPlayer = PlayerUtils.getRandomPlayer(player1, player2);

    // GUI setup
    gameLog = GameLog.setUpGameLog(randomPlayer);
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
    logicManager = new GameLogicManager(
            boardGUI,
            player1,
            player2,
            randomPlayer,
            gameLog,
            cardTitle,
            cardName,
            cardDescription,
            godCardImage
    );

    // Setup Board Click Listener
    BoardEventHandler eventHandler = new BoardEventHandler(logicManager);
    boardGUI.setCellClickListener(eventHandler);
  }




  private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setPreferredSize(new Dimension(250, 0));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel godCardInfo = new JPanel();
    godCardInfo.setLayout(new BoxLayout(godCardInfo, BoxLayout.Y_AXIS));
    godCardInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardTitle = new JLabel(randomPlayer.getName() + "’s Card");
    cardTitle.setFont(new Font("Arial", Font.BOLD, 16));
    cardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardName = new JLabel(randomPlayer.getGodCard().getName());
    cardName.setFont(new Font("Arial", Font.PLAIN, 14));
    cardName.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardDescription = new JLabel("<html><div style='text-align:center;'>" +
            randomPlayer.getGodCard().getDescription() + "</div></html>");
    cardDescription.setFont(new Font("Arial", Font.PLAIN, 12));
    cardDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Load initial image for player1
    ImageUtils.setScaledGodCardIcon(randomPlayer.getGodCard(), godCardImage, 200, 300);;

    godCardInfo.add(cardTitle);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardName);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardDescription);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(godCardImage);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    JButton undoButton = new JButton("Undo");
    undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    undoButton.setPreferredSize(new Dimension(100, 40));
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
    godCardImage = ImageUtils.setUpGodCardLabel();

    GodCard godCard = player.getGodCard();
    ImageIcon scaledIcon = ImageUtils.setScaledGodCardIcon(godCard, godCardImage, 200, 300);

    JLabel imageLabel = new JLabel(scaledIcon);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel nameLabel = new JLabel("<html><div style='text-align: center;'><b>" +
            player.getName() + "</b>, you have been assigned the God Card:<br><br>" +
            "<b>" + godCard.getName() + "</b><br><br>" +
            "<i>Power:</i><br>" + godCard.getDescription() + "</div></html>");
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(nameLabel);
    panel.add(Box.createVerticalStrut(15)); // spacing
    panel.add(imageLabel);

    JOptionPane.showMessageDialog(
            null,
            panel,
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
