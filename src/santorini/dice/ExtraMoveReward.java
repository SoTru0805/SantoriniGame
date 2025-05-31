package santorini.dice;

import santorini.engine.Player;
import santorini.engine.GameLogicManager;

public class ExtraMoveReward implements DiceReward {
    @Override
    public void apply(Player player, GameLogicManager logicManager) {
        player.setHasExtraMove(true);
    }
    @Override
    public String getDescription() {
        return "Lucky you! Next turn, you get one extra move.";
    }
}
