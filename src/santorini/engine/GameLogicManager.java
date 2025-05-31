package santorini.engine;

import santorini.actions.ActionType;
import santorini.board.Cell;
import santorini.board.BoardGUI;
import santorini.actions.Action;
import santorini.actions.MoveAction;
import santorini.actions.BuildAction;
import santorini.elements.Worker;
import santorini.screens.ChessClock;
import santorini.screens.ResultScreen;
import santorini.screens.ScreenManager;
import santorini.utils.ImageUtils;
import santorini.utils.PlayerUtils;

import javax.swing.*;
import java.util.List;

/**
 * Manages the main game logic for the Santorini board game, including turn management,
 * action handling (move, build, undo), phase control, ability activation, and player switching.
 * Also integrates with a ChessClock for timing each player's moves.
 * Supports special dice rewards, including demolishing a building.
 */
public class GameLogicManager {

  /** The GUI for the game board. */
  private BoardGUI boardGUI;

  private boolean doubleBuildInProgress = false;

  private Cell firstBuildCell = null;

  /** The list of all players in the game. */
  public List<Player> playerList;

  /** The player whose turn it is currently. */
  private Player currentPlayer, startingPlayer;

  /** Whether the current phase is the moving phase. */
  private boolean movingPhase = true;

  /** Whether the move action has been completed this turn. */
  private boolean moveCompleted, buildCompleted = false;

  /** Whether a worker has been selected. */
  private boolean workerSelected = false;

  /** The cell containing the selected worker. */
  private Cell selectedWorkerCell;

  /** The last action performed (move/build). */
  private Action lastAction = null;

  /** The current turn count. */
  private int turnCount = 1;

  /** Whether the player's god power has been used this turn. */
  private boolean abilityUsedThisTurn = false;

  /** Temporarily stores a target cell for certain actions. */
  private Cell tempTarget, previousCell = null;

  /** Card display for updating god card info in the UI. */
  private CardDisplay cardDisplay;

  /** Panel for showing current player color indicator. */
  private JPanel currentPlayerColorIndicator;

  /** The chess clock object managing player timers. */
  private ChessClock chessClock;

  /** Add a post-turn call back to End Turn */
  private Runnable postTurnCallBack;

  /**
   * Setter for PostTurnCallBack, called after endTurn().
   * @param callback Runnable to run after turn ends
   */
  public void setPostTurnCallback(Runnable callback) {
    this.postTurnCallBack = callback;
  }

  /**
   * Constructs the GameLogicManager.
   *
   * @param boardGUI The board GUI.
   * @param playerList The list of players.
   * @param startingPlayer The player who starts the game.
   * @param cardDisplay The card display panel.
   * @param currentPlayerColorIndicator The panel indicating the current player's color.
   * @param chessClock The chess clock managing player times.
   */
  public GameLogicManager(BoardGUI boardGUI, List<Player> playerList, Player startingPlayer,
                          CardDisplay cardDisplay, JPanel currentPlayerColorIndicator, ChessClock chessClock) {
    this.boardGUI = boardGUI;
    this.playerList = playerList;
    this.startingPlayer = startingPlayer;
    this.currentPlayer = startingPlayer;
    this.cardDisplay = cardDisplay;
    this.currentPlayerColorIndicator = currentPlayerColorIndicator;
    this.chessClock = chessClock;
  }

