package santorini.board;

import santorini.elements.Building;
import santorini.engine.Player;

import java.awt.*;

public class Cell {
    private int row;
    private int col;
    private Building building;
    private Player worker;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.building = new Building();
        this.worker = null;
    }

    public Building getBuilding() {
        return building;
    }

    public Player getWorker() {
        return worker;
    }

    public void setWorker(Player worker) {
        this.worker = worker;
    }

    public void removeWorker() {
        this.worker = null;
    }

    public boolean isOccupied() {
        return worker != null;
    }

    public boolean hasDome() {
        return building.hasDome();
    }

    public int getLevel() {
        return building.getLevel();
    }

    public void build() {
        building.build();
    }

    public void undoBuild() {
        building.undoBuild();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getDisplaySymbol() {
        if (building != null) {
            return building.getSymbol();
        }
        return "";
    }
}
