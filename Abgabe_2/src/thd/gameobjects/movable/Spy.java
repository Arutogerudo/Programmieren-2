package thd.gameobjects.movable;
import thd.gameobjects.base.Position;
import thd.game.utilities.GameView;

import java.awt.*;

/**
 * movable Gameobject Spy (enemy).
 */
public class Spy {
    private final GameView gameView;
    private final Position position;
    private final double speedInPixel;
    private final double rotation;
    private final double size;
    private final double width;
    private final double height;

    /**
     * Creates a spy in the given gameview.
     *
     * @param gameView provides gameview
     */
    public Spy(GameView gameView) {
        this.gameView = gameView;
        this.size = 30;
        position = new Position(1100, 600);
        speedInPixel = 2;
        this.rotation = 0;
        this.width = 150;
        this.height = 33;
    }

    @Override
    public String toString(){
        return "Spion: " + position;
    }

    /**
     * This method updates the position of the created gameobject.
     */
    public void updatePosition(){
        position.left(speedInPixel);
    }

    /**
     * This method adds a gameobjects to the canvas.
     */
    public void addToCanvas() {
        gameView.addImageToCanvas("spybig.png", position.getX(), position.getY(), 0.1, 0);
    }

}
