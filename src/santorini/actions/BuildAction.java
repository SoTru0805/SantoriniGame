package santorini.actions;

import santorini.board.BoardGUI;
import santorini.board.Cell;
import santorini.elements.Building;
import santorini.elements.Ground;
import santorini.engine.Player;

public class BuildAction extends Action {
  private final BoardGUI boardGUI;
  private final Cell selected;
  private final Cell target;
  private boolean status;
  private Building previousLevel;
  private Building builtLevel;

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
      previousLevel = target.getBuilding();
      if (previousLevel == null) {
        previousLevel = new Ground(target);
      }

      builtLevel = previousLevel.next();
      if (builtLevel == previousLevel) {
        status = false;
        return "Build failed: Already at maximum level.";
      }

      target.setBuilding(builtLevel);

      boardGUI.getButton(target.getRow(),target.getCol()).setUpDisplay();

      status = true;

      String sym = previousLevel.getSymbol();
      if(sym == ""){
        sym = "Ground";
      }

      return player.getName() + " built on (" + target.getRow() + "," + target.getCol() + ") from " + sym + " to " + target.getDisplaySymbol() + ".";
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

    boardGUI.getButton(target.getRow(),target.getCol()).setUpDisplay();

    String sym = target.getDisplaySymbol();
    if (sym == ""){
      sym = "Ground";
    }

    return player.getName() + " undo the build from " + currentLevel + " back to " + sym + ".";
  }
}
