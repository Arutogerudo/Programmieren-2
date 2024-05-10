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
    private String alignment;
    private double sizeSide;

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     * @param alignment       the alignment where the wall is
     */
    public WallRocketPad(GameView gameView, GamePlayManager gamePlayManager, String alignment) {
        super(gameView, gamePlayManager);
        this.alignment = alignment;
        size = 0.1;
        sizeSide = 0.09;
        rotation = 0;
        width = 22;
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
        if (alignment.equals("top")) {
            gameView.addImageToCanvas("wallrocketpadup.png", position.getX(), position.getY(), size, rotation);
        } else if (alignment.equals("bottom")) {
            gameView.addImageToCanvas("wallrocketpaddown.png", position.getX(), position.getY(), size, rotation);
        } else if (alignment.equals("left")) {
            gameView.addImageToCanvas("wallrocketpadleft.png", position.getX(), position.getY(), sizeSide, rotation);
        } else if (alignment.equals("right")) {
            gameView.addImageToCanvas("wallrocketpadright.png", position.getX(), position.getY(), sizeSide, rotation);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}
