package santorini.board;

import javax.swing.*;
import java.awt.*;

public class BoardGUI {
    private JPanel boardPanel;
    private CellButton[][] buttons;
    private String currentPlayer = "P1";
    private int p1WorkersPlaced = 0;
    private int p2WorkersPlaced = 0;
    private boolean setupPhase = false;

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
                    int row = clicked.getRow();
                    int col = clicked.getCol();

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
                                setupPhase = false;
                                currentPlayer = "P1";
                                System.out.println("Setup done! Game starts!");
                            }
                        }
                    } else {
                        // Not setup phase anymore → playing the game
                        santorini.engine.Game.playerClicked(row, col);
                    }
                });

            }
        }
    }


    public JPanel getBoardPanel() {
        return boardPanel;
    }
}
