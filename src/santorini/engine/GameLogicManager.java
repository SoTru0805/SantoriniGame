package santorini.engine;

import santorini.board.Board;
import santorini.board.Cell;
import santorini.board.BoardGUI;
import santorini.actions.Action;
import santorini.actions.MoveAction;
import santorini.actions.BuildAction;
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
        // Select worker
        if (clickedCell.getWorker() == currentPlayer) {
          selectedWorkerCell = clickedCell;
          workerSelected = true;
          GameScreen.logMessage(currentPlayer.getName() + " selected a worker.");
        } else {
          GameScreen.logMessage("Invalid selection. Select your own worker.");
        }
      } else {
        // Try to move
        MoveAction moveAction = new MoveAction(currentPlayer, selectedWorkerCell, clickedCell);
        String log = moveAction.execute();
        lastAction = moveAction;
        GameScreen.logMessage(log);
        movingPhase = false; // Now switch to building
        workerSelected = false;
      }
    } else {
      // Building phase
      BuildAction buildAction = new BuildAction(currentPlayer, selectedWorkerCell, clickedCell);
      String log = buildAction.execute();
      lastAction = buildAction;
      GameScreen.logMessage(log);
      GameScreen.logMessage(currentPlayer.getName() + " must now end the turn.");
    }
  }

  public void undoLastAction() {
    if (lastAction != null) {
      lastAction.undo();
      GameScreen.logMessage("Last action undone.");
      lastAction = null;
      movingPhase = true;
      workerSelected = false;
    } else {
      GameScreen.logMessage("Nothing to undo.");
    }
  }

  public void endTurn() {
    if (movingPhase) {
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
    lastAction = null;
  }
}
