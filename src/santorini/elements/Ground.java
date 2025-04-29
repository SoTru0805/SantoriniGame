package santorini.elements;

import santorini.board.Cell;

public class Ground extends Building {
  public Ground(Cell cell) {
    super(cell);
  }

  @Override
  public int getLevel() {
    return 0;
  }

  @Override
  public String getSymbol() {
    return "";
  }

  @Override
  public Building next() {
    return new FirstLevel(cell);
  }

  @Override
  public Building previous() {
    return this; // Ground has no lower level
  }
}