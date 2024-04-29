package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * movable Gameobject Square.
 */
public class Square extends GameObject {

    /**
     * Creates a square in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Square(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 3;
        position.updateCoordinates(100, 100);
        speedInPixel = 5;
        rotation = 0;
        width = size;
        height = size;
    }

    @Override
    public String toString() {
        return "Spion: " + position;
    }


    @Override
    public void updatePosition() {
        position.right(speedInPixel);
    }

    @Override
    public void updateStatus() {
        if (position.getX() < 0 || position.getX() > GAMEVIEW_WIDTH || position.getY() < 0 || position.getY() > GAMEVIEW_HEIGHT) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.getX(), position.getY(), width, getHeight(), size, false, Color.RED);
    }

}
