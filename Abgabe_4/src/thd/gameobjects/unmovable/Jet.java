package thd.gameobjects.unmovable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject Jet (game field).
 */
public class Jet extends GameObject {

    /**
     * Creates a jet in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Jet(GameView gameView) {
        super(gameView);
        size = 30;
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
        gameView.addImageToCanvas("jet.png", position.getX(), position.getY(), 0.1, 0);
    }

}
