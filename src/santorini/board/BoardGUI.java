package santorini.board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BoardGUI {
    private JPanel boardPanel;
    private CellButton[][] buttons;
    private ActionListener clickListener;
    private Board board;

    public BoardGUI(Board board) {
        boardPanel = new JPanel(new GridLayout(board.getRows(), board.getCols()));
        buttons = new CellButton[board.getRows()][board.getCols()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                CellButton button = new CellButton(board.getCell(i, j));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
    }

    public void setCellClickListener(ActionListener listener) {
        this.clickListener = listener;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].addActionListener(listener);
            }
        }
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public CellButton getButton(int row, int col) {
        return buttons[row][col];
    }

    public Board getBoard() {
        return board;
    }
}
