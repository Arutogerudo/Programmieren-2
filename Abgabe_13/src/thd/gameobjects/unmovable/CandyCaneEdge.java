package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Candy Cane edge (game field).
 */
public class CandyCaneEdge extends CollidingGameObject implements ShiftableGameObject {
    private static final int SIZE_PIXEL = 20;
    private final double additionalX;
    private final double additionalY;
    /**
     * Creates a candy cane edge in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param edge            edge of the candy cane
     */
    public CandyCaneEdge(GameView gameView, GamePlayManager gamePlayManager, String edge) {
        super(gameView, gamePlayManager);
        size = SIZE;
        switch (edge) {
            case "topleft" -> {
                rotation = 0;
                additionalX = 0;
                additionalY = 0;
            }
            case "topright" -> {
                rotation = ROTATION_90;
                additionalX = -7;
                additionalY = 0;
            }
            case "bottomright" -> {
                rotation = ROTATION_180;
                additionalX = -7;
                additionalY = -7;
            }
            default -> {
                rotation = ROTATION_270;
                additionalX = 0;
                additionalY = -7;
            }
        }
        width = SIZE_PIXEL;
        height = SIZE_PIXEL;
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString() {
        return "Candy Cane: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("edgecandycane.png", position.getX() + additionalX, position.getY() + additionalY, size, rotation);
    }

}
