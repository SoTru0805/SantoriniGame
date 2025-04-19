package santorini.board;


public class Cell {
    private final int x;
    private final int y;
    private boolean hasWorker;
    private int buildingLevel;
    private boolean hasDome;

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

    public int getLevel() {
        return buildingLevel;
    }

    public void setLevel(int level){
        this.buildingLevel = level;
    }

    public boolean hasDome() {
        return hasDome;
    }

    public void setDome(boolean hasDome) {
        this.hasDome = hasDome;
    }
}
