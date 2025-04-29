package santorini.board;

import javax.swing.*;
import java.awt.*;
import santorini.engine.Game;
import santorini.engine.Player;
import santorini.screens.GameScreen; // To log messages into the Game Log

public class BoardGUI {
    private JPanel boardPanel;
    private CellButton[][] buttons;
    private boolean selectingWorker = true;
    private int selectedRow = -1;
    private int selectedCol = -1;

    public BoardGUI(Board board) {
        boardPanel = new JPanel(new GridLayout(board.getRows(), board.getCols()));
        buttons = new CellButton[board.getRows()][board.getCols()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                CellButton button = new CellButton(i, j, board.getCell(i, j));
                buttons[i][j] = button;
                boardPanel.add(button);

                button.addActionListener(e -> {
                    CellButton clicked = (CellButton) e.getSource();
                    int row = clicked.getRow();
                    int col = clicked.getCol();
                    Cell cell = clicked.getCell();

                    Player activePlayer = Game.getCurrentPlayer();

                    if (selectingWorker) {
                        // Select the worker
                        if (cell.getWorker() != null && cell.getWorker().equals(activePlayer)) {
                            selectedRow = row;
                            selectedCol = col;
                            selectingWorker = false;
                            GameScreen.logMessage(activePlayer.getName() + " selected worker at (" + row + ", " + col + ")");
                        } else {
                            GameScreen.logMessage("Invalid selection. Select your own worker.");
                        }
                    } else {
                        // Move to new cell
                        Cell selectedCell = Game.getBoard().getCell(selectedRow, selectedCol);
                        int levelDiff = cell.getLevel() - selectedCell.getLevel();
                        boolean isAdjacent = Math.abs(row - selectedRow) <= 1 && Math.abs(col - selectedCol) <= 1;
                        boolean isDifferentCell = row != selectedRow || col != selectedCol;

                        if (isAdjacent && isDifferentCell && levelDiff <= 1 && cell.getWorker() == null && !cell.hasDome()) {
                            // Valid move
                            cell.setWorker(activePlayer);
                            selectedCell.removeWorker();
                            buttons[row][col].updateDisplay();
                            buttons[selectedRow][selectedCol].updateDisplay();

                            GameScreen.logMessage(activePlayer.getName() + " moved to (" + row + ", " + col + ")");

                            selectingWorker = true;
                            selectedRow = -1;
                            selectedCol = -1;

                            Game.endTurn();
                            GameScreen.logMessage("Now " + Game.getCurrentPlayer().getName() + "'s turn (Turn " + Game.getTurnCount() + ")");
                        } else {
                            GameScreen.logMessage("Invalid move! Must be adjacent, unoccupied, no dome, and at most 1 level up.");
                        }
                    }
                });
            }
        }
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }
}
