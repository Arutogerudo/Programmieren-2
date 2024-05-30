package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall in Refinery (game field).
 */
public class WallRefineryHorizontal extends CollidingGameObject implements ShiftableGameObject {
    private static final int SIZE_PIXEL = 25;
    private final int number;

    /**
     * Creates a wall of location refinery in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param number number of single walls horizontal next to each other
     */
    public WallRefineryHorizontal(GameView gameView, GamePlayManager gamePlayManager, int number) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        singleWidth = SIZE_PIXEL;
        width = singleWidth * number;
        height = SIZE_PIXEL;
        distanceToBackground = 1;
        this.number = number;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Wall Refinery: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < number; orderplace++) {
            gameView.addImageToCanvas("wallrefinery.png", position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }
}
