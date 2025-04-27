package santorini.board;

import santorini.engine.Player;

public class Cell {
    private int level; // building level: 0 to 3
    private boolean hasDome; // true if dome on top
    private Player worker; // <-- Change from String to Player

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

    public Player getWorker() {
        return worker;
    }

    public void setWorker(Player worker) {
        this.worker = worker;
    }

    public void removeWorker() {
        this.worker = null;
    }
}
