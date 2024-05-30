package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject CD Plate in Brigde (game field).
 */
public class CDPlateBridge extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 87;
    private static final int SINGLE_HEIGHT = 25;
    private static final int AMOUNT_OF_CD_PLATES = 14;

    /**
     * Creates a cd plate in location bridge in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public CDPlateBridge(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        singleHeight = SINGLE_HEIGHT;
        height = singleHeight * AMOUNT_OF_CD_PLATES;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Bridge: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < AMOUNT_OF_CD_PLATES; orderplace++) {
            gameView.addImageToCanvas("cdplatebridge.png", position.getX(), position.getY() + orderplace * singleHeight, size, rotation);
        }
    }

}