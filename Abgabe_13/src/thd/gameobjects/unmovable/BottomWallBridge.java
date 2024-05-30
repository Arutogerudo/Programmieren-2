package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject bottom wall bridge (game field).
 */
public class BottomWallBridge extends CollidingGameObject implements ShiftableGameObject{

    private static final int SIZE_PIXEL = 25;
    private static final int NUMBER_OF_WALLS = 17;

    /**
     * Creates a bottom wall in location bridge in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param location        location of the wall
     */
    public BottomWallBridge(GameView gameView, GamePlayManager gamePlayManager, String location) {
        super(gameView, gamePlayManager);
        size = SIZE;
        if (location.equals("top")) {
            rotation = ROTATION_180;
        } else {
            rotation = 0;
        }
        singleWidth = SIZE_PIXEL;
        width = singleWidth * NUMBER_OF_WALLS;
        height = SIZE_PIXEL;
        distanceToBackground = 1;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Bottom wall bridge: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < NUMBER_OF_WALLS; orderplace++) {
            gameView.addImageToCanvas("bottomwallbridge.png", position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }
}
