package santorini.godcards;

import santorini.board.Board;
import santorini.board.Cell;
import santorini.elements.Worker;
import santorini.engine.GameLogicManager;

public class ZeusGod extends GodCard {

    public ZeusGod() {
        super("Zeus", "Your Worker may build a block under itself.", "zeus.png");
    }

    @Override
    public boolean useEffect(GameLogicManager gameLogic) {
        return false;
    }

    public boolean canBuild(Board board, Worker worker, Cell targetCell) {
        Cell currentCell = worker.getCurrentLocation();
        boolean isCurrentCell = currentCell.getRow() == targetCell.getRow() &&
                currentCell.getCol() == targetCell.getCol();

        if (isCurrentCell) {
            // Can build under but not dome and not above level 2
            return !targetCell.hasDome() && targetCell.getLevel() < 3;
        }

        // can't build on domes, can't build where there's another worker
        return !targetCell.hasDome() && !targetCell.isOccupied();
    }
}
