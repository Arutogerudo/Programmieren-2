package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Commander Bridge (game field).
 */
public class CommanderBridge extends CollidingGameObject implements ShiftableGameObject {

    private static final double SIZE1 = 0.125;
    private static final int SINGLE_HEIGHT = 110;
    private static final int WIDTH = 190;

    /**
     * Creates a commander bridge in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public CommanderBridge(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE1;
        rotation = 0;
        singleHeight = SINGLE_HEIGHT;
        width = WIDTH;
        height = 2 * singleHeight;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Commander Bridge: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("commanderbridge.png", position.getX(), position.getY(), size, rotation);
        gameView.addImageToCanvas("commanderbridge.png", position.getX(), position.getY() + singleHeight, size, ROTATION_180);
    }

}