  /**
   * Handles a cell click event, managing move/build phases, worker selection,
   * and action execution.
   *
   * @param clickedCell The cell that was clicked.
   */
  public void handleCellClick(Cell clickedCell) {
    if (movingPhase) {
      if (!workerSelected) {
        if (clickedCell.isOccupied()) {
          if (clickedCell.getWorker().getPlayer() == currentPlayer) {
            previousCell = clickedCell;
            selectedWorkerCell = clickedCell;
            workerSelected = true;
            GameLog.logMessage(currentPlayer.getName() + " selected a worker to move.");
          } else {
            GameLog.logMessage("Error: Invalid selection. Select your own worker.");
          }
        } else {
          GameLog.logMessage("Error: The cell does not have your worker.");
        }
      } else {
        MoveAction moveAction = new MoveAction(boardGUI, currentPlayer, selectedWorkerCell, clickedCell);
        moveAction.setExcludedCell(tempTarget);
        String log = moveAction.execute();
        GameLog.logMessage(log);
        if (moveAction.status()) {
          lastAction = moveAction;
          tempTarget = null;

          movingPhase = false;
          workerSelected = false;
          moveCompleted = true;

          selectedWorkerCell = clickedCell;

          checkWinner(clickedCell, currentPlayer);

          // Handle extra move rewards from the dice
          if (currentPlayer.hasExtraMove()) {
            GameLog.logMessage(currentPlayer.getName() + " uses extra move from last dice roll");
            currentPlayer.setHasExtraMove(false);
            movingPhase = true;
            moveCompleted = false;
            workerSelected = false;
            selectedWorkerCell = null;
            return;
          }
        }
      }
    } else {
      if (buildCompleted && !doubleBuildInProgress) {
        GameLog.logMessage(currentPlayer.getName() + " has already built! Please end your turn.");
        return;
      }

      if (!workerSelected) {
        if (clickedCell.isOccupied()) {
          if (clickedCell == selectedWorkerCell) {
            workerSelected = true;
            GameLog.logMessage(currentPlayer.getName() + " selected the same worker to build.");
          } else if (clickedCell.getWorker().getPlayer() == currentPlayer) {
            GameLog.logMessage("Error: You must use the same worker that moved to build.");
          } else {
            GameLog.logMessage("Error: Invalid selection. Select your own worker.");
          }
        } else {
          GameLog.logMessage("Error: The cell does not have your worker.");
        }
      } else {
        boolean canDoubleBuild = currentPlayer.hasDoubleBuild();

        // For second build, prevent building on the same cell as first build
        if (doubleBuildInProgress && clickedCell == firstBuildCell) {
          GameLog.logMessage("You cannot build twice on the same cell!");
          return;
        }

        BuildAction buildAction = new BuildAction(boardGUI, currentPlayer, selectedWorkerCell, clickedCell);
        buildAction.setExcludedCell(tempTarget);
        String log = buildAction.execute();
        GameLog.logMessage(log);

        if (buildAction.status()) {
          previousCell = clickedCell;
          lastAction = buildAction;
          tempTarget = null;

          if (canDoubleBuild && !doubleBuildInProgress) {
            // First build of double build
            doubleBuildInProgress = true;
            firstBuildCell = clickedCell;
            buildCompleted = false; // allow another build
            workerSelected = false;
            GameLog.logMessage(currentPlayer.getName() + " may now build again on a different cell (Double Build reward).");
            return;
          } else if (canDoubleBuild && doubleBuildInProgress) {
            // Second build finished!
            doubleBuildInProgress = false;
            currentPlayer.setHasDoubleBuild(false);
            firstBuildCell = null;
            buildCompleted = true;
            workerSelected = false;
            GameLog.logMessage(currentPlayer.getName() + " has finished double build. Please end your turn.");
            return;
          } else {
            // Standard single build
            buildCompleted = true;
            workerSelected = false;
            GameLog.logMessage(currentPlayer.getName() + " must now end the turn.");
          }
        }
      }
    }
  }


  /**
   * Returns the last action performed (move or build).
   *
   * @return The last Action, or null if none exists.
   */
  public Action getLastAction() {
    return lastAction;
  }

  /**
   * Activates the player's god ability for this turn, if available.
   *
   * @return True if the ability was successfully activated, false otherwise.
   */
  public boolean activateAbility() {
    boolean status = false;

    if (abilityUsedThisTurn) {
      return status;
    }

    status = currentPlayer.getGodCard().useEffect(this);
    if (status) {
      lastAction.setExcludedCell(tempTarget);

      abilityUsedThisTurn = true;
      workerSelected = true;
      tempTarget = previousCell;
    }
    return status;
  }

  /**
   * Allows an extra move if the last action was a move and it was completed.
   *
   * @return True if extra move was activated, false otherwise.
   */
  public boolean activateExtraMove() {
    if (lastAction.getActionType() == ActionType.MOVE && moveCompleted) {
      movingPhase = true;
      moveCompleted = false;
      return true;
    }
    return false;
  }

  /**
   * Allows an extra build if the last action was a build and it was completed.
   *
   * @return True if extra build was activated, false otherwise.
   */
  public boolean activateExtraBuild() {
    if (lastAction.getActionType() == ActionType.BUILD && buildCompleted) {
      movingPhase = false;
      buildCompleted = false;
      return true;
    }
    return false;
  }

  /**
   * Undoes the last move or build action performed this turn.
   */
  public void undoLastAction() {
    if (lastAction != null) {
      String log = lastAction.undo();
      GameLog.logMessage(log);
      GameLog.logMessage("Last action undone.");
      lastAction = null;
      movingPhase = !buildCompleted;
      buildCompleted = false;
      workerSelected = false;
    } else {
      GameLog.logMessage("Error: Nothing to undo.");
    }
  }

