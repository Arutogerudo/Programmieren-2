package thd.gameobjects.base;

import java.util.Random;

/**
 * Represents a movement pattern in the game.
 */
public class MovementPattern {
    protected int currentIndex;
    protected final Random random;
    protected Position[] pattern;
    protected Position start;
    protected int width;
    protected int height;
    protected String edge;
    protected boolean clockwise;

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

    private void edgeTopLeft() {
        if (clockwise) {
            pattern[currentIndex] = new Position(start.getX() + width, start.getY());
            start.updateCoordinates(start.getX() + width, start.getY());
            edge = "top-right";
        } else {
            pattern[currentIndex] = new Position(start.getX(), start.getY() + height);
            start.updateCoordinates(start.getX(), start.getY() + height);
            edge = "bottom-left";
        }
    }

    private void edgeTopRight() {
        if (clockwise) {
            pattern[currentIndex] = new Position(start.getX(), start.getY() + height);
            start.updateCoordinates(start.getX(), start.getY() + height);
            edge = "bottom-right";
        } else {
            pattern[currentIndex] = new Position(start.getX() - width, start.getY());
            start.updateCoordinates(start.getX() - width, start.getY());
            edge = "top-left";
        }
    }

    private void edgeBottomLeft() {
        if (clockwise) {
            pattern[currentIndex] = new Position(start.getX(), start.getY() - height);
            start.updateCoordinates(start.getX(), start.getY() - height);
            edge = "top-left";
        } else {
            pattern[currentIndex] = new Position(start.getX() + width, start.getY());
            start.updateCoordinates(start.getX() + width, start.getY());
            edge = "bottom-right";
        }
    }

    private void edgeBottomRight() {
        if (clockwise) {
            pattern[currentIndex] = new Position(start.getX() - width, start.getY());
            start.updateCoordinates(start.getX() - width, start.getY());
            edge = "bottom-left";
        } else {
            pattern[currentIndex] = new Position(start.getX(), start.getY() - height);
            start.updateCoordinates(start.getX(), start.getY() - height);
            edge = "top-right";
        }
    }

    protected void calculatePositions() {
        currentIndex = 0;
        pattern[currentIndex] = new Position(start);
        currentIndex += 1;
        while (pattern[pattern.length - 1] == null) {
            switch (edge) {
                case "top-left":
                    edgeTopLeft();
                    break;
                case "top-right":
                    edgeTopRight();
                    break;
                case "bottom-left":
                    edgeBottomLeft();
                    break;
                case "bottom-right":
                    edgeBottomRight();
                    break;
            }
            currentIndex += 1;
        }
        currentIndex = -1;
    }
}
