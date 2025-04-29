// Action.java
package santorini.actions;

import santorini.board.Cell;
import santorini.engine.Player;

public abstract class Action {
  protected Player player;

  public Action(Player player) {
    this.player = player;
  }

  public abstract String execute();
  public abstract Boolean status();

  public abstract String undo();

  public boolean isAdjacent(Cell a, Cell b) {
    int dr = Math.abs(a.getRow() - b.getRow());
    int dc = Math.abs(a.getCol() - b.getCol());
    return (dr <= 1 && dc <= 1) && !(dr == 0 && dc == 0);
  }
}
