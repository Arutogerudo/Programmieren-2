package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * movable Gameobject Random Ball (enemy).
 */
public class FollowerBall extends GameObject {
    private final RandomBall followMe;

    /**
     * Creates a random ball in the given gameview.
     *
     * @param gameView provides gameview
     * @param followMe ball to follow
     */
    public FollowerBall(GameView gameView, RandomBall followMe) {
        super(gameView);
        this.followMe = followMe;
        size = 50;
        rotation = 0;
        width = size;
        height = size;
        targetPosition.updateCoordinates(followMe.getPosition());
        speedInPixel = 3;
    }

    @Override
    public String toString() {
        return "Ghost: " + position;
    }


    @Override
    public void updatePosition() {
        targetPosition.updateCoordinates(followMe.getPosition());
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.getX(), position.getY(), width, height, 2, true, Color.GREEN);
    }
}
