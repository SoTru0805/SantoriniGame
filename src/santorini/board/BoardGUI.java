package santorini.board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardGUI extends JFrame {
    private final Board board;
    private final CellButton[][] buttons;

    public BoardGUI(Board board) {
        this.board = board;
        this.buttons = new CellButton[5][5];

        setTitle("Santorini Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //grid panel with pads and the background
        JPanel boardPanel = new JPanel(new GridLayout(5, 5, 2, 2));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardPanel.setBackground(Color.DARK_GRAY);

        //create and add CellButtons that players can click on
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Cell cell = board.getCell(x, y);
                CellButton button = new CellButton(cell);

                int finalX = x;
                int finalY = y;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Clicked: (" + finalX + "," + finalY + ")");
                        cell.setOccupied(!cell.isOccupied()); // Toggle
                        button.updateDisplay();
                    }
                });

                buttons[x][y] = button;
                boardPanel.add(button);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // center the window
        setVisible(true);
    }
}