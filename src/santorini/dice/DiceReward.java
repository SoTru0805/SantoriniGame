package santorini.dice;
import santorini.engine.Player;
import santorini.engine.GameLogicManager;

public interface DiceReward {
    // Apply the reward to the player (and optionally GameLogicManager for side effects)
    void apply(Player player, GameLogicManager logicManager);
    String getDescription();
}
