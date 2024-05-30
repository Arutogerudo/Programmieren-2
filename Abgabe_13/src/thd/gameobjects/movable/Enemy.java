package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * Game object that is able to move and collide.
 */
public abstract class Enemy extends CollidingGameObject implements ShiftableGameObject {
    private static final int DISTANCE_TO_BACKGROUND = 50;
    static final int TIMER_CHANGE_STATE = 200;
    EnemyMovementPatterns enemyMovementPatterns;

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    Enemy(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        rotation = 0;
        distanceToBackground = DISTANCE_TO_BACKGROUND;
        size = SIZE;
    }

    /**
     * Sets the speed of the enemy.
     *
     * @param speedInPixel Speed of the enemy in pixel per frame.
     */
    public void updateSpeedInPixel(int speedInPixel) {
        this.speedInPixel = speedInPixel;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Shot) {
            gameView.addImageToCanvas("enemydead.png", position.getX(), position.getY(), size, rotation);
            gamePlayManager.destroyGameObject(this);
        } else if (other instanceof Tank){
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
    }
}