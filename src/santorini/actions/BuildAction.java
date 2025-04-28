package santorini.actions;

import santorini.board.Cell;
import santorini.engine.Player;

public class BuildAction extends Action {
  private Cell selected;
  private final Cell target;

  public BuildAction(Player player, Cell selected, Cell target) {
    super(player);
    this.selected = selected;
    this.target = target;
  }

  public BuildAction(Player player, Cell target) {
    super(player);
    this.target = target;
  }

  @Override
  public String execute() {
    if (!target.isOccupied() && !target.hasDome()){
      String previousLevel = target.getDisplaySymbol();
      target.build();
      return player.getName() + " built on (" + target.getRow() + "," + target.getCol() + ") from " + previousLevel + " to " + target.getDisplaySymbol() + ".";
    } else if (target.isOccupied() && !target.hasDome()){
      return player.getName() + " cannot build on this position due to the existence of a worker.";
    } else {
      return player.getName() + " cannot build on top of a dome.";
    }
  }

  @Override
  public String undo() {
    String currentLevel = target.getDisplaySymbol();
    target.undoBuild();
    return player.getName() + " undo the build from " + currentLevel + " back to " + target.getDisplaySymbol() + ".";
  }
}
