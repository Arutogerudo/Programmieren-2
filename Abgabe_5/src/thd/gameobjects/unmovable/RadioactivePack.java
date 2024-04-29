package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.game.utilities.GameView;

/**
 * unmovable Gameobject radioactive pad.
 * Must be collected in various locations.
 * By collecting you gain ammutition.
 */
public class RadioactivePack extends GameObject {

    /**
     * Creates a radioactive pack in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public RadioactivePack(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        rotation = 0;
        width = size;
        height = size;
        position.updateCoordinates(GameView.WIDTH - width, 0);
    }

    @Override
    public String toString() {
        return "Score: " + position;
    }


    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("radioactivepack.png", position.getX(), position.getY(), size, rotation);
    }
}
