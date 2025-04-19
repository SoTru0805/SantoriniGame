package santorini.elements;
import santorini.engine.Player;
import santorini.board.Cell;

import java.util.List;

//Abstract class GodCard
public abstract class GodCard {
    private String name;
    private String description;
    private Player player;

    public GodCard(String name, String description, Player player)
    {
        this.name = name;
        this.description = description;
        this.player = player;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Player getPlayer() {
        return player;
    }

    //Develop for Sprint3
    /*public void applyEffect()
     {
     }*/
    public void onTurnStart()
    {

    }

    public void onTurnEnd()
    {

    }
    /**
     * Modify the available movement options for a worker.
     * @param worker The worker trying to move.
     * @param defaultOptions The default movement options.
     * @return The modified list of movement options.
     */
    public List<Cell> modifyMoveOptions(Worker worker, List<Cell> defaultOptions) {
        return defaultOptions;
    }
    /**
     * Modify the available movement options for a worker.
     * @param worker The worker trying to move.
     * @param defaultOptions The default building options.
     * @return The modified list of available build options.
     */
    public List<Cell> modifyBuildOptions(Worker worker, List<Cell> defaultOptions) {
        return defaultOptions;
    }
}
