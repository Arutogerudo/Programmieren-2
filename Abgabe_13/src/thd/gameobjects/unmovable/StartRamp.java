package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * movable Gameobject StartRamp (game field).
 */
public class StartRamp extends CollidingGameObject implements ShiftableGameObject {

    private static final double SIZE1 = 0.12;
    private static final int WIDTH = 83;
    private static final int HEIGHT = 157;

    /**
     * Creates a StartRamp in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public StartRamp(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE1;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
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
