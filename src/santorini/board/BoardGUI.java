package santorini.board;

import javax.swing.*;
import java.awt.*;

public class BoardGUI {
    private JPanel boardPanel;
    private CellButton[][] buttons;
    private String currentPlayer = "P1";
    private int p1WorkersPlaced = 0;
    private int p2WorkersPlaced = 0;
    private boolean setupPhase = true;

    public BoardGUI(Board board) {
        boardPanel = new JPanel(new GridLayout(5, 5));
        buttons = new CellButton[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                CellButton button = new CellButton(i, j, board.getCell(i, j));
                buttons[i][j] = button;
                boardPanel.add(button);

                // Button click logic
                button.addActionListener(e -> {
                    CellButton clicked = (CellButton) e.getSource();
                    Cell cell = clicked.getCell();

                    if (cell.getWorker() != null) {
                        return; // Cannot place on occupied cell
                    }

                    if (setupPhase) {
                        // Place workers
                        cell.setWorker(currentPlayer);
                        clicked.updateDisplay();

                        if (currentPlayer.equals("P1")) {
                            p1WorkersPlaced++;
                            if (p1WorkersPlaced >= 2) {
                                currentPlayer = "P2";
                            }
                        } else if (currentPlayer.equals("P2")) {
                            p2WorkersPlaced++;
                            if (p2WorkersPlaced >= 2) {
                                setupPhase = false; // setup done
                                currentPlayer = "P1"; // start game with P1
                            }
                        }
                    } else {
                        // Later: move worker phase
                        System.out.println(currentPlayer + " would move a worker now!");
                    }
                });
            }
        }
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }
}
