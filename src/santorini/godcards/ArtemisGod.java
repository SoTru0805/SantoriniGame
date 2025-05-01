package santorini.godcards;

import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.GameLogicManager;

import java.util.List;
import java.util.ArrayList;

public class ArtemisGod extends GodCard {
    private Cell firstMoveCell;
    private boolean isSecondMove = false;

    public ArtemisGod() {
        super("Artemis", "Your Worker may move one additional time, but not back to the initial space.", "images/GodCards_Avatar/Artemis.jpg");
    }

//    @Override
//    public boolean applyEffect(Worker worker, GameLogicManager logicManager) {
//        // Allow a second move if player has moved once
//        if (!isSecondMove || firstMoveCell == null) return false;
//        logicManager.allowExtraMove(worker, firstMoveCell); // custom method in logic
//        return true;
//    }

    @Override
    public void onTurnStart() {
        // Setup for Artemis at the beginning of the turn
        super.onTurnStart();
        firstMoveCell = null;
        isSecondMove = false;
    }

    public void setFirstMoveCell(Cell cell)
    {
        this.firstMoveCell = cell;
        this.isSecondMove = true;
    }

    @Override
    public List<Cell> modifyMoveOptions(Worker worker, List<Cell> defaultOptions) {
        if (isSecondMove && firstMoveCell != null) {
            List<Cell> modified = new ArrayList<>(defaultOptions);
            modified.removeIf(cell -> cell.equals(firstMoveCell)); // Prevent moving back to the initial space
            return modified;
        }
        return defaultOptions; // If it's the first move, use default options}    }
    }
    @Override
    public List<Cell> modifyBuildOptions(Worker worker, List<Cell> defaultOptions) {
        return defaultOptions; // Artemis does not affect building options
    }

    @Override
    public void onTurnEnd() {
        // Clean up after the turn ends
        super.onTurnEnd();
        firstMoveCell = null;
        isSecondMove = false;
    }

}
