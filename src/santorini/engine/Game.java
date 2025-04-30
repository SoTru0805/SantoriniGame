package santorini.engine;

import santorini.board.Board;
import santorini.screens.GameScreen;

public class Game {
  private static Player currentPlayer;
  private static Board board;

  public static void setCurrentPlayer(Player p) {
    currentPlayer = p;
  }

  public static Board getBoard() {
    return board;
  }

}
