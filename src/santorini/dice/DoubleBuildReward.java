package santorini.dice;

import santorini.engine.Player;
import santorini.engine.GameLogicManager;

public class DoubleBuildReward implements DiceReward {
    @Override
    public void apply(Player player, GameLogicManager logicManager) {
        player.setHasDoubleBuild(true);
    }
    @Override
    public String getDescription() {
        return "Double builder! You can build twice next time.";
    }
}
