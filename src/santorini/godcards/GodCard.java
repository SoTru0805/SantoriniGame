package santorini.godcards;
import santorini.elements.Worker;
import santorini.engine.GameLogicManager;
import santorini.engine.Player;
import santorini.board.Cell;

import java.util.List;

//Abstract class GodCard
public abstract class GodCard {
    private String name;
    private String description;
    private String imagePath;
    private Player player;
    private Cell firstMoveCell;

    public GodCard(String name, String description, String imagePath)
    {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setFirstMoveCell(Cell cell) {
        this.firstMoveCell = cell;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getImagePath() {
        return imagePath;
    }
    public Player getPlayer() {
        return player;
    }
    public abstract boolean useEffect(GameLogicManager logicManager);
}
