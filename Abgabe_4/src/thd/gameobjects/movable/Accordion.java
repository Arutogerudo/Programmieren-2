package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject Accordion (enemy).
 */
public class Accordion extends GameObject {
    private final QuadraticMovementPatternUse quadraticMovementPattern;

    /**
     * Creates a Accordion in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Accordion(GameView gameView) {
        super(gameView);
        size = 30;
        position.updateCoordinates(200, 150);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        quadraticMovementPattern = new QuadraticMovementPatternUse(position, 450, 200);
        targetPosition.updateCoordinates(quadraticMovementPattern.nextTargetPosition());
    }

    @Override
    public String toString() {
        return "Accordion: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(quadraticMovementPattern.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("accordionsmall.png", position.getX(), position.getY(), 0.1, rotation);
    }
}
