package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall in Rocket Pad (game field).
 */
public class WallRocketPad extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject {
    private static final String WALL_ROCKET_PAD = "LLLL\nOOOO\nLLLL\nWWLL\nLLLL\nLLWW\nLLLL\nWWLL\nLLLL";
    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public WallRocketPad(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 3;
        rotation = 0;
        width = widthOfBlockImage(WALL_ROCKET_PAD) * size;
        height = heightOfBlockImage(WALL_ROCKET_PAD) * size;
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
        gameView.addBlockImageToCanvas(WALL_ROCKET_PAD, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}
