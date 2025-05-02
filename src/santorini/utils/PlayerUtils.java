package santorini.utils;

import java.awt.*;
import java.util.*;
import java.util.List;

import santorini.board.Board;
import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.Player;

import javax.swing.*;

public class PlayerUtils {

  private static final Random random = new Random();
  private static final int PLAYERS_HIGHER_BOUND = 4;
  private static final int PLAYERS_LOWER_BOUND = 1;
  public static String askName(String prompt) {
    String name;
    do {
      name = JOptionPane.showInputDialog(null, "Enter " + prompt + " Name:", "Santorini", JOptionPane.PLAIN_MESSAGE);
    } while (name == null || name.trim().isEmpty());
    return name.trim();
  }
  public static void askNamesForPlayers(List<Player> players) {
    for (int i = 0; i < players.size(); i++) {
      String name = askName("Player " + (i + 1));
      players.get(i).setName(name);
    }
  }
  public static Player getRandomPlayer(List<Player> players) {
    if (players == null || players.size() <= PLAYERS_LOWER_BOUND) {
      throw new IllegalArgumentException("Player list must not be null or contain fewer than 1 Player.");
    }
    if (players.size() > PLAYERS_HIGHER_BOUND){
      throw new IllegalArgumentException("Player list must not reach more than 4");
    }
    return players.get(random.nextInt(players.size()));
  }
  public static void shufflePlayers(List<Player> players) {
    Collections.shuffle(players);
  }
  public static Player getNextPlayer(List<Player> players, Player currentPlayer) {
    int currentIndex = players.indexOf(currentPlayer);
    if (currentIndex == -1 || players.isEmpty()) {
      throw new IllegalArgumentException("Current player not found in player list.");
    }
    int nextIndex = (currentIndex + 1) % players.size();
    return players.get(nextIndex);
  }
  public static void randomizeWorkers(Board board, List<Player> players) {
    if (players.size() < 2 || players.size() > 4) {
      throw new IllegalArgumentException("Player list must contain between 2 and 4 players.");
    }

    // Gather all empty spots on the board
    List<Point> emptySpots = new ArrayList<>();
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (board.getCell(i, j).getWorker() == null) {
          emptySpots.add(new Point(i, j));
        }
      }
    }

    if (emptySpots.size() < 4) {
      throw new IllegalStateException("Not enough empty cells to place workers.");
    }

    Collections.shuffle(emptySpots);

    // Determine worker count per player
    Map<Player, Integer> workerDistribution = new HashMap<>();

    // 2 players total
    if (players.size() == 2) {
      workerDistribution.put(players.get(0), 2);
      workerDistribution.put(players.get(1), 2);
    } else if (players.size() == 3) {
      // Randomly pick one player to get 2 workers for 3 players total
      Player lucky = players.get(new Random().nextInt(3));
      for (Player p : players) {
        workerDistribution.put(p, (p == lucky) ? 2 : 1);
      }
    } else { // 4 players total
      for (Player p : players) {
        workerDistribution.put(p, 1);
      }
    }

    // Place workers onto the board
    for (Player player : players) {
      int workerCount = workerDistribution.get(player);
      for (int i = 0; i < workerCount; i++) {
        Point p = emptySpots.remove(0);
        Cell cell = board.getCell(p.x, p.y);
        Worker worker = new Worker(player, player.getWorkers().size());
        cell.setWorker(worker);
        worker.setCurrentLocation(cell);
        player.addWorker(worker);
      }
    }
  }
}
