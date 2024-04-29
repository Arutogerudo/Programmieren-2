package thd.gameobjects.base;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;

/**
 * Represents an object in the game.
 */
public abstract class GameObject {

    protected final GameView gameView;
    protected final Position position;
    protected final Position targetPosition;
    protected double speedInPixel;
    protected double rotation;
    protected double size;
    protected double width;
    protected double height;
    protected final GamePlayManager gamePlayManager;
    protected static final double GAMEVIEW_WIDTH = 1280;
    protected static final double GAMEVIEW_HEIGHT = 720;

    /**
     * Crates a new GameObject.
     *
     * @param gameView GameView to show the game object on.
     * @param gamePlayManager Game Play Manager
     */
    public GameObject(GameView gameView, GamePlayManager gamePlayManager) {
        this.gameView = gameView;
        position = new Position();
        targetPosition = new Position();
        this.gamePlayManager = gamePlayManager;
    }

    /**
     * Updates the position of the game object.
     */
    public void updatePosition() {
    }

    /**
     * Updates the status of the game object.
     */
    public void updateStatus(){
    }

    /**
     * Draws the game object to the canvas.
     */
    public abstract void addToCanvas();

    /**
     * Returns the current position of the game object.
     *
     * @return position of the game object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns width of game object.
     *
     * @return Width of game object
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns height of game object.
     *
     * @return Height of game object
     */
    public double getHeight() {
        return height;
    }
}