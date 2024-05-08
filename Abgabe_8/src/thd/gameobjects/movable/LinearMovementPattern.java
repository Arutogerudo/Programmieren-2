package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class LinearMovementPattern extends MovementPattern {
    LinearMovementPattern(int pixelToGoWidth, int pixelToGoHeight, String direction) {
        super(pixelToGoWidth, pixelToGoHeight, direction);
    }

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        switch (direction) {
            case "up":
                currentIndex = 0;
                direction = "down";
                break;
            case "down":
                currentIndex = 1;
                direction = "up";
                break;
            case "left":
                currentIndex = 2;
                direction = "right";
                break;
            case "right":
                currentIndex = 3;
                direction = "left";
                break;
        }
        return new Position(referencePositions[0].getX() + pattern[currentIndex].getX(), referencePositions[0].getY() + pattern[currentIndex].getY());
    }
}
