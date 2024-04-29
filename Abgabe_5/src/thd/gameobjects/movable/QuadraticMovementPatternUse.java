package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class QuadraticMovementPatternUse extends MovementPattern {

    private final Position[] pattern;
    private final Position start;
    private final int width;
    private final int height;
    private String edge;
    private final boolean clockwise;

    QuadraticMovementPatternUse(Position start, int width, int height, String startEdge, boolean clockwise) {
        super();
        pattern = new Position[4];
        this.start = start;
        this.width = width;
        this.height = height;
        edge = startEdge;
        this.clockwise = clockwise;
        calculatePositions();
    }

    QuadraticMovementPatternUse(Position start, int width, int height, String startEdge) {
        this(start, width, height, startEdge, true);
    }

    QuadraticMovementPatternUse(Position start, int width, int height) {
        this(start, width, height, "top-left", true);
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

    private void calculatePositions() {
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

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        currentIndex++;
        if (currentIndex >= pattern.length) {
            currentIndex = 0;
        }
        return pattern[currentIndex];
    }


    @Override
    protected Position startPosition(Position... referencePositions) {
        return nextTargetPosition();
    }
}
