// Action.java
package santorini.actions;

import santorini.engine.Player;

public abstract class Action {
  protected Player player;

  public Action(Player player) {
    this.player = player;
  }

  public abstract String execute();

  public abstract String undo();
}
