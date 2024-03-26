package thd.gameobjects.unmovable;

import thd.gameobjects.base.Position;
import thd.game.utilities.GameView;

import java.awt.*;

/**
 * unmovable Gameobject radioactive pad.
 * Must be collected in various locations.
 * By collecting you gain ammutition.
 */
public class RadioactivePack {
    private final GameView gameView;
    private final Position position;
    private double speedInPixel;
    private final double rotation;
    private final double size;
    private final double width;
    private final double height;

    /**
     * Creates a radioactive pack in the given gameview.
     *
     * @param gameView provides gameview
     */
    public RadioactivePack(GameView gameView) {
        this.gameView = gameView;
        this.size = 30;
        this.width = 50;
        this.height = 33;
        position = new Position(GameView.WIDTH - width, 0);
        this.rotation = 0;
    }

    @Override
    public String toString() {
        return "Score: " + position;
    }

    /**
     * This method adds a gameobjects to the canvas.
     */
    public void addToCanvas() {
        gameView.addImageToCanvas("radioactivepack.png", position.getX(), position.getY(), 0.1, 0);
    }
}
