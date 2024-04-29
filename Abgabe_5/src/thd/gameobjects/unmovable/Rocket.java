package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject Rocket (game field).
 */
public class Rocket extends GameObject {

    /**
     * Creates a Rocket in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Rocket(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
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
        gameView.addImageToCanvas("rocket.png", position.getX(), position.getY(), size, rotation);
    }

}
