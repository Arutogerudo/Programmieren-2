package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class CentrelineRunway extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 19;

    /**
     * Creates a centreline of the runway in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public CentrelineRunway(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = 9;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Centreline of Runway: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("centrelinerunway.png", position.getX(), position.getY(), size, rotation);
    }

}
