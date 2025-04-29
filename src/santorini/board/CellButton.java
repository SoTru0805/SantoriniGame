package santorini.board;

import santorini.elements.Worker;
import santorini.engine.Game;
import santorini.engine.Player;

import javax.swing.*;
import java.awt.Color;
public class CellButton extends JButton {
    private Cell cell;

    public CellButton(Cell cell) {
        this.cell = cell;
        setUpDisplay();
    }

    public Cell getCell() {
        return cell;
    }

    public void setUpDisplay(){
        if (cell.getWorker() != null) {
            Player owner = cell.getWorker().getPlayer();
            if (owner.getColor() == Color.RED) {
                setBackground(Color.RED);
            } else if (owner.getColor() == Color.BLUE) {
                setBackground(Color.BLUE);
            }
        } else {
            // Display building symbol like G, L1, etc.
            if (cell.getBuilding() != null) {
                setText(cell.getBuilding().getSymbol());
            } else {
                setText("");
            }
            setBackground(Color.LIGHT_GRAY);
        }
    }

    public void moveDisplay(Player player) {
        if (cell.isOccupied()){
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(player.getColor());
        }
    }

    public void buildDisplay(Player player){
        if (!cell.isOccupied()){
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
