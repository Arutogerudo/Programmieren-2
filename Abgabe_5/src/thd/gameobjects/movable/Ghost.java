package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.game.utilities.GameView;

/**
 * movable Gameobject Ghost (enemy).
 */
public class Ghost extends GameObject {
    private final QuadraticMovementPatternUse quadraticMovementPattern;
    private static final int WIDTH_MOVE = 450;
    private static final int HEIGHT_MOVE = 200;

    /**
     * Creates a ghost in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Ghost(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(640, 100);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        quadraticMovementPattern = new QuadraticMovementPatternUse(position, WIDTH_MOVE, HEIGHT_MOVE);
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
        gameView.addImageToCanvas("ghostsmall.png", position.getX(), position.getY(), size, rotation);
    }
}
