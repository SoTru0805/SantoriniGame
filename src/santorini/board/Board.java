package santorini.board;

public class Board {
    private final int ROWS = 5;
    private final int COLUMNS = 5;
    private Cell[][] cells;

    public Board() {
        cells = new Cell[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLUMNS;
    }

}
