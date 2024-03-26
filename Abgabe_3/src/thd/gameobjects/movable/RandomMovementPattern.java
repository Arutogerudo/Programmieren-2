package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

class RandomMovementPattern extends MovementPattern {


    RandomMovementPattern() {
        super();
    }

    @Override
    protected Position startPosition(Position... referencePositions) {
        return new Position(GameView.WIDTH / 2d, GameView.HEIGHT / 2d);
    }

    @Override
    protected Position nextTargetPosition(Position... referencePositions) {
        return new Position(random.nextInt(GameView.WIDTH), random.nextInt(GameView.HEIGHT));
    }
}
