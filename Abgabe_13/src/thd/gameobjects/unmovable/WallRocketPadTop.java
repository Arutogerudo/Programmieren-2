package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall in Rocket Pad (game field).
 */
public class WallRocketPadTop extends CollidingGameObject implements ShiftableGameObject{

    private static final int SINGLE_WIDTH = 24;
    private static final int AMOUNT_WALLS = 51;
    private static final int HEIGHT = 22;

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public WallRocketPadTop(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        singleWidth = SINGLE_WIDTH;
        width = singleWidth * AMOUNT_WALLS;
        height = HEIGHT;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Wall in Rocket Pad: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < AMOUNT_WALLS; orderplace++) {
            gameView.addImageToCanvas("wallrocketpadup.png", position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }

}