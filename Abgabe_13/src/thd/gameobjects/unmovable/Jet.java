package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Jet (game field).
 */
public class Jet extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 158;
    private static final int HEIGHT = 72;

    /**
     * Creates a jet in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Jet(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
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
