package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Candy Cane horizontal (game field).
 */
public class CandyCaneHorizontal extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 20;

    /**
     * Creates a candy cane horizontal in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public CandyCaneHorizontal(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Candy Cane: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("sidecandycane.png", position.getX(), position.getY(), size, rotation);
    }

}
