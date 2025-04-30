package santorini.turn;

import santorini.engine.Player;

public class TurnManager {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int turnCount;

    public TurnManager(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = player1;  // Player 1 starts
        this.turnCount = 1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void endTurn() {
        // Alternate the current player
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        turnCount++;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
