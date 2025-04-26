package santorini.elements;

import santorini.engine.Player;
import santorini.board.Cell;

/**
 * Represents a worker in the Santorini game.
 * Each worker belongs to a player and has a current location.
 */
public class Worker {

<<<<<<< Updated upstream
}
=======
    private int id;
    private Cell currentLocation;
    private Player player;
    private Cell previousLocation;

    /**
     * Constructs a new Worker.
     * @param id The unique identifier for this worker.
     * @param player The player who owns this worker.
     */
    public Worker(int id, Player player) {
        this.id = id;
        this.player = player;
        this.currentLocation = null;
        this.previousLocation = null;
    }

    /**
     * Gets the unique identifier of this worker.
     * @return The worker's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the current location of this worker.
     * @return The Cell where the worker is currently located.
     */
    public Cell getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the current location of this worker.
     * @param currentLocation The Cell to set as the worker's current location.
     */
    public void setCurrentLocation(Cell currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * Gets the player who owns this worker.
     * @return The Player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the previous location of this worker.
     * @return The Cell where the worker was previously located.
     */
    public Cell getPreviousLocation() {
        return previousLocation;
    }

    /**
     * Sets the previous location of this worker.
     * @param previousLocation The Cell to set as the worker's previous location.
     */
    public void setPreviousLocation(Cell previousLocation) {
        this.previousLocation = previousLocation;
    }

    /**
     * Moves the worker to a new cell.
     * NOTE:  Validation should be done by the Player class!
     * @param newLocation The Cell to move the worker to.
     */
    public void moveTo(Cell newLocation) {
        this.previousLocation = this.currentLocation;
        this.currentLocation = newLocation;
    }

    /**
     * Builds a block on a cell.
     * NOTE: Validation should be done by the Player class!
     * @param cell The Cell to build on.
     */
    public void buildBlock(Cell cell) {
        //  TODO: Implement the logic to build a block on the cell
        //  This might involve updating the cell's level or structure.
        System.out.println("Worker " + id + " built on cell: " + cell);
    }

    /**
     * Undoes the last move.
     * NOTE: This assumes we only need to undo one move. More complex undo might need a stack.
     */
    public void undoMove() {
        if (previousLocation != null) {
            currentLocation = previousLocation;
            previousLocation = null; //  Clear the previous location after undoing
        }
    }
}
>>>>>>> Stashed changes
