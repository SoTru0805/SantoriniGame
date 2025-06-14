package santorini.actions;

import santorini.board.Cell;
import santorini.engine.Player;

public class EndTurnAction extends Action {
  public EndTurnAction(Player player) {
    super(player);
  }

  @Override
  public String execute() {
    return player.getName() + " ended their turn.";
  }

  @Override
  public Boolean status(){
    return true; // true as default
  }

  @Override
  public String undo() {
    return player.getName() + " cannot undo end turn.";
  }

  @Override
  public void setExcludedCell(Cell excludedCell) {}

  @Override
  public ActionType getActionType() {
    return null;
  }
}
