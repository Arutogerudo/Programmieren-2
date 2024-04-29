package thd.gameobjects.movable;
import thd.gameobjects.base.GameObject;
import thd.game.utilities.GameView;

import java.awt.*;

/**
 * movable Gameobject Tank (maincharacter).
 */
public class Tank extends GameObject {
    private boolean shotInProgress;

    /**
     * Creates the tank in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Tank(GameView gameView) {
        super(gameView);
        size = 0.1;
        position.updateCoordinates(100, 50);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        shotInProgress = false;
    }

    /**
     * Tank moves left.
     */
    public void left(){
        position.left(speedInPixel);
    }

    /**
     * Tank moves right.
     */
    public void right(){
        position.right(speedInPixel);
    }

    /**
     * Tank moves up.
     */
    public void up(){
        position.up(speedInPixel);
    }

    /**
     * Tank moves down.
     */
    public void down(){
        position.down(speedInPixel);
    }

    /**
     * Tank shoots his projectile in a linear way.
     */
    public void shoot() {
        shotInProgress = true;
    }

    @Override
    public String toString(){
        return "Tank: " + position;
    }


    @Override
    public void updatePosition(){
    }

    @Override
    public void updateStatus(){
        if (gameView.timer(1000, this)){
            size += 0.1;
        }
    }

    @Override
    public void addToCanvas() {
        if (shotInProgress) {
            gameView.addTextToCanvas("X", position.getX(), position.getY(), size * 100, true, Color.BLACK, 0);
            shotInProgress = !shotInProgress;
        } else {
            gameView.addImageToCanvas("tankdownwb.png", position.getX(), position.getY(), size, 0);
        }
    }
}
