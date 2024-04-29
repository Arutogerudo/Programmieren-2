package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.game.utilities.GameView;
import thd.gameobjects.base.MainCharacter;

/**
 * movable Gameobject Tank (maincharacter).
 */
public class Tank extends GameObject implements MainCharacter {
    private String imageURL;
    private int shotDurationInMilliseconds;
    private static final double ADDITIONAL_X_VALUE_FOR_SHOT_POSITION = 70;
    private static final double ADDITIONAL_Y_VALUE_FOR_SHOT_POSITION = 19;

    /**
     * Creates the tank in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Tank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(100, 50);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        shotDurationInMilliseconds = 300;
    }

    /**
     * Tank moves left.
     */
    public void left() {
        position.left(speedInPixel);
    }

    /**
     * Tank moves right.
     */
    public void right() {
        position.right(speedInPixel);
    }

    /**
     * Tank moves up.
     */
    public void up() {
        position.up(speedInPixel);
    }

    /**
     * Tank moves down.
     */
    public void down() {
        position.down(speedInPixel);
    }

    @Override
    public void shoot() {
        if (gameView.timer(shotDurationInMilliseconds, gameView)){
            Shot shot = new Shot(gameView, gamePlayManager, position.getX() + ADDITIONAL_X_VALUE_FOR_SHOT_POSITION, position.getY() + ADDITIONAL_Y_VALUE_FOR_SHOT_POSITION);
            gamePlayManager.spawnGameObject(shot);
        }
    }

    @Override
    public String toString() {
        return "Tank: " + position;
    }


    @Override
    public void updatePosition() {
    }

    @Override
    public void updateStatus() {

    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("tankrightwb.png", position.getX(), position.getY(), size, rotation);
    }
}
