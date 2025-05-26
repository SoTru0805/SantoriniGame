package santorini.board;

import santorini.elements.Worker;
import santorini.engine.Game;
import santorini.engine.Player;

import javax.swing.*;
import java.awt.*;

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

    public void setUpDisplay(){
        setText(cell.getDisplaySymbol());

        if (cell.getWorker() != null) {
            Player owner = cell.getWorker().getPlayer();
            setFont(getFont().deriveFont(Font.BOLD));
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

    public int getRow() {
        return cell.getRow();
    }

    public int getCol() {
        return cell.getCol();
    }

}
