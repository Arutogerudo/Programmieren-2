package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject Jet (game field).
 */
public class Jet extends GameObject {

    /**
     * Creates a jet in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Jet(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(600, 400);
        rotation = 0;
        width = size;
        height = size;
    }

    @Override
    public String toString(){
        return "Jet: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("jet.png", position.getX(), position.getY(), size, rotation);
    }
}
