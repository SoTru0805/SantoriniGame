package santorini.engine;

import santorini.board.Board;
import santorini.board.Cell;
import santorini.elements.Building;
import santorini.elements.Dome;
import santorini.actions.Action;
import santorini.actions.MoveAction;
import santorini.actions.BuildAction;
import santorini.actions.EndTurnAction;

public class GameLogic {
  private static Board board;
  private static Player player1;
  private static Player player2;
  private static Player currentPlayer;
  private static Action lastAction = null;
  private static int turnNumber = 1;

  public static void initializeGame(Player p1, Player p2, Board gameBoard) {
    player1 = p1;
    player2 = p2;
    currentPlayer = player1;
    board = gameBoard;
  }

  public static String moveWorker(Cell from, Cell to) {
    MoveAction move = new MoveAction(currentPlayer, from, to);
    String result = move.execute();
    lastAction = move;
    return result;
  }

  public static String buildLevel(Cell target) {
    if (target.hasDome()) {
      return "Cannot build on dome!";
    }
    BuildAction build = new BuildAction(currentPlayer, target);
    String result = build.execute();
    lastAction = build;
    return result;
  }

  public static String endTurn() {
    EndTurnAction end = new EndTurnAction(currentPlayer);
    String result = end.execute();
    lastAction = null; // No undo after ending turn
    currentPlayer = (currentPlayer == player1) ? player2 : player1;
    turnNumber++;
    return result;
  }

  public static String undo() {
    if (lastAction != null) {
      lastAction.undo();
      String undoMessage = "Undo performed.";
      lastAction = null;
      return undoMessage;
    }
    return "No action to undo!";
  }

  public static Board getBoard() {
    return board;
  }

  public static Player getCurrentPlayer() {
    return currentPlayer;
  }

  public static int getTurnNumber() {
    return turnNumber;
  }
}
