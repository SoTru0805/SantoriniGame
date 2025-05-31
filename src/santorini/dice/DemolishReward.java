package santorini.dice;

import santorini.engine.Player;
import santorini.engine.GameLogicManager;

/**
 * Dice reward that allows a player to demolish (remove) a building block from any cell
 * (except domes and already empty cells) at the start of their next turn.
 */
public class DemolishReward implements DiceReward {
    /**
     * Grants the player the ability to demolish a building at the start of their next turn.
     * @param player The player receiving the reward.
     * @param logicManager The game logic manager instance (not used directly in this reward).
     */
    @Override
    public void apply(Player player, GameLogicManager logicManager) {
        player.setCanDemolish(true);
    }

    /**
     * Provides a description of the reward for display to the player.
     * @return A description of the demolish reward.
     */
    @Override
    public String getDescription() {
        return "Demolish! At the start of your next turn, you may remove a single building block (not a dome) from anywhere on the board.";
    }
}
