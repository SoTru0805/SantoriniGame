package santorini.board;
import santorini.elements.Worker;
import santorini.elements.Building;

/**
 * Represents a cell on the Santorini game board.
 * Each cell can have a building and/or a worker.
 */
public class Cell {

    private int x; // Coordinate on the board
    private int y; // Coordinate on the board
    private Building buildingOnCell; // Building on this cell (can be null)
    private Worker worker; // Worker on this cell (can be null)

    /**
     * Constructs a new Cell with coordinates.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.buildingOnCell = null; // No building initially
        this.worker = null; // No worker initially
    }

    /**
     * Gets the x-coordinate of the cell.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the cell.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the building on this cell.
     *
     * @return The Building object, or null if there is no building.
     */
    public Building getBuildingOnCell() {
        return buildingOnCell;
    }

    /**
     * Sets the building on this cell.
     *
     * @param buildingOnCell The Building object to set.
     */
    public void setBuildingOnCell(Building buildingOnCell) {
        this.buildingOnCell = buildingOnCell;
    }

    /**
     * Gets the worker on this cell.
     *
     * @return The Worker object, or null if the cell is not occupied.
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Sets the worker on this cell.
     *
     * @param worker The Worker object to set.
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Removes the worker from this cell.
     */
    public void clearWorker() {
        this.worker = null;
    }

    /**
     * Checks if the cell is occupied by a worker.
     *
     * @return True if a worker is on the cell, false otherwise.
     */
    public boolean isOccupied() {
        return worker != null;
    }

    /**
     * Checks if a worker can move onto this cell.
     *
     * @param worker The worker trying to move.
     * @return True if the worker can move onto the cell, false otherwise.
     */
    public boolean canMoveTo(Worker worker) {
        return this.worker == null && (buildingOnCell == null || !buildingOnCell.hasDome());
    }

    /**
     * Checks if a building can be added or leveled up on this cell.
     *
     * @return True if building is allowed, false otherwise.
     */
    public boolean canBuild() {
        return buildingOnCell != null ? buildingOnCell.getLevel() < 3 && !buildingOnCell.hasDome() : true;
    }

    /**
     * Returns a string representation of the cell.
     *
     * @return A string containing cell information.
     */
    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", buildingOnCell=" + buildingOnCell +
                ", worker=" + (worker != null ? worker.getId() : "null") +
                '}';
    }
}
