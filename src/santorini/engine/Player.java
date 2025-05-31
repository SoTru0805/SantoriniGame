package santorini.engine;
//import santorini.elements.GodCard;
//import santorini.elements.Worker;
import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.godcards.GodCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Santorini game.
 * Each player has a name, owns a set of workers, and have a God Card.
 */
public class Player {

    private String name;
    private List<Worker> workers;
    private GodCard godCard;
    private Color color;

    // Dice roll reward flags
    private boolean hasExtraMove = false;
    private boolean hasDoubleBuild = false;
    private boolean isBlocked = false;
    private boolean shouldLoseTurn = false;
    private boolean canJumpTwoLevels = false;
    private boolean canDemolish = false;



    /**
     * Checks if the player currently has the demolish ability.
     * @return true if the player can demolish, false otherwise.
     */
    public boolean canDemolish() {
        return canDemolish;
    }

    /**
     * Sets whether the player can demolish a building block on their next turn.
     * @param value true to grant the demolish ability, false to remove it.
     */
    public void setCanDemolish(boolean value) {
        this.canDemolish = value;
    }


    /**
     * Checks if the player has an extra move reward.
     * @return True if the player can make an extra move, false otherwise.
     */
    public boolean hasExtraMove() { return hasExtraMove; }

    /**
     * Sets whether the player has an extra move reward.
     * @param value True to give extra move, false otherwise.
     */
    public void setHasExtraMove(boolean value) { hasExtraMove = value; }

    /**
     * Checks if the player has a double build reward.
     * @return True if the player can build twice, false otherwise.
     */
    public boolean hasDoubleBuild() { return hasDoubleBuild; }

    /**
     * Sets whether the player has a double build reward.
     * @param value True to give double build, false otherwise.
     */
    public void setHasDoubleBuild(boolean value) { hasDoubleBuild = value; }

    /**
     * Checks if the player is blocked for this turn.
     * @return True if the player is blocked, false otherwise.
     */
    public boolean isBlocked() { return isBlocked; }

    /**
     * Sets whether the player is blocked for this turn.
     * @param value True to block the player, false otherwise.
     */
    public void setBlocked(boolean value) { isBlocked = value; }

    /**
     * Checks if the player should lose their next turn.
     * @return True if the player should lose a turn, false otherwise.
     */
    public boolean shouldLoseTurn() { return shouldLoseTurn; }

    /**
     * Sets whether the player should lose their next turn.
     * @param value True if the player should lose their next turn, false otherwise.
     */
    public void setShouldLoseTurn(boolean value) { shouldLoseTurn = value; }

    /**
     * Checks if the player’s worker can jump up two levels.
     * @return True if the player can jump two levels, false otherwise.
     */
    public boolean canJumpTwoLevels() { return canJumpTwoLevels; }

    /**
     * Sets whether the player’s worker can jump up two levels.
     * @param value True to allow jump two levels, false otherwise.
     */
    public void setCanJumpTwoLevels(boolean value) { canJumpTwoLevels = value; }



    /**
     * Constructs a new Player with a given name.
     * Workers and God Card will be assigned after creation.
     */
    public Player(Color color) {
        this.name = null;
        this.workers = new ArrayList<>(); // Initialize the list of workers
        this.godCard = null; // GodCard is initially null
        this.color = color;
    }

    /**
     * Gets the name of the player.
     * @return The player's name.
     */
    public String getGodCardName() {
        return this.godCard.getName();
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Color getColor(){
        return color;
    }

    /**
     * Gets the God Card assigned to this player.
     * @return The GodCard object, or null if no God Card is assigned.
     */
    public GodCard getGodCard() {
        return godCard;
    }

    /**
     * Assigns a God Card to this player.
     * @param godCard The GodCard object to assign.
     */
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
        godCard.setPlayer(this);
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    // Create a new Worker and assign it to a Cell
    public void addWorker(Worker worker) {
        if (workers.size() >= 2) {
            throw new IllegalStateException("A player can only have 2 workers.");
        }
        workers.add(worker);
    }

    public boolean contains(Worker worker) {
        return workers.contains(worker);
    }
}

