package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class QuadraticMovementPatternUse extends MovementPattern {



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

    QuadraticMovementPatternUse(Position start, int width, int height) {
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
