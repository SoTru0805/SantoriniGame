package santorini.board;

import java.awt.Color;
import javax.swing.*;
import santorini.elements.Worker;
import santorini.engine.Game;
import santorini.engine.Player;

public class CellButton extends JButton {

    private Cell cell;

    public CellButton(Cell cell) {
        this.cell = cell;
        setContentAreaFilled(false);
        setOpaque(true);
        setUpDisplay();
    }

    public Cell getCell() {
        return cell;
    }

    public void setUpDisplay() {
        if (cell.getWorker() != null) {
            Player owner = cell.getWorker().getPlayer();
            if (owner.getColor() == Color.RED) {
                setBackground(Color.RED);
            } else if (owner.getColor() == Color.BLUE) {
                setBackground(Color.BLUE);
            }
        } else {
            if (cell.getBuilding() != null) {
                setText(cell.getDisplaySymbol());
            } else {
                setText("");
            }
            setBackground(Color.LIGHT_GRAY);
        }
    }

    public void moveDisplay(Player player) {
        if (cell.isOccupied()) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(player.getColor());
        }
    }

    public void buildDisplay(Player player) {
        if (!cell.isOccupied()) {
            setText(cell.getDisplaySymbol());
        }
    }

    public int getRow() {
        return cell.getRow();
    }

    public int getCol() {
        return cell.getCol();
    }
}
