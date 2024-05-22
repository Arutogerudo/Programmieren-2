package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class SafetyBox extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject{

    /**
     * Creates a safetybox in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public SafetyBox(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 220;
        height = 200;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Safetybox: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("safetybox.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}
