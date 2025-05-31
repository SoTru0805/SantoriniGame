package santorini.dice;

import santorini.engine.Player;
import santorini.engine.GameLogicManager;

public class BlockOpponentReward implements DiceReward {
    @Override
    public void apply(Player player, GameLogicManager logicManager) {
        // Block the opponent for the next turn (assuming 2 players)
        for (Player p : logicManager.playerList) {
            if (p != player) {
                p.setBlocked(true);
            }
        }
    }
    @Override
    public String getDescription() {
        return "Sneaky! Block your opponent’s move for one turn.";
    }
}
