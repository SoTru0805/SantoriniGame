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

