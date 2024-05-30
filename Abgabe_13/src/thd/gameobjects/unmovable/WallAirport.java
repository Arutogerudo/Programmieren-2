package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall in Airport (game field).
 */
public class WallAirport extends CollidingGameObject implements ShiftableGameObject {

    private static final int SINGLE_WIDTH = 25;
    private static final int AMOUNT_WALLS = 52;
    private static final int HEIGHT = 22;

    /**
     * Creates a wall in the airport in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public WallAirport(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        singleWidth = SINGLE_WIDTH;
        width = singleWidth * AMOUNT_WALLS;
        height = HEIGHT;
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
        for (int orderplace = 0; orderplace < AMOUNT_WALLS; orderplace++) {
            gameView.addImageToCanvas("wallairport.png", position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }

}