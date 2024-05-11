package thd.game.level;

import java.awt.*;

/**
 * Class that defines properties of levels.
 */
public class Level {
    /**
     * Name of level.
     */
    public String name;
    /**
     * Number of level.
     */
    public int number;
    /**
     * String to define the world of level.
     */
    public String world;
    /**
     * Number of columns where first visible world starts.
     */
    public int worldOffsetColumns;
    /**
     * Number of lines where first visible world starts.
     */
    public int worldOffsetLines;
    /**
     * Background color of level.
     */
    public Color backgroundColor;
}
