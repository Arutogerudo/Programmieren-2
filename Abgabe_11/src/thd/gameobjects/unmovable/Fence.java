package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class Fence extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject{

    /**
     * Creates a fence in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Fence(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.175;
        rotation = 0;
        width = 87;
        singleHeight = 42;
        height = singleHeight * 8;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Fence: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < 10; orderplace++) {
            gameView.addImageToCanvas("fenceairport.png", position.getX(), position.getY() + orderplace * singleHeight, size, rotation);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}