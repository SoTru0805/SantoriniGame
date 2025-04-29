package santorini.elements;

import santorini.board.Cell;

public class Dome extends Building {
  public Dome(Cell cell) {
    super(cell);
  }

  @Override
  public int getLevel() {
    return 4;
  }

  @Override
  public String getSymbol() {
    return "D";
  }

  @Override
  public Building next() {
    return this; // Dome is the highest level
  }

  @Override
  public Building previous() {
    return new ThirdLevel(cell);
  }
}