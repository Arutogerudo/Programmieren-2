package thd.gameobjects.movable;

import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

public class EnemyMovementPatterns extends MovementPattern {
    LinearMovementPattern linearMovementPattern;
    QuadraticMovementPatternUse quadraticMovementPatternUse;
    TriangularMovementPattern triangularMovementPattern;

    EnemyMovementPatterns(String pattern, int pixelToGo, String direction, Position start, int width, int height, String startEdge, boolean clockwise){
        if (pattern.equals("linear")) {
            linearMovementPattern = new LinearMovementPattern(start, pixelToGo, direction);
        } else if (pattern.equals("quadratic")) {
            quadraticMovementPatternUse = new QuadraticMovementPatternUse(start, width, height, startEdge, clockwise);
        } else if (pattern.equals("triangular")) {
            triangularMovementPattern = new TriangularMovementPattern(start, width, height);
        }
    }

    @Override
    protected void calculatePositions() {
        if (triangularMovementPattern != null) {
            triangularMovementPattern.calculatePositions();
        }
    }
    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        if (triangularMovementPattern != null) {
            return triangularMovementPattern.nextTargetPosition(referencePositions);
        } else if (quadraticMovementPatternUse != null) {
            return quadraticMovementPatternUse.nextTargetPosition(referencePositions);
        } else if (linearMovementPattern != null) {
            return linearMovementPattern.nextTargetPosition(referencePositions);
        }
        return null;
    }

    @Override
    protected Position startPosition(Position... referencePositions) {
        if (triangularMovementPattern != null) {
            return triangularMovementPattern.startPosition(referencePositions);
        } else if (quadraticMovementPatternUse != null) {
            return quadraticMovementPatternUse.startPosition(referencePositions);
        }
        return null;
    }
    @Override
    protected void worldShift(double pixel){
        if (triangularMovementPattern != null) {
            triangularMovementPattern.worldShift(pixel);
        } else if (quadraticMovementPatternUse != null) {
            quadraticMovementPatternUse.worldShift(pixel);
        } else if (linearMovementPattern != null) {
            linearMovementPattern.worldShift(pixel);
        }
    }
}
