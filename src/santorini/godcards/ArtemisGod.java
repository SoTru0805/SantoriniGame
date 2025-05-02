package santorini.godcards;

import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.GameLog;
import santorini.engine.GameLogicManager;

import java.util.List;
import java.util.ArrayList;

public class ArtemisGod extends GodCard {
    private Cell firstMoveCell;
    private boolean isSecondMove = false;

    public ArtemisGod() {
        super("Artemis", "Your Worker may move one additional time, but not back to the initial space.", "images/GodCards_Avatar/Artemis.jpg");
    }

    @Override
    public boolean useEffect(GameLogicManager logicManager) {
        return logicManager.activateExtraMove();
    }

}
