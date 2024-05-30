package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall in Rocket Pad (game field).
 */
public class WallRocketPadRight extends CollidingGameObject implements ShiftableGameObject {
    private static final int SIZE_PIXEL = 22;
    private final double sizeSide;

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public WallRocketPadRight(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        sizeSide = 0.09;
        rotation = 0;
        width = SIZE_PIXEL;
        height = SIZE_PIXEL;
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
        gameView.addImageToCanvas("wallrocketpadright.png", position.getX(), position.getY(), sizeSide, rotation);
    }

}