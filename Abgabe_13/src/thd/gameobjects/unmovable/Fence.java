package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Fence (game field).
 */
public class Fence extends CollidingGameObject implements ShiftableGameObject {

    private static final double SIZE1 = 0.175;
    private static final int WIDTH = 87;
    private static final int SINGLE_HEIGHT = 42;

    /**
     * Creates a fence in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Fence(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE1;
        rotation = 0;
        width = WIDTH;
        singleHeight = SINGLE_HEIGHT;
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

}