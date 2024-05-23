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
public class StoneWall extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject<GameObject> {

    /**
     * Creates a bush in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public StoneWall(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 30;
        height = 25 * 18;
        singleHeight = 25;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Stone Wall: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < 18; orderplace++) {
            gameView.addImageToCanvas("stonewall.png", position.getX(), position.getY() + orderplace * singleHeight, size, rotation);
        }
    }

    @Override
    public boolean tryToActivate(GameObject info) {
        if (info instanceof Tank) {
            return false;
        }
        return position.getX() < GameView.WIDTH;
    }
}