package santorini.engine;

import santorini.board.Board;
import santorini.board.Cell;
import santorini.board.BoardGUI;
import santorini.actions.Action;
import santorini.actions.MoveAction;
import santorini.actions.BuildAction;
import santorini.board.CellButton;
import santorini.screens.GameScreen;

import javax.swing.*;
import java.awt.*;

public class GameLogicManager {

  private Board board;
  private BoardGUI boardGUI;
  private Player player1, player2;
  private Player currentPlayer;
  private JTextArea gameLog;
  private JLabel cardTitle, cardName, cardDescription;

  private boolean movingPhase = true;
  private boolean buildCompleted = false;
  private boolean workerSelected = false;
  private Cell selectedWorkerCell;
  private Action lastAction = null;
  private int turnCount = 1;

  public GameLogicManager(Board board, BoardGUI boardGUI, Player player1, Player player2,
                          JTextArea gameLog, JLabel cardTitle, JLabel cardName, JLabel cardDescription) {
    this.board = board;
    this.boardGUI = boardGUI;
    this.player1 = player1;
    this.player2 = player2;
    this.gameLog = gameLog;
    this.cardTitle = cardTitle;
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.currentPlayer = player1;

    Game.setCurrentPlayer(currentPlayer);
  }

  public void handleCellClick(Cell clickedCell) {
    if (movingPhase) {
      if (!workerSelected) {
        if (clickedCell.isOccupied()) {
          if (clickedCell.getWorker().getPlayer() == currentPlayer){
            selectedWorkerCell = clickedCell;
            workerSelected = true;
            GameScreen.logMessage(currentPlayer.getName() + " selected a worker to move.");
          } else {
            GameScreen.logMessage("Error: Invalid selection. Select your own worker.");
          }
        } else {
          GameScreen.logMessage("Error: The cell does not have your worker.");
        }
      } else {
        MoveAction moveAction = new MoveAction(boardGUI, currentPlayer, selectedWorkerCell, clickedCell);
        String log = moveAction.execute();
        GameScreen.logMessage(log);
        if (moveAction.status()){
          lastAction = moveAction;

          movingPhase = false;
          workerSelected = false;
        }
      }
    } else {
      if (buildCompleted) {
        GameScreen.logMessage(currentPlayer.getName() + " has already built! Please end your turn.");
        return;
      }

      if (!workerSelected) {
        if (clickedCell.isOccupied()) {
          if (clickedCell.getWorker().getPlayer() == currentPlayer){
            selectedWorkerCell = clickedCell;
            workerSelected = true;
            GameScreen.logMessage(currentPlayer.getName() + " selected a worker to build.");
          } else {
            GameScreen.logMessage("Error: Invalid selection. Select your own worker.");
          }
        } else {
          GameScreen.logMessage("Error: The cell does not have your worker.");
        }
      } else {
        BuildAction buildAction = new BuildAction(boardGUI, currentPlayer, selectedWorkerCell, clickedCell);
        String log = buildAction.execute();
        GameScreen.logMessage(log);
        if (buildAction.status()){
          lastAction = buildAction;
          buildCompleted = true;
          workerSelected = false;

          GameScreen.logMessage(currentPlayer.getName() + " must now end the turn.");
        }
      }
    }
  }

  public void undoLastAction() {
    if (lastAction != null) {
      String log = lastAction.undo();
      GameScreen.logMessage(log);
      GameScreen.logMessage("Last action undone.");
      lastAction = null;
      movingPhase = !buildCompleted;
      buildCompleted = false;
      workerSelected = false;
    } else {
      GameScreen.logMessage("Nothing to undo.");
    }
  }

  public void endTurn() {
    if (movingPhase || !buildCompleted) {
      GameScreen.logMessage("You must move and build before ending turn!");
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

    turnCount++;
    GameScreen.logMessage("Turn #" + turnCount + " - " + currentPlayer.getName() + " starts!");

    // Reset phase
    movingPhase = true;
    workerSelected = false;
    buildCompleted = false;
    lastAction = null;
  }
}
