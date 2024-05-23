package thd.gameobjects.base;

/**
 * Represents a movement pattern in the game.
 */
public class MovementPattern {
    protected int currentIndex;
    protected String direction;
    protected Position[] pattern;
    protected int width;
    protected int height;

    /**
     * Creates a new movement pattern in the game.
     *
     * @param pixelToGoWidth  pixels to go horizontal
     * @param pixelToGoHeight pixels to go vertical
     * @param direction       direction of first move
     */
    protected MovementPattern(int pixelToGoWidth, int pixelToGoHeight, String direction) {
        this.direction = direction;
        pattern = new Position[4];
        pattern[0] = new Position(0, -pixelToGoHeight);
        pattern[1] = new Position(0, pixelToGoHeight);
        pattern[2] = new Position(-pixelToGoWidth, 0);
        pattern[3] = new Position(pixelToGoWidth, 0);
    }

    /**
     * Generates the next targetposition.
     *
     * @param referencePositions any positions that can be used for orientation.
     * @return returns the next targetposition for an object.
     */
    protected Position nextTargetPosition(Position... referencePositions) {
        return new Position();
    }
}
