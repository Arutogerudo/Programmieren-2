package thd.gameobjects.unmovable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject Rocket (game field).
 */
public class Rocket extends GameObject {

    /**
     * Creates a Rocket in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Rocket(GameView gameView) {
        super(gameView);
        size = 30;
        position.updateCoordinates(300, 400);
        rotation = 0;
        width = size;
        height = size;
    }

    @Override
    public String toString(){
        return "Rocket: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("rocket.png", position.getX(), position.getY(), 0.1, 0);
    }

}
