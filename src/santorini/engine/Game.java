package santorini.engine;

import santorini.board.Board;
import santorini.screens.GameScreen;
import santorini.turn.TurnManager;

public class Game {
  private static Player player1;
  private static Player player2;
  private static Board board;
  private static TurnManager turnManager;

  public static void initializeGame(Player p1, Player p2, Board b) {
    player1 = p1;
    player2 = p2;
    board = b;
    turnManager = new TurnManager(player1, player2);
  }

  public static Player getCurrentPlayer() {
    return turnManager.getCurrentPlayer();
  }

  public static Board getBoard() {
    return board;
  }

  public static void endTurn() {
    turnManager.endTurn();
  }

  public static int getTurnCount() {
    return turnManager.getTurnCount();
  }

  public static void playerClicked(int row, int col) {
    String message = getCurrentPlayer().getName() + " clicked on (" + row + ", " + col + ")";
    GameScreen.logMessage(message);
    System.out.println(message);
  }
}
