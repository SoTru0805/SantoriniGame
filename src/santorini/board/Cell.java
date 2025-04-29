package santorini.board;

import santorini.elements.Building;
import santorini.elements.Worker;

import javax.swing.*;

public class Cell extends JButton {
    private int row;
    private int col;
    private Building building;
    private Worker worker;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.building = new Building();
        this.worker = null;
    }

    public Building getBuilding() {
        return building;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
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
            return building.getSymbol(); // Example: "G", "L1", "L2", "L3", "D"
        }
        return "";
    }
}
