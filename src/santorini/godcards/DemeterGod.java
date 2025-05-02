package santorini.godcards;

import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.GameLog;
import santorini.engine.GameLogicManager;

import java.util.List;
import java.util.ArrayList;

public class DemeterGod extends GodCard {
    private boolean isFirstBuild = true;

    public DemeterGod() {
        super("Demeter", "Your Worker may build one additional time, but not on the same space.", "images/GodCards_Avatar/Demeter.jpg");
    }

    @Override
    public boolean useEffect(GameLogicManager logicManager) {
        return logicManager.activateExtraBuild();
    }
}