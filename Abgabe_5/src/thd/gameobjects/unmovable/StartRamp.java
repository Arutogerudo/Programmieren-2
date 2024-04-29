package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject StartRamp (game field).
 */
public class StartRamp extends GameObject {

    /**
     * Creates a StartRamp in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public StartRamp(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
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
        gameView.addImageToCanvas("startramp.png", position.getX(), position.getY(), size, rotation);
    }

}
