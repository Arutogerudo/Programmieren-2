package thd.gameobjects.base;

import java.util.Random;

/**
 * Represents a movement pattern in the game.
 */
public class MovementPattern {
    protected int currentIndex;
    protected final Random random;

    /**
     * Creates a new movement pattern in the game.
     */
    protected MovementPattern(){
        random = new Random();
        currentIndex = -1;
    }

    /**
     * Generates a new startposition of an object.
     *
     * @param referencePositions any positions that can be used for orientation.
     *
     * @return returns the new startposition of an object.
     */
    protected Position startPosition(Position... referencePositions) {
        return new Position();
    }

    /**
     * Generates the next targetposition.
     *
     * @param referencePositions any positions that can be used for orientation.
     *
     * @return returns the next targetposition for an object.
     */
    protected Position nextTargetPosition(Position... referencePositions) {
        return new Position();
    }
}
