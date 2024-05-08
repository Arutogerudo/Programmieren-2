package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class TriangularMovementPattern extends MovementPattern {
    private final String startDirection;

    TriangularMovementPattern(int pixelToGoWidth, int pixelToGoHeight, String direction) {
        super(pixelToGoWidth, pixelToGoHeight, direction);
        this.startDirection = direction;
    }

    private void upCase() {
        switch (startDirection) {
            case "up":
                direction = "right";
                break;
            case "down", "left":
                direction = "down";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    private void downCase() {
        switch (startDirection) {
            case "up", "right":
                direction = "up";
                break;
            case "down":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }

    private void leftCase() {
        switch (startDirection) {
            case "up":
                direction = "down";
                break;
            case "down", "right":
                direction = "right";
                break;
            case "left":
                direction = "up";
                break;
        }
    }

    private void rightCase() {
        switch (startDirection) {
            case "up", "left":
                direction = "left";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "down";
                break;
        }
    }

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        switch (direction) {
            case "up":
                currentIndex = 0;
                upCase();
                break;
            case "down":
                currentIndex = 1;
                downCase();
                break;
            case "left":
                currentIndex = 2;
                leftCase();
                break;
            case "right":
                currentIndex = 3;
                rightCase();
                break;
        }
        return new Position(referencePositions[0].getX() + pattern[currentIndex].getX(), referencePositions[0].getY() + pattern[currentIndex].getY());
    }
}
