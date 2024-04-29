package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class Bush extends GameObject {

    /**
     * Creates a bush in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Bush(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = 25;
        height = 25;
        distanceToBackground = 0;
    }

    @Override
    public String toString() {
        return "Bush: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("bush.png", position.getX(), position.getY(), size, rotation);
    }
}
