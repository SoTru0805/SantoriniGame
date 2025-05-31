package santorini.elements;

import santorini.board.Cell;

public abstract class Building {
    protected Cell cell;

    public Building(Cell cell) {
        this.cell = cell;
    }

    public abstract int getLevel();

    public abstract String getSymbol();

    public abstract Building next();

    public abstract Building previous();


}