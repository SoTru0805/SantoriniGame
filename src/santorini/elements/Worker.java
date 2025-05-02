package santorini.elements;

import santorini.engine.Player;
import santorini.board.Cell;

/**
 * Represents a worker in the Santorini game.
 * Each worker belongs to a player and has a current location.
 */
public class Worker {

    private int id;
    private Cell currentLocation;
    private Player player;

    /**
     * Constructs a new Worker.
     * @param id The unique identifier for this worker.
     * @param player The player who owns this worker.
     */
    public Worker(Player player, int id) {
        this.id = id;
        this.player = player;
        this.currentLocation = null;
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

    public void setPlayer(Player player){
        this.player = player;
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
    public String getPlayerName() {
        return player.getName();
    }
}
