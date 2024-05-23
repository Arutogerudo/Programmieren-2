package thd.gameobjects.base;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;

import java.awt.*;

/**
 * Game objects that are able to collide with other game objects.
 */
public abstract class CollidingGameObject extends GameObject {
    private final Rectangle hitBoxRectangle;
    private double hitBoxOffsetX;
    private double hitBoxOffsetY;
    private double hitBoxOffsetWidth;
    private double hitBoxOffsetHeight;

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public CollidingGameObject(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        hitBoxRectangle = new Rectangle(0, 0, 0, 0);
    }

    /**
     * Determines if this game object currently collides with the other game object. Both hitboxes are updated before
     * detection.
     *
     * @param other The other game object.
     * @return <code>true</code> if the there was a collision.
     */
    public final boolean collidesWith(CollidingGameObject other) {
        updateHitBox();
        other.updateHitBox();
        return hitBoxRectangle.intersects(other.hitBoxRectangle);
    }

    private void updateHitBox() {
        hitBoxRectangle.x = (int) (position.getX() + hitBoxOffsetX);
        hitBoxRectangle.y = (int) (position.getY() + hitBoxOffsetY);
        hitBoxRectangle.width = (int) (width + hitBoxOffsetWidth);
        hitBoxRectangle.height = (int) (height + hitBoxOffsetHeight);
    }

    /**
     * Determines position and size of the hitbox relatively to the position and size of the game object.
     *
     * @param offsetX      x-coordinate, relative to the game objects' x-coordinate.
     * @param offsetY      y-coordinate, relative to the game objects' y-coordinate.
     * @param offsetWidth  Width, relative to the game objects' width.
     * @param offsetHeight Height, relative to the game objects' height.
     */
    protected void hitBoxOffsets(double offsetX, double offsetY, double offsetWidth, double offsetHeight) {
        this.hitBoxOffsetX = offsetX;
        this.hitBoxOffsetY = offsetY;
        this.hitBoxOffsetWidth = offsetWidth;
        this.hitBoxOffsetHeight = offsetHeight;
    }

    /**
     * If a game object is collided with another game object, it reacts to the collision. This method needs to be
     * overridden by game objects and implemented with appropriate reactions.
     *
     * @param other The other game object that is involved in the collision.
     */
    public abstract void reactToCollisionWith(CollidingGameObject other);

    /**
     * Shows hitbox of this game object as a red rectangle.
     */
    public void showHitBox() {
        if (hitBoxRectangle.width > 0 && hitBoxRectangle.height > 0) {
            gameView.addRectangleToCanvas(hitBoxRectangle.x, hitBoxRectangle.y, hitBoxRectangle.width, hitBoxRectangle.height, 2, false, Color.RED);
        }
    }
}
