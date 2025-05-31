package santorini.dice;

import santorini.engine.Player;
import santorini.engine.GameLogicManager;

public class LoseTurnReward implements DiceReward {
    @Override
    public void apply(Player player, GameLogicManager logicManager) {
        player.setShouldLoseTurn(true);
    }
    @Override
    public String getDescription() {
        return "Oh no! You lose your next turn. Better luck next time!";
    }
}
