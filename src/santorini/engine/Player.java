package santorini.engine;

import santorini.elements.Worker;
import santorini.board.Cell;
import santorini.elements.GodCard;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Worker> workers;
    private GodCard godCard;

    public Player(String name) {
        this.name = name;
        this.workers = new ArrayList<>();
        this.godCard = null;
    }

    public String getName() {
        return name;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void addWorker(Worker worker) {
        if (this.workers.size() < 4) { //  Santorini has 2 workers per player
            this.workers.add(worker);
        } else {
            System.out.println("Player can't have more than 4 workers.");
        }
    }

    // public GodCard getGodCard() { return godCard; }
    // public void setGodCard(GodCard godCard) { this.godCard = godCard; }

    /**
     * Selects a worker for the current turn.
     * @param worker The worker to select.
     */
    public void selectWorker(Worker worker) {
        // TODO: Implement worker selection logic (e.g., track the currently selected worker)
        System.out.println(name + " selected worker: " + worker);
    }

    /**
     * Attempts to move the specified worker to a target cell.
     * @param worker The worker to move.
     * @param targetCell The cell to move to.
     * @return true if the move is valid and successful, false otherwise.
     */
    public boolean moveWorker(Worker worker, Cell targetCell) {
        //  TODO: Implement worker movement logic, including validation
        //  1. Check if the worker belongs to this player
        //  2. Validate the move against game rules (adjacency, height, etc.)
        //  3. If valid, instruct the worker to move (worker.moveTo(targetCell))
        //  4. Update game state
        //  5. Check for win conditions
        System.out.println(name + " attempting to move worker " + worker + " to cell: " + targetCell);
        return false; // Placeholder
    }

    /**
     * Attempts to build a level on a target cell using the specified worker.
     * @param worker The worker to build with.
     * @param targetCell The cell to build on.
     * @return true if the build is valid and successful, false otherwise.
     */
    public boolean build(Worker worker, Cell targetCell) {
        //  TODO: Implement building logic, including validation
        //  1. Check if the worker belongs to this player
        //  2. Validate the build against game rules
        //  3. If valid, instruct the worker to build (worker.buildBlock(targetCell))
        //  4. Update game state
        //  5. Check for win conditions (e.g., winning by building a dome)
        System.out.println(name + " attempting to build with worker " + worker + " on cell: " + targetCell);
        return false; // Placeholder
    }

    // public void applyMove(Worker worker, Cell cell) { ... }  //  These might be internal helper methods
    // public void applyBuild(Cell cell) { ... }

    public void undoCurrentTurn() {
        // TODO: Implement undo logic
        System.out.println(name + " undoing current turn.");
    }

    public void confirmCurrentTurn() {
        // TODO: Implement turn confirmation logic
        System.out.println(name + " confirming current turn.");
    }

    /**
     * Gets a list of available cells the worker can move to.
     * @param worker The worker to check for available moves.
     * @return A list of valid cells to move to.
     */
    public List<Cell> getAvailableWorkerMoves(Worker worker) {
        // TODO: Implement logic to get available moves for a worker
        System.out.println(name + " getting available moves for worker " + worker);
        return new ArrayList<>(); // Placeholder
    }

    /**
     * Gets a list of available cells the worker can build on.
     * @param worker The worker to check for available builds.
     * @return A list of valid cells to build on.
     */
    public List<Cell> getAvailableWorkerBuilds(Worker worker) {
        // TODO: Implement logic to get available builds for a worker
        System.out.println(name + " getting available builds for worker " + worker);
        return new ArrayList<>(); // Placeholder
    }

    // public boolean canMove(Worker worker, Cell targetCell) {}  //  Consider these helper methods within moveWorker
    // public boolean canBuild(Worker worker, Cell targetCell) {}
}