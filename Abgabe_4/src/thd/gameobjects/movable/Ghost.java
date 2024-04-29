package thd.gameobjects.movable;

import thd.gameobjects.base.GameObject;
import thd.game.utilities.GameView;

/**
 * movable Gameobject Ghost (enemy).
 */
public class Ghost extends GameObject {
    private final QuadraticMovementPatternUse quadraticMovementPattern;

    /**
     * Creates a ghost in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Ghost(GameView gameView) {
        super(gameView);
        size = 30;
        position.updateCoordinates(640, 100);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        quadraticMovementPattern = new QuadraticMovementPatternUse(position, 450, 200);
        targetPosition.updateCoordinates(quadraticMovementPattern.nextTargetPosition());
    }

    @Override
    public String toString() {
        return "Ghost: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(quadraticMovementPattern.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("ghostsmall.png", position.getX(), position.getY(), 0.1, rotation);
    }
}
