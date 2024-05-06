package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.Tank;

/**
 * unmovable Gameobject radioactive pad.
 * Must be collected in various locations.
 * By collecting you gain ammutition.
 */
public class RadioactivePack extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject {

    /**
     * Creates a radioactive pack in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public RadioactivePack(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 30;
        height = 63;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 2;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Tank){
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public String toString() {
        return "Score: " + position;
    }


    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("radioactivepack.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}
