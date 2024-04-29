package thd.gameobjects.unmovable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject StartRamp (game field).
 */
public class StartRamp extends GameObject {

    /**
     * Creates a StartRamp in the given gameview.
     *
     * @param gameView provides gameview
     */
    public StartRamp(GameView gameView) {
        super(gameView);
        size = 30;
        position.updateCoordinates(300, 600);
        rotation = 0;
        width = size;
        height = size;
    }

    @Override
    public String toString(){
        return "Start Ramp: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("startramp.png", position.getX(), position.getY(), 0.1, 0);
    }

}
