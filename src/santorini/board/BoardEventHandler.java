package santorini.board;

import santorini.engine.GameLogicManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardEventHandler implements ActionListener {
  private GameLogicManager logicManager;

  public BoardEventHandler(GameLogicManager logicManager) {
    this.logicManager = logicManager;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof CellButton) {
      CellButton button = (CellButton) e.getSource();
      logicManager.handleCellClick(button.getCell());
    }
  }
}
