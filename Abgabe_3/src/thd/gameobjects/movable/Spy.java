package thd.gameobjects.movable;
import thd.gameobjects.base.GameObject;
import thd.game.utilities.GameView;

import java.awt.*;

/**
 * movable Gameobject Spy (enemy).
 */
public class Spy extends GameObject {
    private final TriangularMovementPattern triangularMovementPattern;

    /**
     * Creates a spy in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Spy(GameView gameView) {
        super(gameView);
        size = 30;
        position.updateCoordinates(800, 400);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        triangularMovementPattern = new TriangularMovementPattern(position, 450, 200);
        targetPosition.updateCoordinates(triangularMovementPattern.nextTargetPosition());
    }

    @Override
    public String toString(){
        return "Spion: " + position;
    }


    @Override
    public void updatePosition(){
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(triangularMovementPattern.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("spybig.png", position.getX(), position.getY(), 0.1, 0);
    }

}
