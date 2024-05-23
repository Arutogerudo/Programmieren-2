package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class WallAirport extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject{

    /**
     * Creates a wall in the airport in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public WallAirport(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        singleWidth = 25;
        width = singleWidth * 52;
        height = 22;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Wall in Airport: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < 52; orderplace++) {
            gameView.addImageToCanvas("wallairport.png", position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}