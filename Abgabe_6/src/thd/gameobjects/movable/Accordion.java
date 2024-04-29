package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;

/**
 * movable Gameobject Accordion (enemy).
 */
public class Accordion extends CollidingGameObject {
    private final LinearMovementPattern linearMovementPattern;
    private static final double X_COORDINATE = 200;
    private static final double Y_COORDINATE = 150;
    private static final int PIXEL_TO_GO = 300;

    /**
     * Creates a Accordion in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Accordion(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(X_COORDINATE, Y_COORDINATE);
        speedInPixel = 2;
        rotation = 0;
        width = 75;
        height = 53;
        hitBoxOffsets(0, 0, 0, 0);
        linearMovementPattern = new LinearMovementPattern(position.getX(), position.getY(), PIXEL_TO_GO, "right");
        targetPosition.updateCoordinates(linearMovementPattern.nextTargetPosition());
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
            targetPosition.updateCoordinates(linearMovementPattern.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("accordionsmall.png", position.getX(), position.getY(), size, rotation);
    }

}
