package santorini.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import santorini.board.Board;
import santorini.elements.Worker;
import santorini.engine.Player;

public class PlayerUtils {

  private static final Random random = new Random();

  public static Player getRandomPlayer(Player p1, Player p2) {
    return random.nextBoolean() ? p1 : p2;
  }

  public static void randomizeWorkers(Board board, Player player1, Player player2) {
    List<Point> emptySpots = new ArrayList<>();
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (board.getCell(i, j).getWorker() == null) {
          emptySpots.add(new Point(i, j));
        }
      }
    }
    Collections.shuffle(emptySpots);

    for (int i = 0; i < 2; i++) {
      Point p = emptySpots.remove(0);
      Worker newWorker = new Worker(player1, player1.getWorkers().size());
      board.getCell(p.x, p.y).setWorker(newWorker);
      newWorker.setCurrentLocation(board.getCell(p.x, p.y));
      player1.addWorker(newWorker);
    }

    for (int i = 0; i < 2; i++) {
      Point p = emptySpots.remove(0);
      Worker newWorker = new Worker(player2, player2.getWorkers().size());
      board.getCell(p.x, p.y).setWorker(newWorker);
      newWorker.setCurrentLocation(board.getCell(p.x, p.y));
      player2.addWorker(newWorker);
    }
  }
}
