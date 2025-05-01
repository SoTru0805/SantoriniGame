package santorini.engine;

import santorini.board.Board;
import santorini.board.Cell;
import santorini.board.BoardGUI;
import santorini.actions.Action;
import santorini.actions.MoveAction;
import santorini.actions.BuildAction;
import santorini.board.CellButton;
import santorini.screens.GameScreen;
import santorini.screens.ResultScreen;
import santorini.screens.ScreenManager;
import santorini.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class GameLogicManager {
  private BoardGUI boardGUI;
  private Player player1, player2;
  private Player currentPlayer, startingPlayer;
  private JTextArea gameLog;
  private JLabel cardTitle, cardName, cardDescription;
  private JLabel godCardImage;
  private GameScreen gameScreen;

  private boolean movingPhase = true;
  private boolean buildCompleted = false;
  private boolean workerSelected = false;
  private Cell selectedWorkerCell;
  private Action lastAction = null;
  private int turnCount = 1;
  private JLabel currentPlayerNameLabel;
  private JPanel currentPlayerColorIndicator;

  public GameLogicManager(BoardGUI boardGUI, Player player1, Player player2, Player startingPlayer,
                          JTextArea gameLog, JLabel cardTitle, JLabel cardName, JLabel cardDescription, JLabel cardImage, GameScreen gameScreen) {
    this.boardGUI = boardGUI;
    this.player1 = player1;
    this.player2 = player2;
    this.gameLog = gameLog;
    this.cardTitle = cardTitle;
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.startingPlayer = startingPlayer;
    this.currentPlayer = startingPlayer;
    this.godCardImage = cardImage;
    this.gameScreen = gameScreen;
  }

  public void handleCellClick(Cell clickedCell) {
    if (movingPhase) {
      if (!workerSelected) {
        if (clickedCell.isOccupied()) {
          if (clickedCell.getWorker().getPlayer() == currentPlayer){
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
        String log = moveAction.execute();
        GameLog.logMessage(log);
        if (moveAction.status()){
          lastAction = moveAction;

          movingPhase = false;
          workerSelected = false;

          selectedWorkerCell = clickedCell;

          checkWinner(clickedCell, currentPlayer);
        }
      }
    } else {
      if (buildCompleted) {
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
        BuildAction buildAction = new BuildAction(boardGUI, currentPlayer, selectedWorkerCell, clickedCell);
        String log = buildAction.execute();
        GameLog.logMessage(log);
        if (buildAction.status()){
          lastAction = buildAction;
          buildCompleted = true;
          workerSelected = false;

          GameLog.logMessage(currentPlayer.getName() + " must now end the turn.");
        }
      }
    }
  }

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
      GameLog.logMessage("Nothing to undo.");
    }
  }

  public void endTurn() {
    if (movingPhase || !buildCompleted) {
      GameLog.logMessage("You must move and build before ending turn!");
      return;
    }
    // Switch player
    currentPlayer = (currentPlayer == player1) ? player2 : player1;
    Game.setCurrentPlayer(currentPlayer);

    // Update God card info panel
    cardTitle.setText(currentPlayer.getName() + "’s Card");
    cardName.setText(currentPlayer.getGodCard().getName());
    cardDescription.setText("<html><div style='text-align:center;'>" +
            currentPlayer.getGodCard().getDescription() + "</div></html>");
    ImageUtils.setScaledGodCardIcon(currentPlayer.getGodCard(), godCardImage, 200, 300);

    gameScreen.updateCurrentPlayerDisplay(currentPlayer);

    if (currentPlayer == startingPlayer){
      turnCount++;
      GameLog.turnMessage("Turn #" + turnCount);
    }

    GameLog.logMessage("It is now " + currentPlayer.getName() + " turn.");

    // Reset phase
    movingPhase = true;
    workerSelected = false;
    buildCompleted = false;
    lastAction = null;
  }

  public void checkWinner(Cell winnerCell, Player winner){
    if (winnerCell.getBuilding().getLevel() == 3) {
      ScreenManager.registerScreen("RESULT", new ResultScreen(winner, turnCount));
      ScreenManager.showScreen("RESULT");
    }
  }
}
