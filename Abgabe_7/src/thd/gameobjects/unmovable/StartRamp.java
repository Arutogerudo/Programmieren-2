package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;

/**
 * movable Gameobject StartRamp (game field).
 */
public class StartRamp extends CollidingGameObject {

    /**
     * Creates a StartRamp in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public StartRamp(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 70;
        height = 127;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Start Ramp: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("startramp.png", position.getX(), position.getY(), size, rotation);
    }

}
