package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall in Rocket Pad (game field).
 */
public class WallRocketPadTop extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject {
    private double sizeSide;

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public WallRocketPadTop(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        sizeSide = 0.09;
        rotation = 0;
        singleWidth = 24;
        width = singleWidth * 51;
        height = 22;
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
        for (int orderplace = 0; orderplace < 51; orderplace++) {
            gameView.addImageToCanvas("wallrocketpadup.png", position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}