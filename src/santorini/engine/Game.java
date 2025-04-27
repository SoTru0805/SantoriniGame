package santorini.engine;

import santorini.board.Board;

public class Game {
  private static Player player1;
  private static Player player2;
  private static Player currentPlayer;
  private static Board board;

  public static void initializeGame(Player p1, Player p2, Board b) {
    player1 = p1;
    player2 = p2;
    board = b;
    currentPlayer = player1; // Player 1 starts
  }

  public static Player getPlayer1() {
    return player1;
  }

  public static Player getPlayer2() {
    return player2;
  }

  public static Player getCurrentPlayer() {
    return currentPlayer;
  }

  public static Board getBoard() {
    return board;
  }

  public static void endTurn() {
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }
  }
}
