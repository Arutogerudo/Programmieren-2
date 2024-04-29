package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class TriangularMovementPattern extends MovementPattern {

    TriangularMovementPattern(Position start, int width, int height, String startEdge, boolean clockwise){
        super();
        pattern = new Position[3];
        this.start = new Position(start);
        this.width = width;
        this.height = height;
        this.edge = startEdge;
        this.clockwise = clockwise;
        calculatePositions();
    }
    TriangularMovementPattern(Position start, int width, int height){
        this(start, width, height, "top-left", true);
    }
    @Override
    protected void calculatePositions() {
        Position temp = new Position(start);
        super.calculatePositions();
        currentIndex = 0;
        start.updateCoordinates(temp);
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
