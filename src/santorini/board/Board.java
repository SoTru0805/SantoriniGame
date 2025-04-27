package santorini.board;

public class Board {
    private Cell[][] cells;

    public Board() {
        cells = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public int getRows() {
        return 5;
    }

    public int getCols() {
        return 5;
    }

}
