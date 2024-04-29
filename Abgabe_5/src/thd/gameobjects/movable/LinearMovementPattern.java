package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class LinearMovementPattern extends MovementPattern {
    private final Position[] pattern;


    LinearMovementPattern() {
        super();
        pattern = new Position[]{
                new Position(100, 100),
                new Position(1100, 100),
        };
    }

    LinearMovementPattern(double xCoordinate, double yCoordinate, int pixelToGo, String direction) {
        super();
        pattern = new Position[2];
        pattern[0] = new Position(xCoordinate, yCoordinate);
        pattern[1] = targetPosition(pixelToGo, direction);
    }

    private Position targetPosition(int pixelToGo, String direction) {
        switch (direction) {
            case "up":
                return new Position(pattern[0].getX(), pattern[0].getY() - pixelToGo);
            case "down":
                return new Position(pattern[0].getX(), pattern[0].getY() + pixelToGo);
            case "left":
                return new Position(pattern[0].getX() - pixelToGo, pattern[0].getY());
            case "right":
                return new Position(pattern[0].getX() + pixelToGo, pattern[0].getY());
        }
        throw new IllegalArgumentException();
    }

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        currentIndex++;
        if (currentIndex >= pattern.length) {
            currentIndex = 0;
        }
        return pattern[currentIndex];
    }
}
