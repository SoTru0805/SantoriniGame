package santorini.engine;

public class TurnManager {
  private final Player player1, player2, startingPlayer;
  private Player currentPlayer;
  private int turn = 1;

  public TurnManager(Player p1, Player p2, Player starting) {
    this.player1 = p1;
    this.player2 = p2;
    this.startingPlayer = starting;
    this.currentPlayer = starting;
  }

  public void nextTurn() {
    currentPlayer = (currentPlayer == player1) ? player2 : player1;
    if (currentPlayer == startingPlayer) turn++;
  }

  public Player getCurrentPlayer() { return currentPlayer; }
  public int getTurn() { return turn; }
  public Player getStartingPlayer() { return startingPlayer; }
}

