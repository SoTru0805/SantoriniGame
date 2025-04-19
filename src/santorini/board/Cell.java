package santorini.board;

public class Cell {
    private final int x;
    private final int y;
    private boolean hasWorker;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasWorker = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOccupied() {
        return hasWorker;
    }

    public void setOccupied(boolean hasWorker) {
        this.hasWorker = hasWorker;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + (hasWorker ? " W" : "") + "]";
    }
}
