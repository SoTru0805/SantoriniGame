package santorini.screens;

import santorini.board.Board;
import santorini.board.BoardEventHandler;
import santorini.board.BoardGUI;
import santorini.engine.*;
import santorini.godcards.GodCardDeck;
import santorini.utils.GodCardUtils;
import santorini.utils.ImageUtils;
import santorini.utils.PlayerUtils;
import javax.swing.JOptionPane;

import santorini.dice.DiceReward;
import santorini.dice.DiceRewardFactory;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreen implements Screen {

  private JPanel panel = new JPanel(new BorderLayout());
  private boolean setupDone = false;
  private GodCardDeck godCardDeck;
  private Board board;
  private BoardGUI boardGUI;
  private Player randomPlayer;
  private GameLogicManager logicManager;
  public static JTextArea gameLog;
  private JLabel cardTitle, cardName, cardDescription, godCardImage;
  private JLabel currentPlayerNameLabel;
  private JPanel currentPlayerColorIndicator;

  private JLabel player1TimerLable;
  private JLabel player2TimerLable;
  private ChessClock chessClock;


  private List<Player> players;

  public GameScreen(GodCardDeck godCardDeck, List<Player> players) {
    this.godCardDeck = godCardDeck;
    this.players = players;
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
    board = new Board();
    PlayerUtils.askNamesForPlayers(players);

    godCardDeck.shuffle();

    GodCardUtils.assignGodCardsToPlayers(players, godCardDeck);

    PlayerUtils.shufflePlayers(players);  // Shuffle players at the beginning
    PlayerUtils.randomizeWorkers(board, players);  // Assign workers to such players

    randomPlayer = PlayerUtils.getRandomPlayer(players);

    Game.setCurrentPlayer(randomPlayer);

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

    CardDisplay cardDisplay = new CardDisplay(
            cardTitle,
            cardName,
            cardDescription,
            godCardImage
    );

    // New panels for timer
    JPanel timerPanel = new JPanel();
    timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    timerPanel.add(player1TimerLable);
    timerPanel.add(Box.createHorizontalStrut(20));
    timerPanel.add(player2TimerLable);
    panel.add(timerPanel, BorderLayout.NORTH);

    Runnable onTimeOut = () -> JOptionPane.showMessageDialog(null, "Time's up!");
    chessClock = new ChessClock(player1TimerLable, player2TimerLable, 10, onTimeOut); // Create BEFORE logicManager!


    // Setup Logic Manager after BoardGUI created
    logicManager = new GameLogicManager(
            boardGUI,
            players,
            randomPlayer,
            cardDisplay,
            currentPlayerColorIndicator,
            chessClock
    );

    logicManager.setPostTurnCallback(() -> {
      Player player = Game.getCurrentPlayer();
      int choice = JOptionPane.showConfirmDialog(
              null,
              player.getName() + ", do you want to roll the dice for a random event?",
              "Roll Dice?",
              JOptionPane.YES_NO_OPTION
      );

      if (choice == JOptionPane.YES_OPTION) {
        showDiceRollPopup(player);
      } else {
        JOptionPane.showMessageDialog(
                null,
                "You chose not to roll the dice this turn.",
                "No Dice Roll",
                JOptionPane.INFORMATION_MESSAGE
        );
      }
    });


    // Setup Board Click Listener
    BoardEventHandler eventHandler = new BoardEventHandler(logicManager);
    boardGUI.setCellClickListener(eventHandler);

    // Start the timer for the starting player
    chessClock.start();

  }




  private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setPreferredSize(new Dimension(250, 0));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel godCardInfo = new JPanel();
    godCardInfo.setLayout(new BoxLayout(godCardInfo, BoxLayout.Y_AXIS));
    godCardInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Panel for current player info and "Player's Card" text
    JPanel currentPlayerCardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    currentPlayerCardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    currentPlayerColorIndicator = new JPanel();
    currentPlayerColorIndicator.setPreferredSize(new Dimension(20, 20));


    cardTitle = new JLabel(randomPlayer.getName() + "’s Card");
    cardTitle.setFont(new Font("Arial", Font.BOLD, 16));
    cardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    currentPlayerCardPanel.add(currentPlayerColorIndicator);
    currentPlayerCardPanel.add(Box.createHorizontalStrut(5));
    currentPlayerCardPanel.add(cardTitle);


    cardName = new JLabel(randomPlayer.getGodCard().getName());
    cardName.setFont(new Font("Arial", Font.PLAIN, 14));
    cardName.setAlignmentX(Component.CENTER_ALIGNMENT);

    cardDescription = new JLabel("<html><div style='text-align:center;'>" +
            randomPlayer.getGodCard().getDescription() + "</div></html>");
    cardDescription.setFont(new Font("Arial", Font.PLAIN, 12));
    cardDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Load initial image for the random player
    godCardImage = ImageUtils.setUpGodCardLabel();
    ImageUtils.setScaledGodCardIcon(randomPlayer.getGodCard(), godCardImage, 200, 300);;

    godCardInfo.add(currentPlayerCardPanel);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardName);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(cardDescription);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(godCardImage);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

    JButton usePowerButton = new JButton("Use Power");
    usePowerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    usePowerButton.setFont(new Font("Arial", Font.BOLD, 17));
    usePowerButton.setForeground(Color.WHITE);
    usePowerButton.setBackground(new Color(34, 139, 34)); // Forest Green
    usePowerButton.setOpaque(true);
    usePowerButton.setContentAreaFilled(true);
    usePowerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

    // Set preferred and maximum size
    Dimension buttonSize = new Dimension(210, 50);
    usePowerButton.setPreferredSize(buttonSize);
    usePowerButton.setMaximumSize(buttonSize);


    usePowerButton.addActionListener(e -> {
      if (logicManager.getLastAction() == null || !logicManager.getLastAction().status()) {
        GameLog.logMessage("Error: You must make the action before using the effect.");
        return;
      }

      Player currentPlayer = Game.getCurrentPlayer();

      boolean success = logicManager.activateAbility();
      if (success) {
        GameLog.logMessage(currentPlayer.getName() + " used the god card " + currentPlayer.getGodCard().getName() + " effect.");
        GameLog.logMessage("Select a cell to do the extra action.");
      } else {
        GameLog.logMessage("Error: Power cannot be used again or is not applicable now.");
      }
    });


    JButton undoButton = new JButton("Undo");
    undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    undoButton.setPreferredSize(new Dimension(100, 40));
    undoButton.addActionListener(e -> logicManager.undoLastAction());

    JButton endTurnButton = new JButton("End Turn");
    endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    endTurnButton.setPreferredSize(new Dimension(100, 40));
    endTurnButton.addActionListener(e -> logicManager.endTurn());

    JPanel actionButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // 10 px gap

    actionButtonRow.add(undoButton);
    actionButtonRow.add(endTurnButton);

    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(usePowerButton);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(actionButtonRow);
    buttonPanel.add(Box.createVerticalGlue());

    rightPanel.add(godCardInfo, BorderLayout.NORTH);
    rightPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Initialise the color for the starting player
    ImageUtils.updateCurrentPlayerDisplay(randomPlayer, currentPlayerColorIndicator, cardTitle);

    player1TimerLable = new JLabel("Player 1: 10:00");
    player1TimerLable.setFont(new Font("Arial", Font.BOLD, 14));
    player1TimerLable.setAlignmentX(Component.CENTER_ALIGNMENT);

    player2TimerLable = new JLabel("Player 2: 10:00");
    player2TimerLable.setFont(new Font("Arial", Font.BOLD, 14));
    player2TimerLable.setAlignmentX(Component.CENTER_ALIGNMENT);

    godCardInfo.add(Box.createVerticalStrut(20));
    godCardInfo.add(player1TimerLable);
    godCardInfo.add(Box.createVerticalStrut(10));
    godCardInfo.add(player2TimerLable);


    return rightPanel;
  }

  public void showDiceRollPopup(Player player) {
    int diceRoll = (int)(Math.random() * 6) + 1;
    DiceReward reward = DiceRewardFactory.getReward(diceRoll);

    // Reset all reward flags
    player.setHasExtraMove(false);
    player.setHasDoubleBuild(false);
    player.setCanDemolish(false);
    player.setCanJumpTwoLevels(false);
    player.setShouldLoseTurn(false);
    player.setBlocked(false);

    // Apply the reward
    reward.apply(player, logicManager);

    JOptionPane.showMessageDialog(
            null,
            player.getName() + " rolled a " + diceRoll + "!\n" + reward.getDescription(),
            "Dice Roll",
            JOptionPane.INFORMATION_MESSAGE
    );
  }



}
