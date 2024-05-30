package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject SideWallBridge (game field).
 */
public class SideWallBridge extends CollidingGameObject implements ShiftableGameObject {

    private static final double SIZE1 = 0.165;
    private static final int WIDTH = 40;
    private static final int SINGLE_HEIGHT = 42;

    /**
     * Creates a side wall of the location bridge in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public SideWallBridge(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE1;
        rotation = 0;
        width = WIDTH;
        singleHeight = SINGLE_HEIGHT;
        height = singleHeight * 9;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Side Wall Bridge: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < 9; orderplace++) {
            gameView.addImageToCanvas("sidewallbridge.png", position.getX(), position.getY() + orderplace * singleHeight, size, rotation);
        }
    }

}