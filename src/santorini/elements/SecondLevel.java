package santorini.elements;

import santorini.board.Cell;

public class SecondLevel extends Building {
  public SecondLevel(Cell cell) {
    super(cell);
  }

  @Override
  public int getLevel() {
    return 2;
  }

  @Override
  public String getSymbol() {
    return "L2";
  }

  @Override
  public Building next() {
    return new ThirdLevel(cell);
  }

  @Override
  public Building previous() {
    return new FirstLevel(cell);
  }
}
