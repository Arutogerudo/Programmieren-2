package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Bush (game field).
 */
public class ChainRefinery extends CollidingGameObject implements ShiftableGameObject {
    private static final int SINGLE_WIDTH = 50;
    private static final int SINGLE_HEIGHT = 30;
    private final String direction;

    /**
     * Creates a bush in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param direction direction of the chain
     */
    public ChainRefinery(GameView gameView, GamePlayManager gamePlayManager, String direction) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        if (direction.equals("horizontal")) {
            width = SINGLE_WIDTH * 10;
        } else {
            width = SINGLE_WIDTH;
        }
        if (direction.equals("vertical")) {
            height = SINGLE_HEIGHT * 9;
        } else {
            height = SINGLE_HEIGHT;
        }
        distanceToBackground = 1;
        this.direction = direction;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Chain refinery: " + position;
    }

    @Override
    public void addToCanvas() {
        if (direction.equals("horizontal")) {
            for (int orderplace = 0; orderplace < 10; orderplace++) {
                gameView.addImageToCanvas("chainrefinery.png", position.getX() + orderplace * SINGLE_WIDTH, position.getY(), size, rotation);
            }
        } else {
            for (int orderplace = 0; orderplace < 9; orderplace++) {
                gameView.addImageToCanvas("chainrefinery.png", position.getX(), position.getY() + orderplace * SINGLE_HEIGHT, size, rotation);
            }
        }

    }

}
