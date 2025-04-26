package santorini.elements;

/**
 * Represents a building on a cell in the Santorini game.
 * A building has a level and may have a dome.
 */
public class Building {

    private int level; // Current level of the building
    private int previousLevel; // Level before the last build
    private boolean hasDome; // True if the building has a dome

    /**
     * Constructs a new Building with initial level 0 and no dome.
     */
    public Building() {
        this.level = 0; // Initial level is ground level
        this.previousLevel = 0; // No previous level initially
        this.hasDome = false; // No dome initially
    }

    /**
     * Gets the current level of the building.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the current level of the building.
     *
     * @param level The level to set.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the previous level of the building.
     *
     * @return The previous level.
     */
    public int getPreviousLevel() {
        return previousLevel;
    }

    /**
     * Sets the previous level of the building.
     *
     * @param previousLevel The previous level to set.
     */
    public void setPreviousLevel(int previousLevel) {
        this.previousLevel = previousLevel;
    }

    /**
     * Checks if the building has a dome.
     *
     * @return True if the building has a dome, false otherwise.
     */
    public boolean hasDome() {
        return hasDome;
    }

    /**
     * Sets whether the building has a dome.
     *
     * @param hasDome True to set a dome, false to remove it.
     */
    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    /**
     * Adds a level to the building.
     * If the level is 3, a dome is placed.
     */
    public void addLevel() {
        if (level < 3) {
            previousLevel = level;
            level++;
        } else if (level == 3) {
            previousLevel = level;
            level++;
            hasDome = true; // Dome is placed on level 3
        }
    }

    /**
     * Undoes the last build action, reverting to the previous level.
     */
    public void undoBuild() {
        level = previousLevel; // Revert to previous level
        hasDome = (level == 3); // Dome only if level is 3
    }
}
