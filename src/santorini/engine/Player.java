package santorini.engine;
//import santorini.elements.GodCard;
//import santorini.elements.Worker;
import santorini.elements.Worker;
import santorini.godcards.GodCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Santorini game.
 * Each player has a name, owns a set of workers, and have a God Card.
 */
public class Player {

    private String name;
    private List<Worker> workers;
    private GodCard godCard; // Association with GodCard (0..1)

    /**
     * Constructs a new Player with a given name.
     * Workers and God Card will be assigned after creation.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
//        this.workers = new ArrayList<>(); // Initialize the list of workers
//        this.godCard = null; // GodCard is initially null
    }

    /**
     * Gets the name of the player.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of workers owned by this player.
     * @return A list of Worker objects.
     */
//    public List<Worker> getWorkers() {
//        return workers;
//    }

    /**
     * Adds a worker to the player's collection.
     * @param worker The Worker object to add.
     */
//    public void addWorker(Worker worker) {
//        if (this.workers.size() < 4) { // Assuming a max of 4 workers based on cardinality
//            this.workers.add(worker);
//        } else {
//            System.out.println("Player can't have more than 4 workers."); // Basic error handling
//        }
//    }

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
    }

    /**
     * Selects a worker for the current turn.
     * @param worker The worker to select.
     */
//    public void selectWorker(Worker worker) {
//        // TODO: Implement worker selection logic
//        System.out.println(name + " selected worker: " + worker);
//    }

    /**
     * Attempts to move the selected worker to a target cell.
     * @param targetCell The cell to move to.
     * @return true if the move is valid and successful, false otherwise.
     */
//    public boolean moveWorker(Cell targetCell) {
//        // TODO: Implement worker movement logic, including validation
//        System.out.println(name + " attempting to move to cell: " + targetCell);
//        return false; // Placeholder
//    }

    /**
     * Attempts to build a level on a target cell using the selected worker.
     * @param targetCell The cell to build on.
     * @return true if the build is valid and successful, false otherwise.
     */
//    public boolean build(Cell targetCell) {
//        // TODO: Implement building logic, including validation
//        System.out.println(name + " attempting to build on cell: " + targetCell);
//        return false; // Placeholder
//    }

    /**
     * Applies the move action. This might involve updating the worker's position
     * and checking for win conditions after the move.
     * @param worker The worker that moved.
     * @param cell The cell the worker moved to.
     */
//    public void applyMove(Worker worker, Cell cell) {
//        // TODO: Implement logic after a successful move
//        System.out.println(name + " applying move for worker " + worker + " to cell " + cell);
//    }

    /**
     * Applies the build action. This might involve updating the cell's level
     * and checking for win conditions after the build (if applicable, e.g., winning by building a dome).
     * @param cell The cell where a level was built.
     */
//    public void applyBuild(Cell cell) {
//        // TODO: Implement logic after a successful build
//        System.out.println(name + " applying build on cell " + cell);
//    }

    /**
     * Undoes the current turn's actions.
     */
    public void undoCurrentTurn() {
        // TODO: Implement undo logic
        System.out.println(name + " undoing current turn.");
    }

    /**
     * Confirms the current turn's actions, making them permanent.
     */
    public void confirmCurrentTurn() {
        // TODO: Implement turn confirmation logic
        System.out.println(name + " confirming current turn.");
    }

    // public List<Cell> getAvailableMoves(Worker worker) {}
    // public List<Cell> getAvailableBuilds(Worker worker) {}
    // public boolean canMove(Worker worker, Cell targetCell) {}
    // public boolean canBuild(Worker worker, Cell targetCell) {}

//    @Override
//    public String toString() {
//        return "Player{" +
//               "name='" + name + '\'' +
//               ", workers=" + workers.size() + // Just show count for simplicity
//               ", godCard=" + (godCard != null ? godCard.getName() : "None") +
//               '}';
//    }
}

