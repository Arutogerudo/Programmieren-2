package thd.gameobjects.unmovable;

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
     * @param gameView provides gameview
     */
    public RadioactivePack(GameView gameView) {
        super(gameView);
        size = 30;
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
        gameView.addImageToCanvas("radioactivepack.png", position.getX(), position.getY(), 0.1, 0);
    }
}
