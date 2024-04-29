package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class LinearMovementPattern extends MovementPattern {
    private String direction;
    private Position position;
    private double speedInPixel;


    LinearMovementPattern() {
        super();
    }

    LinearMovementPattern(Position position, String direction, double speedInPixel) {
        this.position = position;
        this.direction = direction;
        this.speedInPixel = speedInPixel;
    }

    Position targetPosition() {
        switch (direction) {
            case "left":
                position.updateCoordinates(position.getX() - speedInPixel, position.getY());
                break;
            case "right":
                position.updateCoordinates(position.getX() + speedInPixel, position.getY());
                break;
            case "down":
                position.updateCoordinates(position.getX(), position.getY() + speedInPixel);
                break;
            case "up":
                position.updateCoordinates(position.getX(), position.getY() - speedInPixel);
                break;
        }
        return new Position(position.getX(), position.getY());
    }
}
