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
    public void onTurnStart() {
        // Setup for Demeter at the beginning of the turn
        super.onTurnStart();
        isFirstBuild = true;
    }

    @Override
    public List<Cell> modifyMoveOptions(Worker worker, List<Cell> defaultOptions) {
        return defaultOptions; // Demeter does not affect movement options
    }

    @Override
    public List<Cell> modifyBuildOptions(Worker worker, List<Cell> defaultOptions) {
        if (isFirstBuild) {
            return defaultOptions; // For the first build, use the default options
        } else {
            // For the second build, allow building on the same space
            List<Cell> modified = new ArrayList<>(defaultOptions);
            return modified;
        }
    }

    @Override
    public void onTurnEnd() {
        // Clean up after the turn ends
        super.onTurnEnd();
        isFirstBuild = false; // Demeter allows the second build only after the first turn
    }
}