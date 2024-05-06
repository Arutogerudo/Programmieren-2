package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidingGameObject;
import thd.game.utilities.GameView;
import thd.gameobjects.base.MainCharacter;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.unmovable.RadioactivePack;
import java.util.LinkedList;
import java.util.List;

/**
 * movable Gameobject Tank (maincharacter).
 */
public class Tank extends CollidingGameObject implements MainCharacter, ShiftableGameObject {
    // private String imageURL;
    private final int shotDurationInMilliseconds;
    private static final double ADDITIONAL_X_VALUE_FOR_SHOT_POSITION = 70;
    private static final double ADDITIONAL_Y_VALUE_FOR_SHOT_POSITION = 19;
    private static final int POINTS_PER_PACK = 100;
    private int dimension;
    private boolean unbeatable;
    private List<CollidingGameObject> collidingGameObjectsForPathDecision;

    /**
     * Creates the tank in the given gameview.
     *
     * @param gameView         provides gameview
     * @param gamePlayManager  manages the gamePlay
     */
    public Tank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        speedInPixel = 5;
        rotation = 0;
        width = 75;
        height = 55;
        hitBoxOffsets(0, 0, 0, 0);
        shotDurationInMilliseconds = 300;
        distanceToBackground = 1;
        this.position.updateCoordinates(100, 100);
        dimension = 1;
        unbeatable = true;
    }

    /**
     * Hand over a list of collinding game objects for path decision.
     *
     * @param collidingGameObjectsForPathDecision List of colliding Game objects for path decision.
     */
    public void setCollidingGameObjectsForPathDecision(LinkedList<CollidingGameObject> collidingGameObjectsForPathDecision){
        this.collidingGameObjectsForPathDecision = collidingGameObjectsForPathDecision;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof RadioactivePack) {
            gamePlayManager.addPoints(POINTS_PER_PACK);
            gamePlayManager.collectPack();
            gamePlayManager.refillAmmunition();
        } else if (!(other instanceof Shot) && !(other instanceof Tank)) {
            if (!unbeatable) {
                gamePlayManager.lifeLost();
                unbeatable = true;
            }
        }
    }

    /**
     * Tank moves left.
     */
    public void left() {
        if (position.getX() / dimension <= GAMEVIEW_WIDTH && position.getX() / dimension >= 0){
            position.left(speedInPixel);
            for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                if (collidesWith(collidingGameObject)) {
                    reactToCollisionWith(collidingGameObject);
                    position.right(speedInPixel);
                    break;
                }
            }
        } else {
            gamePlayManager.moveWorldToRight(GameView.WIDTH);
        }
    }

    /**
     * Tank moves right.
     */
    public void right() {
        if (position.getX() < GAMEVIEW_WIDTH){
            position.right(speedInPixel); // Bewegung zunächst wie geplant durchführen.
            for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                if (collidesWith(collidingGameObject)) {
                    reactToCollisionWith(collidingGameObject);
                    position.left(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                    break;
                }
            }
        } else {
            gamePlayManager.moveWorldToLeft(GameView.WIDTH);
            dimension += 1;
        }


    }

    /**
     * Tank moves up.
     */
    public void up() {
        //position.up(speedInPixel);
        position.up(speedInPixel); // Bewegung zunächst wie geplant durchführen.
        for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
            if (collidesWith(collidingGameObject)) {
                reactToCollisionWith(collidingGameObject);
                position.down(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                break;
            }
        }

    }

    /**
     * Tank moves down.
     */
    public void down() {
        //position.down(speedInPixel);
        position.down(speedInPixel); // Bewegung zunächst wie geplant durchführen.
        for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
            if (collidesWith(collidingGameObject)) {
                reactToCollisionWith(collidingGameObject);
                position.up(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                break;
            }
        }

    }

    @Override
    public void shoot() {
        if (gameView.timer(shotDurationInMilliseconds, gameView) && gamePlayManager.getAmmunition() > 0) {
            Shot shot = new Shot(gameView, gamePlayManager, position.getX() + ADDITIONAL_X_VALUE_FOR_SHOT_POSITION, position.getY() + ADDITIONAL_Y_VALUE_FOR_SHOT_POSITION);
            gamePlayManager.spawnGameObject(shot);
            gamePlayManager.shot();
        }
    }

    @Override
    public String toString() {
        return "Tank: " + position;
    }

    @Override
    public void updateStatus(){
        if (unbeatable && gameView.timer(1000, this)){
            unbeatable = false;
        }
    }


    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("tankrightwb.png", position.getX(), position.getY(), size, rotation);
    }

}
