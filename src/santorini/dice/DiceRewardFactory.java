package santorini.dice;

public class DiceRewardFactory {
    public static DiceReward getReward(int diceRoll) {
        switch (diceRoll) {
            case 1: return new ExtraMoveReward();
            case 2: return new DoubleBuildReward();
            case 3: return new BlockOpponentReward();
            case 4: return new LoseTurnReward();
            case 5: return new DemolishReward();
            case 6: return new JumpTwoLevelsReward();
            default: return null;
        }
    }
}
