package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

class Shot extends GameObject {
    Shot(GameView gameView, GamePlayManager gamePlayManager, double xCoordinate, double yCoordinate) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(xCoordinate, yCoordinate);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
    }

    @Override
    public String toString() {
        return "Shot: " + position;
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
        gameView.addImageToCanvas("shotright.png", position.getX(), position.getY(), size, rotation);
    }

}
