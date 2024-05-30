package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Stone Wall (game field).
 */
public class StoneWall extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 30;
    private static final int HEIGTH = 25;
    private static final int AMOUNT_OF_WALLS = 18;

    /**
     * Creates a bush in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public StoneWall(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = HEIGTH * AMOUNT_OF_WALLS;
        singleHeight = HEIGTH;
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
        for (int orderplace = 0; orderplace < AMOUNT_OF_WALLS; orderplace++) {
            gameView.addImageToCanvas("stonewall.png", position.getX(), position.getY() + orderplace * singleHeight, size, rotation);
        }
    }
}