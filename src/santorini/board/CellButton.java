package santorini.board;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {
    private final Cell cell;

    public CellButton(Cell cell) {
        this.cell = cell;
        setPreferredSize(new Dimension(80, 80));
        setFont(new Font("Arial", Font.BOLD, 24));
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        updateDisplay();
    }

    public void updateDisplay() {
        String display = "";

        // To tell workers the cells are occupied
        if (cell.isOccupied()) {
            display += "W";
            setForeground(Color.BLUE); //this is for later move if the players swap
        }

        if (cell.hasDome()) {
            display += " D";
        } else if (cell.getLevel() > 0) {
            display += " L" + cell.getLevel();
        }

        setText(display.trim());
    }

    public Cell getCell() {
        return cell;
    }
}
