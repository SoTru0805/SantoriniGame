//package santorini.elements;
//
//import santorini.board.Cell;
//import santorini.engine.Player;
//
//public class Worker {
//  private int id;                     // Unique ID for this worker
//  private Player player;             // Player who owns this worker
//  private Cell currentLocation;      // Current position on the board
//  private Cell previousCell;         // Previous cell before last move (for undo)
//
//  public Worker(int id, Player player, Cell currentLocation) {
//    this.id = id;
//    this.player = player;
//    this.currentLocation = currentLocation;
//  }
//
//  public int getId() {
//    return id;
//  }
//
//  public Player getPlayer() {
//    return player;
//  }
//
//  public Cell getCurrentLocation() {
//    return currentLocation;
//  }
//
//  public void moveTo(Cell newCell) {
//    this.previousCell = currentLocation;
//    this.currentLocation = newCell;
//  }
//
//  public void undoMove() {
//    if (previousCell != null) {
//      this.currentLocation = previousCell;
//    }
//  }
//
//  @Override
//  public String toString() {
//    // Optional display format like: W1(P) where P is player’s first letter
//    return "W" + id + "(" + player.getName().charAt(0) + ")";
//  }
//}
