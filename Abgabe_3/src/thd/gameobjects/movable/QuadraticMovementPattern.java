package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class QuadraticMovementPattern extends MovementPattern {

    private final Position[] pattern;

    QuadraticMovementPattern() {
        super();
        pattern = new Position[]{
                new Position(100, 100),
                new Position(1100, 100),
                new Position(1100, 600),
                new Position(100, 600)};
    }
    QuadraticMovementPattern(Position start, int width, int height, String startEdge, boolean clockwise){
        super();
        pattern = new Position[4];
        String edge = startEdge;
        currentIndex = 0;
        pattern[currentIndex] = new Position(start);
        currentIndex += 1;
        while (pattern[pattern.length - 1] == null){
            switch (edge){
                case "top-left":
                    if (clockwise) {
                        pattern[currentIndex] = new Position(start.getX() + width, start.getY());
                        start.updateCoordinates(start.getX() + width, start.getY());
                        edge = "top-right";
                    } else {
                        pattern[currentIndex] = new Position(start.getX(), start.getY() + height);
                        start.updateCoordinates(start.getX(), start.getY() + height);
                        edge = "bottom-left";
                    }
                    currentIndex += 1;
                    break;
                case "top-right":
                    if (clockwise) {
                        pattern[currentIndex] = new Position(start.getX(), start.getY() + height);
                        start.updateCoordinates(start.getX(), start.getY() + height);
                        edge = "bottom-right";
                    } else {
                        pattern[currentIndex] = new Position(start.getX() - width, start.getY());
                        start.updateCoordinates(start.getX() - width, start.getY());
                        edge = "top-left";
                    }
                    currentIndex += 1;
                    break;
                case "bottom-left":
                    if (clockwise) {
                        pattern[currentIndex] = new Position(start.getX(), start.getY() - height);
                        start.updateCoordinates(start.getX(), start.getY() - height);
                        edge = "top-left";
                    } else {
                        pattern[currentIndex] = new Position(start.getX() + width, start.getY());
                        start.updateCoordinates(start.getX() + width, start.getY());
                        edge = "bottom-right";
                    }
                    currentIndex += 1;
                    break;
                case "bottom-right":
                    if (clockwise) {
                        pattern[currentIndex] = new Position(start.getX() - width, start.getY());
                        start.updateCoordinates(start.getX() - width, start.getY());
                        edge = "bottom-left";
                    } else {
                        pattern[currentIndex] = new Position(start.getX(), start.getY() - height);
                        start.updateCoordinates(start.getX(), start.getY() - height);
                        edge = "top-right";
                    }
                    currentIndex += 1;
                    break;
            }
        }
        currentIndex = -1;
    }

    QuadraticMovementPattern(Position start, int width, int height, String startEdge){
        this(start, width, height, startEdge, true);
    }
    QuadraticMovementPattern(Position start, int width, int height){
        this(start, width, height, "top-left", true);
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
