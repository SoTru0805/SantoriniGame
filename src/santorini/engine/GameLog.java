package santorini.engine;

import javax.swing.*;

public class GameLog {
  private static JTextArea gameLog;

  public static void setGameLog(JTextArea log) {
    gameLog = log;
  }

  public static JTextArea setUpGameLog(Player startingPlayer) {
    gameLog = new JTextArea("Game’s Log:\n➢ Turn #1\n• " + startingPlayer.getName() + " starts the game!");
    gameLog.setEditable(false);
    return gameLog;
  }

  public static void logMessage(String message) {
    if (gameLog != null) {
      gameLog.append("\n• " + message);
      gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
  }

  public static void turnMessage(String message) {
    if (gameLog != null) {
      gameLog.append("\n➢ " + message);
      gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
  }

  public static JTextArea getGameLog() {
    return gameLog;
  }
}
