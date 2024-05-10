package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.Position;

/**
 * movable Gameobject Ghost (enemy).
 */
public class Ghost extends Enemy {

    /**
     * Creates a ghost in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param start           given start Position
     * @param pixelToGoWidth  pixels to go horizontal
     * @param pixelToGoHeight pixels to go vertical
     * @param direction       direction of first move
     * @param pattern         given movement pattern of object
     */
    public Ghost(GameView gameView, GamePlayManager gamePlayManager, Position start, int pixelToGoWidth, int pixelToGoHeight, String direction, String pattern) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(new Position(start));
        size = 0.1;
        speedInPixel = 3;
        rotation = 0;
        width = 75;
        height = 43;
        hitBoxOffsets(0, 0, 0, 0);
        enemyMovementPatterns = new EnemyMovementPatterns(pattern, pixelToGoWidth, pixelToGoHeight, direction);
        targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition(position));
        distanceToBackground = 1;
    }

    @Override
    public String toString() {
        return "Ghost: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition(position));
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("ghostsmall.png", position.getX(), position.getY(), size, rotation);
    }
}
