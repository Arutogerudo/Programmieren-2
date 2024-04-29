package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;

/**
 * unmovable Gameobject Jet (game field).
 */
public class Jet extends CollidingGameObject {

    /**
     * Creates a jet in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Jet(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 158;
        height = 72;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString(){
        return "Jet: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("jet.png", position.getX(), position.getY(), size, rotation);
    }

}
