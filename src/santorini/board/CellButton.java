package santorini.board;

import javax.swing.*;
import java.awt.Color;
public class CellButton extends JButton {
    private int row;
    private int col;
    private Cell cell;

    public CellButton(int row, int col, Cell cell) {
        this.row = row;
        this.col = col;
        this.cell = cell;
        updateDisplay();
    }

    public Cell getCell() {
        return cell;
    }

    public void updateDisplay() {
        if (cell.getWorker() != null) {
            setText(cell.getWorker());
            setBackground(Color.ORANGE); // Worker highlighted
        } else if (cell.hasDome()) {
            setText("");
            setBackground(Color.DARK_GRAY); // Dome
        } else {
            setText("");
            switch (cell.getLevel()) {
                case 1 -> setBackground(Color.CYAN);
                case 2 -> setBackground(Color.GREEN);
                case 3 -> setBackground(Color.RED);
                default -> setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
