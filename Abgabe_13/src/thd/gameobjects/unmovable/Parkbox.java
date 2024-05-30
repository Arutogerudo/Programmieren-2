package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class Parkbox extends CollidingGameObject implements ShiftableGameObject {

    private static final int SIZE_PIXEL = 25;

    /**
     * Creates a parkbox in the airport in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Parkbox(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = SIZE_PIXEL;
        height = SIZE_PIXEL;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Parkbox: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("parkboxairport.png", position.getX(), position.getY(), size, rotation);
    }

}
