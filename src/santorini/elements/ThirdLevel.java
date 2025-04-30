package santorini.elements;

import santorini.board.Cell;

public class ThirdLevel extends Building {
  public ThirdLevel(Cell cell) {
    super(cell);
  }

  @Override
  public int getLevel() {
    return 3;
  }

  @Override
  public String getSymbol() {
    return "L3";
  }

  @Override
  public Building next() {
    return new Dome(cell);
  }

  @Override
  public Building previous() {
    return new SecondLevel(cell);
  }
}