package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * movable Gameobject Projectil (munition).
 */
public class Projectile extends GameObject {
    private final LinearMovementPattern linearMovementPattern;

    /**
     * Creates a Projectile in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Projectile(GameView gameView) {
        super(gameView);
        size = 30;
        position.updateCoordinates(200, 150);
        speedInPixel = 2;
        rotation = 0;
        width = size;
        height = size;
        linearMovementPattern = new LinearMovementPattern(getPosition(), "right", speedInPixel);
    }

    @Override
    public String toString() {
        return "Accordion: " + position;
    }

    @Override
    public void updatePosition() {
        position.updateCoordinates(linearMovementPattern.targetPosition());
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("projectile.png", position.getX(), position.getY(), 0.1, rotation);
    }
}
