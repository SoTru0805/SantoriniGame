package santorini.elements;

import santorini.board.Cell;

public class FirstLevel extends Building {
  public FirstLevel(Cell cell) {
    super(cell);
  }

  @Override
  public int getLevel() {
    return 1;
  }

  @Override
  public String getSymbol() {
    return "L1";
  }

  @Override
  public Building next() {
    return new SecondLevel(cell);
  }

  @Override
  public Building previous() {
    return new Ground(cell);
  }
}
