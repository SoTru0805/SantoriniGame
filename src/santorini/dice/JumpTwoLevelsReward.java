package santorini.dice;

import santorini.engine.Player;
import santorini.engine.GameLogicManager;

public class JumpTwoLevelsReward implements DiceReward {
    @Override
    public void apply(Player player, GameLogicManager logicManager) {
        player.setCanJumpTwoLevels(true);
    }
    @Override
    public String getDescription() {
        return "Super move! Jump up two levels on your next move.";
    }
}
