package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class TriangularMovementPattern extends MovementPattern {

    private final Position[] pattern;
    private boolean clockwise;

    TriangularMovementPattern() {
        super();
        pattern = new Position[]{
                new Position(100, 100),
                new Position(1100, 100),
                new Position(1100, 600)};
    }
    private void calculateNextPosition(boolean clockwise){

    }
    TriangularMovementPattern(Position start, int width, int height, String startEdge, boolean clockwise){
        super();
        pattern = new Position[3];
        Position temp = new Position(start);
        this.clockwise = clockwise;
        String edge = startEdge;
        currentIndex = 0;
        pattern[currentIndex] = new Position(start);
        currentIndex += 1;
        while (pattern[pattern.length - 1] == null){
            switch (edge){
                case "top-left":
                    if (clockwise) {
                        start.updateCoordinates(start.getX() + width, start.getY());
                        pattern[currentIndex] = new Position(start);
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
        currentIndex = 0;
        start.updateCoordinates(temp);
    }
    TriangularMovementPattern(Position start, int width, int height, String startEdge){
        this(start, width, height, startEdge, true);
    }
    TriangularMovementPattern(Position start, int width, int height){
        this(start, width, height, "top-left", true);
    }

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        if (clockwise) {
            currentIndex++;
        } else {
            currentIndex--;
        }
        if (currentIndex >= pattern.length -1 || currentIndex <= 0) {
            clockwise = !clockwise;
        }
        return pattern[currentIndex];
    }

    @Override
    protected Position startPosition(Position... referencePositions) {
        return nextTargetPosition();
    }
}