  /**
   * Ends the current player's turn, resets phases, increments the turn counter as needed,
   * updates UI, and switches to the next player. Also updates the ChessClock to switch
   * timers between players. Handles dice rewards, including the Demolish reward.
   */
  public void endTurn() {
    if (movingPhase || !buildCompleted) {
      GameLog.logMessage("Error: You must move and build before ending turn!");
      return;
    }

    // ChessClock: pause current player's timer, switch, and start next player's timer.
    if (chessClock != null) {
      chessClock.pause();
      chessClock.switchTurn();
      chessClock.start();
    }

    // Show dice roll popup if needed (handled by postTurnCallback)
    if (postTurnCallBack != null) {
      this.postTurnCallBack.run();
    }

    // Switch to next player
    currentPlayer = PlayerUtils.getNextPlayer(playerList, currentPlayer);
    Game.setCurrentPlayer(currentPlayer);

    // Skip turn if blocked or lose turn
    boolean skippedTurn = false;

    // Check for Lose Turn
    if (currentPlayer.shouldLoseTurn()) {
      GameLog.logMessage(currentPlayer.getName() + " loses their turn due to last dice roll!");
      currentPlayer.setShouldLoseTurn(false);
      currentPlayer = PlayerUtils.getNextPlayer(playerList, currentPlayer);
      Game.setCurrentPlayer(currentPlayer);
      skippedTurn = true;
    }

    // Check for Blocked
    if (currentPlayer.isBlocked()) {
      GameLog.logMessage(currentPlayer.getName() + " is blocked this turn due to last dice roll!");
      currentPlayer.setBlocked(false);
      currentPlayer = PlayerUtils.getNextPlayer(playerList, currentPlayer);
      Game.setCurrentPlayer(currentPlayer);
      skippedTurn = true;
    }

    // Update God card info panel
    cardDisplay.updateCardPanel(currentPlayer);
    ImageUtils.updateCurrentPlayerDisplay(currentPlayer, currentPlayerColorIndicator, cardDisplay.getCardTitle());

    // Increment turn count if we're back to the starting player
    if (currentPlayer == startingPlayer) {
      turnCount++;
      GameLog.turnMessage("Turn #" + turnCount);
    }

    GameLog.logMessage("It is now " + currentPlayer.getName() + "'s turn.");

    // Announce any available dice rewards
    if (currentPlayer.hasExtraMove())
      GameLog.logMessage(currentPlayer.getName() + " has an extra move this turn from last dice roll!");
    if (currentPlayer.hasDoubleBuild())
      GameLog.logMessage(currentPlayer.getName() + " has double build this turn from last dice roll!");
    if (currentPlayer.canJumpTwoLevels())
      GameLog.logMessage(currentPlayer.getName() + " can jump two levels this turn from last dice roll!");
    if (currentPlayer.canDemolish())
      GameLog.logMessage(currentPlayer.getName() + " can demolish a building at the start of this turn!");

    // Handle Demolish Reward
    if (currentPlayer.canDemolish()) {
      handleDemolishReward();
      currentPlayer.setCanDemolish(false);
    }

    // Reset ability at every end turn
    abilityUsedThisTurn = false;

    // Reset phase and double build state
    movingPhase = true;
    workerSelected = false;
    moveCompleted = false;
    buildCompleted = false;
    lastAction = null;

    doubleBuildInProgress = false;
    firstBuildCell = null;
    currentPlayer.setHasDoubleBuild(false);
  }


  /**
   * Allows the current player to demolish (remove the top level from) any building on the board.
   * Pops up a dialog to enter cell coordinates and updates the board accordingly.
   */
  private void handleDemolishReward() {
    GameLog.logMessage(currentPlayer.getName() + " can select a building to demolish (remove top level).");
    JOptionPane.showMessageDialog(null, currentPlayer.getName() +
            ": Select a cell on the board to demolish (remove the top level of a building).", "Demolish Reward", JOptionPane.INFORMATION_MESSAGE);

    String input = JOptionPane.showInputDialog(null, "Enter cell coordinates to demolish (row,col):", "e.g., 2,3");
    if (input != null && input.contains(",")) {
      String[] parts = input.split(",");
      try {
        int row = Integer.parseInt(parts[0].trim());
        int col = Integer.parseInt(parts[1].trim());
        Cell cell = boardGUI.getBoard().getCell(row, col);

        int currentLevel = cell.getBuilding().getLevel();
        if (currentLevel > 0) {
          // Downgrade the building to the previous level
          cell.setBuilding(cell.getBuilding().previous());
          GameLog.logMessage("Demolished cell at (" + row + "," + col + "). New level: " + cell.getBuilding().getLevel());
          boardGUI.getButton(row, col).setUpDisplay();
        } else {
          GameLog.logMessage("Cell at (" + row + "," + col + ") has no building to demolish.");
        }
      } catch (Exception e) {
        GameLog.logMessage("Invalid input for demolish cell.");
      }
    }
  }


  /**
   * Checks if the current player is a winner by reaching a level 3 building.
   * If so, shows the result screen.
   *
   * @param winnerCell The cell the worker moved to.
   * @param winner The player who may have won.
   */
  public void checkWinner(Cell winnerCell, Player winner) {
    if (winnerCell.getBuilding().getLevel() == 3) {
      ScreenManager.registerScreen("RESULT", new ResultScreen(winner, turnCount));
      ScreenManager.showScreen("RESULT");
    }
  }
}
