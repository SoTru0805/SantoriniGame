// Action.java
package santorini.actions;

import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.Player;


public abstract class Action {
  protected Player player;
  protected Worker worker;
  protected Cell selected;
  protected Cell target;


  public Action(Player player) {
    this.player = player;
  }
  public Worker getWorker() {
    return selected.getWorker();
  }

  public abstract String execute();
  public abstract Boolean status();

  public abstract String undo();
  public abstract void setExcludedCell(Cell excludedCell);

  public boolean isAdjacent(Cell a, Cell b) {
    int dr = Math.abs(a.getRow() - b.getRow());
    int dc = Math.abs(a.getCol() - b.getCol());
    return (dr <= 1 && dc <= 1) && !(dr == 0 && dc == 0);
  }

  public abstract ActionType getActionType();
}
