package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.Tank;

/**
 * unmovable Gameobject Bush (game field).
 */
public class Bush extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject<GameObject> {

    /**
     * Creates a bush in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Bush(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 25;
        height = 25;
        distanceToBackground = 1;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Bush: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("bush.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public boolean tryToActivate(GameObject info) {
        if (info instanceof Tank) {
            return false;
        }
        return position.getX() < GameView.WIDTH;
    }
}
