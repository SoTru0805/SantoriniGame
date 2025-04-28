package santorini.engine;

import santorini.board.Cell;
import santorini.elements.Building;

public class TurnManager {
  private static int turnCounter = 1;
  private static boolean hasMoved = false;
  private static boolean hasBuilt = false;

  public static String playerMove(Cell from, Cell to) {
    if (hasMoved) {
      return "Already moved this turn!";
    }
    String result = GameLogic.moveWorker(from, to);
    if (!result.startsWith("Invalid")) {
      hasMoved = true;
    }
    return result;
  }

  public static void endTurn() {
    Player previous = Game.getCurrentPlayer();
    if (previous.equals(Game.getPlayer1())) {
      Game.setCurrentPlayer(Game.getPlayer2());
    } else {
      Game.setCurrentPlayer(Game.getPlayer1());
      turnCounter++;
    }
  }

  public static int getTurnCounter() {
    return turnCounter;
  }

  public static String playerBuild(Cell target) {
    if (!hasMoved) {
      return "You must move before building!";
    }
    if (hasBuilt) {
      return "Already built this turn!";
    }
    String result = GameLogic.buildLevel(target);
    if (!result.startsWith("Invalid")) {
      hasBuilt = true;
    }
    return result;
  }

  public static String endPlayerTurn() {
    if (!hasMoved || !hasBuilt) {
      return "You must move and build before ending your turn!";
    }
    String result = GameLogic.endTurn();
    resetTurn();
    return result;
  }

  public static String undoLastAction() {
    if (hasBuilt) {
      hasBuilt = false;
    } else if (hasMoved) {
      hasMoved = false;
    }
    return GameLogic.undo();
  }

  private static void resetTurn() {
    hasMoved = false;
    hasBuilt = false;
  }
}
