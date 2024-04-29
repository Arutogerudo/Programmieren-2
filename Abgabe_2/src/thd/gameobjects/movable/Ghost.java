package thd.gameobjects.movable;

import thd.gameobjects.base.Position;
import thd.game.utilities.GameView;

import java.awt.*;

/**
 * movable Gameobject Ghost (enemy).
 */
public class Ghost {
    private final GameView gameView;
    private final Position position;
    private double speedInPixel;
    private double rotation;
    private final double size;
    private final double width;
    private final double height;

    /**
     * Creates a ghost in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Ghost(GameView gameView) {
        this.gameView = gameView;
        this.size = 30;
        position = new Position(0, GameView.HEIGHT / 2);
        speedInPixel = 2;
        this.rotation = 0;
        this.width = 150;
        this.height = 33;
    }

    @Override
    public String toString() {
        return "Ghost: " + position;
    }

    /**
     * This method updates the position of the created gameobject.
     */
    public void updatePosition() {
        position.right(speedInPixel);
        rotation += 1;
    }

    /**
     * This method adds a gameobjects to the canvas.
     */
    public void addToCanvas() {
        gameView.addImageToCanvas("ghostsmall.png", position.getX(), position.getY(), 0.1, rotation);
    }
}
