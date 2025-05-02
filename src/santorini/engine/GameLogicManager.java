package santorini.engine;

import santorini.actions.ActionType;
import santorini.board.Cell;
import santorini.board.BoardGUI;
import santorini.actions.Action;
import santorini.actions.MoveAction;
import santorini.actions.BuildAction;
import santorini.screens.ResultScreen;
import santorini.screens.ScreenManager;
import santorini.utils.ImageUtils;
import santorini.utils.PlayerUtils;

import javax.swing.*;
import java.util.List;

public class GameLogicManager {
  private BoardGUI boardGUI;
  private List<Player> playerList;
  private Player currentPlayer, startingPlayer;

  private boolean movingPhase = true;
  private boolean moveCompleted, buildCompleted = false;
  private boolean workerSelected = false;
  private Cell selectedWorkerCell;
  private Action lastAction = null;
  private int turnCount = 1;
  private boolean abilityUsedThisTurn = false;
  private Cell tempTarget, previousCell = null;
  private CardDisplay cardDisplay;

  public GameLogicManager(BoardGUI boardGUI, List<Player> playerList, Player startingPlayer, CardDisplay cardDisplay) {
    this.boardGUI = boardGUI;
    this.playerList = playerList;
    this.startingPlayer = startingPlayer;
    this.currentPlayer = startingPlayer;
    this.cardDisplay = cardDisplay;
  }

  public void handleCellClick(Cell clickedCell) {
    if (movingPhase) {
      if (!workerSelected) {
        if (clickedCell.isOccupied()) {
          if (clickedCell.getWorker().getPlayer() == currentPlayer){
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
        if (moveAction.status()){
          lastAction = moveAction;
          tempTarget = null;

          movingPhase = false;
          workerSelected = false;
          moveCompleted = true;

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
        buildAction.setExcludedCell(tempTarget);
        String log = buildAction.execute();
        GameLog.logMessage(log);
        if (buildAction.status()){
          previousCell = clickedCell;
          lastAction = buildAction;
          tempTarget = null;

          buildCompleted = true;
          workerSelected = false;

          GameLog.logMessage(currentPlayer.getName() + " must now end the turn.");
        }
      }
    }
  }

  public Action getLastAction(){
    return lastAction;
  }

  public boolean activateAbility(){
    boolean status = false;

    if (abilityUsedThisTurn){
      return status;
    }

    status = currentPlayer.getGodCard().useEffect(this);
    if (status){
      lastAction.setExcludedCell(tempTarget);

      abilityUsedThisTurn = true;
      workerSelected = true;
      tempTarget = previousCell;
    }
    return status;
  }


  public boolean activateExtraMove(){
    if (lastAction.getActionType() == ActionType.MOVE && moveCompleted){
      movingPhase = true;
      moveCompleted = false;
      return true;
    }
    return false;
  }

  public boolean activateExtraBuild(){
    if (lastAction.getActionType() == ActionType.BUILD && buildCompleted){
      movingPhase = false;
      buildCompleted = false;
      return true;
    }
    return false;
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
      GameLog.logMessage("Error: Nothing to undo.");
    }
  }

  public void endTurn() {
    if (movingPhase || !buildCompleted) {
      GameLog.logMessage("Error: You must move and build before ending turn!");
      return;
    }

    // Switch player
    currentPlayer = PlayerUtils.getNextPlayer(playerList, currentPlayer);
    Game.setCurrentPlayer(currentPlayer);

    // Update God card info panel
    cardDisplay.updateCardPanel(currentPlayer);

    // Increment turn count
    if (currentPlayer == startingPlayer){
      turnCount++;
      GameLog.turnMessage("Turn #" + turnCount);
    }

    GameLog.logMessage("It is now " + currentPlayer.getName() + " turn.");

    // Reset ability at every end turn
    abilityUsedThisTurn = false;

    // Reset phase
    movingPhase = true;
    workerSelected = false;
    moveCompleted = false;
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
