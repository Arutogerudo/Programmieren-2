package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * movable Gameobject Random Ball (enemy).
 */
public class RandomBall extends GameObject {
    private boolean stop;
    private final QuadraticMovementPattern quadraticMovementPattern;

    /**
     * Creates a random ball in the given gameview.
     *
     * @param gameView provides gameview
     */
    public RandomBall(GameView gameView) {
        super(gameView);
        size = 50;
        speedInPixel = 4;
        rotation = 0;
        width = size;
        height = size;
        stop = true;
        quadraticMovementPattern = new QuadraticMovementPattern();
        position.updateCoordinates(new RandomMovementPattern().startPosition());
        targetPosition.updateCoordinates(quadraticMovementPattern.nextTargetPosition());
    }

    @Override
    public String toString() {
        return "Ghost: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(quadraticMovementPattern.nextTargetPosition());
        }
        if (stop) {
            position.moveToPosition(targetPosition, speedInPixel);
        }
        if (gameView.timer(5000, this)) {
            speedInPixel += 1;
        }
        if (gameView.timer(8000, this)) {
            stop = false;
            if (gameView.timer(2000, this)){
                stop = true;
            }
        }
    }

    @Override
    public void addToCanvas() {
        if (gameView.gameTimeInMilliseconds() < 5000) {
            gameView.addOvalToCanvas(position.getX(), position.getY(), width, height, 2, true, Color.YELLOW);
        } else {
            gameView.addOvalToCanvas(position.getX(), position.getY(), width, height, 2, true, Color.RED);
        }
        gameView.addOvalToCanvas(targetPosition.getX(), targetPosition.getY(), width, height, 2, false, Color.WHITE);
    }
}
