package santorini.elements;

import java.awt.*;

public class Building {
    protected int level;
    protected String symbol;
    protected boolean hasDome;

    public Building() {
        this.level = 0;
        this.hasDome = false;
        this.symbol = null;
    }

    public int getLevel() {
        return level;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean hasDome() {
        return hasDome;
    }

    // Build one level higher
    public void build() {
        if (hasDome) {
            throw new IllegalStateException("Cannot build, dome already placed.");
        }

        level++;

        if (level == 4) { // 4th level is a dome
            hasDome = true;
        }
    }

    // Undo the last build action
    public void undoBuild() {
        if (level == 0) {
            throw new IllegalStateException("No building to undo.");
        }

        if (hasDome) {
            hasDome = false; // remove dome first
        } else {
            level--;
        }
    }

    @Override
    public String toString() {
        if (hasDome) return "Dome";
        return "Level " + level;
    }
}
