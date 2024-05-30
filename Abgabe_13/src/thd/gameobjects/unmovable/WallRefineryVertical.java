package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Wall Refinery (game field).
 */
public class WallRefineryVertical extends CollidingGameObject implements ShiftableGameObject {
    private static final int WIDTH = 25;
    private static final int SINGLE_HEIGHT = 28;
    private final int number;

    /**
     * Creates a fence in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param number number of single walls vertical under each other
     */
    public WallRefineryVertical(GameView gameView, GamePlayManager gamePlayManager, int number){
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        singleHeight = SINGLE_HEIGHT;
        height = singleHeight * number;
        distanceToBackground = 0;
        this.number = number;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Fence: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < number; orderplace++) {
            gameView.addImageToCanvas("wallrefinery.png", position.getX(), position.getY() + orderplace * singleHeight, size, rotation);
        }
    }

}