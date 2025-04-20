//package santorini.elements;
//
//import santorini.engine.Player;
//import santorini.board.Cell;
//
//import java.util.List;
//
//public class GodCard {
//  private String name;
//  private String description;
//  private Player player;
//
//  public GodCard(String name) {
//    this.name = name;
//    this.description = switch (name) {
//      case "Artemis" -> "Your Worker may move one additional time, but not back to its initial space.";
//      case "Demeter" -> "Your Worker may build one additional time, but not on the same space.";
//      default -> "No description available.";
//    };
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public String getDescription() {
//    return description;
//  }
//
//  public void setPlayer(Player player) {
//    this.player = player;
//  }
//
//  public Player getPlayer() {
//    return player;
//  }
//
//  // These methods can be overridden later for specific powers
//  public void useEffect() {
//    // Optional custom behavior per god
//  }
//
//  public List<Cell> modifyMove(List<Cell> cells) {
//    return cells; // Default: no change
//  }
//
//  public List<Cell> modifyBuild(List<Cell> cells) {
//    return cells; // Default: no change
//  }
//
//  public void onTurnStart() {
//    // Called at beginning of player's turn
//  }
//
//  public void onTurnEnd() {
//    // Called at end of player's turn
//  }
//}
