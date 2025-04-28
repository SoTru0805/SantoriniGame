package santorini.actions;

import santorini.board.Cell;
import santorini.engine.Player;

public class MoveAction extends Action {
  private final Cell selected;
  private final Cell target;

  public MoveAction(Player player, Cell selected, Cell target) {
    super(player);
    this.selected = selected;
    this.target = target;
  }

  @Override
  public String execute() {
    if (!target.isOccupied() && !target.hasDome()){
      selected.removeWorker();
      target.setWorker(player);
      return player.getName() + " moved from (" + selected.getRow() + "," + selected.getCol() + ") to (" + target.getRow() + "," + target.getCol() + ")";
    } else if (!target.isOccupied() && target.hasDome()){
      return player.getName() + " cannot move to a dome.";
    } else {
      return player.getName() + " cannot move to a position that already has a worker.";
    }
  }

  @Override
  public String undo() {
    target.removeWorker();
    selected.setWorker(player);
    return player.getName() + " undo move back to (" + selected.getRow() + "," + selected.getCol() + ")";
  }
}
