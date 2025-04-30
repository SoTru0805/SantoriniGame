package santorini.actions;

import santorini.board.Board;
import santorini.board.BoardGUI;
import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.Player;

public class MoveAction extends Action {
  private final BoardGUI boardGUI;
  private final Cell selected;
  private final Cell target;
  private Boolean status;

  public MoveAction(BoardGUI boardGUI, Player player, Cell selected, Cell target) {
    super(player);
    this.boardGUI = boardGUI;
    this.selected = selected;
    this.target = target;
    this.status = false;
  }

  @Override
  public String execute() {
    if (!isAdjacent(selected, target)) {
      return "Error: " + player.getName() + " cannot move too far away.";
    }

    if (target.isOccupied() || target.hasDome()) {
      return "Error: " + player.getName() + " cannot move to a cell that has a worker or a dome.";
    }

    int selectedLevel = selected.getBuilding().getLevel();
    int targetLevel = target.getBuilding().getLevel();

    if (targetLevel - selectedLevel > 1) {
      return "Error: " + player.getName() + " cannot move up more than one level.";
    }

    Worker selectedWorker = selected.getWorker();
    target.setWorker(selectedWorker);
    selectedWorker.setCurrentLocation(target);
    selected.removeWorker();

    boardGUI.getButton(selected.getRow(),selected.getCol()).setUpDisplay();
    boardGUI.getButton(target.getRow(),target.getCol()).setUpDisplay();

    status = true;

    return player.getName() + " moved from (" + selected.getRow() + "," + selected.getCol() + ") to (" + target.getRow() + "," + target.getCol() + "). Now, select a worker to build.";

  }
  @Override
  public Boolean status(){
    return status;
  }

  @Override
  public String undo() {
    Worker targetWorker = target.getWorker();
    selected.setWorker(targetWorker);
    targetWorker.setCurrentLocation(selected);
    target.removeWorker();

    boardGUI.getButton(target.getRow(),target.getCol()).setUpDisplay();
    boardGUI.getButton(selected.getRow(),selected.getCol()).setUpDisplay();

    return player.getName() + " undo move back to (" + selected.getRow() + "," + selected.getCol() + ")";
  }
}
