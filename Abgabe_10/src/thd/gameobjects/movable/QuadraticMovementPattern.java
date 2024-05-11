package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class QuadraticMovementPattern extends MovementPattern {
    QuadraticMovementPattern(int pixelToGoWidth, int pixelToGoHeight, String direction) {
        super(pixelToGoWidth, pixelToGoHeight, direction);
    }

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        switch (direction) {
            case "up":
                currentIndex = 0;
                direction = "right";
                break;
            case "down":
                currentIndex = 1;
                direction = "left";
                break;
            case "left":
                currentIndex = 2;
                direction = "up";
                break;
            case "right":
                currentIndex = 3;
                direction = "down";
                break;
        }
        return new Position(referencePositions[0].getX() + pattern[currentIndex].getX(), referencePositions[0].getY() + pattern[currentIndex].getY());
    }
}
