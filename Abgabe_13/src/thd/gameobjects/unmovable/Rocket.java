package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * movable Gameobject Rocket (game field).
 */
public class Rocket extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 128;
    private static final int HEIGHT = 45;

    /**
     * Creates a Rocket in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Rocket(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString(){
        return "Rocket: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("rocket.png", position.getX(), position.getY(), size, rotation);
    }

}
