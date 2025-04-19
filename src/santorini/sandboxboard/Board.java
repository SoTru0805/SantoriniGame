package santorini.sandboxboard;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<List<Cell>> cells;

    public Board() {
        cells = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            List<Cell> row = new ArrayList<>();
            for (int y = 0; y < 5; y++) {
                row.add(new Cell(x, y));
            }
            cells.add(row);
        }
    }

    public Cell getCell(int x, int y) {
        if (isWithinBounds(x, y)) {
            return cells.get(x).get(y);
        }
        return null;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    public List<Cell> getAdjacentCells(Cell cell) {
        List<Cell> adjacent = new ArrayList<>();
        int cx = cell.getX();
        int cy = cell.getY();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = cx + dx;
                int ny = cy + dy;
                if (isWithinBounds(nx, ny)) {
                    adjacent.add(getCell(nx, ny));
                }
            }
        }
        return adjacent;
    }
}
