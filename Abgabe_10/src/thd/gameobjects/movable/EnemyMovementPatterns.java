package thd.gameobjects.movable;

import thd.gameobjects.base.Position;

class EnemyMovementPatterns {
    private LinearMovementPattern linearMovementPattern;
    private QuadraticMovementPattern quadraticMovementPatternUse;
    private TriangularMovementPattern triangularMovementPattern;

    EnemyMovementPatterns(String pattern, int width, int height, String direction) {
        switch (pattern) {
            case "linear":
                linearMovementPattern = new LinearMovementPattern(width, height, direction);
                break;
            case "quadratic":
                quadraticMovementPatternUse = new QuadraticMovementPattern(width, height, direction);
                break;
            case "triangular":
                triangularMovementPattern = new TriangularMovementPattern(width, height, direction);
                break;
        }
    }

    Position nextTargetPosition(Position... referencePositions) {
        if (triangularMovementPattern != null) {
            return triangularMovementPattern.nextTargetPosition(referencePositions);
        } else if (quadraticMovementPatternUse != null) {
            return quadraticMovementPatternUse.nextTargetPosition(referencePositions);
        } else if (linearMovementPattern != null) {
            return linearMovementPattern.nextTargetPosition(referencePositions);
        }
        return null;
    }
}
