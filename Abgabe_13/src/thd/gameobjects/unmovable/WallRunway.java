package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class WallRunway extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 25;

    /**
     * Creates a wall of the runway in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public WallRunway(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = 10;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Wall of Runway: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("wallrunway.png", position.getX(), position.getY(), size, rotation);
    }

}
