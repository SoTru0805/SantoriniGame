package santorini.actions;

import santorini.board.BoardGUI;
import santorini.board.Cell;
import santorini.engine.Player;

public class BuildAction extends Action {
  private final BoardGUI boardGUI;
  private final Cell selected;
  private final Cell target;
  private boolean status;

  public BuildAction(BoardGUI boardGUI, Player player, Cell selected, Cell target) {
    super(player);
    this.boardGUI = boardGUI;
    this.selected = selected;
    this.target = target;
    this.status = false;
  }

  @Override
  public String execute() {
    if (!isAdjacent(selected, target)) {
      return "Error: " + player.getName() + " cannot build too far.";
    }

    if (!target.isOccupied() && !target.hasDome()){
      String previousLevel = target.getDisplaySymbol();
      target.build();

//      boardGUI.getButton(target.getRow(),target.getCol()).buildDisplay(player);

      status = true;

      return player.getName() + " built on (" + target.getRow() + "," + target.getCol() + ") from " + previousLevel + " to " + target.getDisplaySymbol() + ".";
    } else if (target.isOccupied() && !target.hasDome()){
      return "Error: " + player.getName() + " cannot build on this position due to the existence of a worker.";
    } else {
      return "Error: " + player.getName() + " cannot build on top of a dome.";
    }
  }

  @Override
  public Boolean status(){
    return status;
  }

  @Override
  public String undo() {
    String currentLevel = target.getDisplaySymbol();
    target.undoBuild();

    status = false;

    return player.getName() + " undo the build from " + currentLevel + " back to " + target.getDisplaySymbol() + ".";
  }
}
