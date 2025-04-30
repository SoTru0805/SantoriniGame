package santorini.board;

import santorini.elements.Building;
import santorini.elements.Ground;
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
        this.building = new Ground(this);
        this.worker = null;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getLevel() {
        return building.getLevel();
    }

    public boolean hasDome() {
        return building.getSymbol().equals("D");
    }

    public void build() {
        this.building = building.next();
    }

    public void undoBuild() {
        this.building = building.previous();
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getDisplaySymbol() {
        return building.getSymbol(); // "", "L1", "L2", "L3", "D"
    }
}
