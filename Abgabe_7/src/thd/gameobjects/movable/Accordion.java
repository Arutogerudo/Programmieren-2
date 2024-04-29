package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * movable Gameobject Accordion (enemy).
 */
public class Accordion extends CollidingGameObject implements ShiftableGameObject {
    private final QuadraticMovementPatternUse quadraticMovementPatternUse;
    private static final int WIDTH_MOVE = 450;
    private static final int HEIGHT_MOVE = 450;

    /**
     * Creates a Accordion in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Accordion(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        size = 0.1;
        speedInPixel = 2;
        rotation = 0;
        width = 75;
        height = 53;
        hitBoxOffsets(0, 0, 0, 0);
        quadraticMovementPatternUse = new QuadraticMovementPatternUse(position, WIDTH_MOVE, HEIGHT_MOVE, "bottom-left", false);
        targetPosition.updateCoordinates(quadraticMovementPatternUse.nextTargetPosition());
        distanceToBackground = 1;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Shot) {
            gamePlayManager.destroyGameObject(this);
        } else if (other instanceof Tank){
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public String toString() {
        return "Accordion: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(quadraticMovementPatternUse.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("accordionsmall.png", position.getX(), position.getY(), size, rotation);
    }

}
