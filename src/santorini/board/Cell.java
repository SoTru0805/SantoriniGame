package santorini.board;

public class Cell {
    private int level; // building level: 0 to 3
    private boolean hasDome; // true if dome on top
    private String worker; // null if no worker, or "P1", "P2"

    public Cell() {
        this.level = 0;
        this.hasDome = false;
        this.worker = null;
    }

    public int getLevel() {
        return level;
    }

    public void build() {
        if (level < 3) {
            level++;
        } else {
            hasDome = true;
        }
    }

    public boolean hasDome() {
        return hasDome;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public void removeWorker() {
        this.worker = null;
    }
}