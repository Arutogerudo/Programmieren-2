package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.*;
import thd.game.utilities.GameView;
import thd.gameobjects.unmovable.RadioactivePack;
import java.util.LinkedList;
import java.util.List;

/**
 * movable Gameobject Tank (maincharacter).
 */
public class Tank extends CollidingGameObject implements MainCharacter, ShiftableGameObject {
    private static final int WIDTH = 63;
    private static final int HEIGHT = 55;
    private static final double HALF_FACTOR = 2.0;
    private static final int DEFAULT_ADD = -99;
    private static final int TIMER_ANIMATION = 150;
    private final int shotDurationInMilliseconds;
    private static final int POINTS_PER_PACK = 100;
    private int dimension;
    private boolean unbeatable;
    private boolean movable;
    private List<CollidingGameObject> collidingGameObjectsForPathDecision;
    private State currentState;

    private enum State {
        LIFE_LOST("tankleftwb"), LEFT_BW("tankleftbw.png"), LEFT_WB("tankleftwb.png"), RIGHT_BW("tankrightbw.png"),
        RIGHT_WB("tankrightwb.png"), UP_BW("tankupbw.png"), UP_WB("tankupwb.png"),
        DOWN_BW("tankdownbw.png"), DOWN_WB("tankdownwb.png");
        private final String image;

        State(String image) {
            this.image = image;
        }
    }

    /**
     * Creates the tank in the given gameview.
     *
     * @param gameView         provides gameview
     * @param gamePlayManager  manages the gamePlay
     * @param position         position of the tank
     */
    public Tank(GameView gameView, GamePlayManager gamePlayManager, Position position) {
        super(gameView, gamePlayManager);
        size = SIZE;
        speedInPixel = 2;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
        hitBoxOffsets(0, 0, 0, 0);
        shotDurationInMilliseconds = 300;
        distanceToBackground = 3;
        this.position.updateCoordinates(new Position(position));
        dimension = 1;
        unbeatable = true;
        movable = true;
        currentState = State.DOWN_BW;
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
            gameView.playSound("pack.wav", false);
        } else if (!(other instanceof Shot) && !(other instanceof Tank)) {
            if (!unbeatable) {
                gamePlayManager.lifeLost();
                unbeatable = true;
                movable = false;
            }
        }
    }

    /**
     * Tank moves left, regarding Colliding Objects he cannot went through.
     * and with shifting the world when he is at the right edge of the gameview.
     *
     * @param shiftCounterPerLevel gives the actual number of already done shifts, before the move
     *
     * @return returns the number of already done left shift in the level, after the move
     */
    public int left(int shiftCounterPerLevel) {
        if (movable) {
            currentState = State.LEFT_BW;
            if (position.getX() / dimension <= GAMEVIEW_WIDTH && position.getX() / dimension >= 0){
                position.left(speedInPixel);
                for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                    if (collidesWith(collidingGameObject)) {
                        reactToCollisionWith(collidingGameObject);
                        position.right(speedInPixel);
                        break;
                    }
                }
                return shiftCounterPerLevel;
            } else {
                gamePlayManager.moveWorldToRight(GAMEVIEW_WIDTH);
                return shiftCounterPerLevel - 1;
            }
        }
        return shiftCounterPerLevel;
    }

    /**
     * Tank moves right, regarding Colliding Objects he cannot went through.
     * and with shifting the world when he is at the right edge of the gameview.
     *
     * @param shiftCounterPerLevel gives the actual number of already done shifts, before the move
     *
     * @return returns the number of already done left shift in the level, after the move
     */
    public int right(int shiftCounterPerLevel) {
        if (movable) {
            currentState = State.RIGHT_BW;
            if (position.getX() < GAMEVIEW_WIDTH){
                position.right(speedInPixel); // Bewegung zunächst wie geplant durchführen.
                for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                    if (collidesWith(collidingGameObject)) {
                        reactToCollisionWith(collidingGameObject);
                        position.left(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                        break;
                    }
                }
                return shiftCounterPerLevel;
            } else {
                gamePlayManager.moveWorldToLeft(GAMEVIEW_WIDTH);
                dimension += 1;
                return shiftCounterPerLevel + 1;
            }
        }
        return shiftCounterPerLevel;
    }

    /**
     * Tank moves up.
     */
    public void up() {
        if (movable) {
            currentState = State.UP_BW;
            position.up(speedInPixel); // Bewegung zunächst wie geplant durchführen.
            for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                if (collidesWith(collidingGameObject)) {
                    reactToCollisionWith(collidingGameObject);
                    position.down(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                    break;
                }
            }
        }
    }

    /**
     * Tank moves down.
     */
    public void down() {
        if (movable) {
            currentState = State.DOWN_BW;
            position.down(speedInPixel); // Bewegung zunächst wie geplant durchführen.
            for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                if (collidesWith(collidingGameObject)) {
                    reactToCollisionWith(collidingGameObject);
                    position.up(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                    break;
                }
            }
        }
    }

    private String shotDirection(){
        return switch (currentState) {
            case DOWN_BW, DOWN_WB -> "down";
            case UP_BW, UP_WB -> "up";
            case RIGHT_BW, RIGHT_WB -> "right";
            case LEFT_BW, LEFT_WB -> "left";
            default -> "None";
        };
    }

    @Override
    public void shoot() {
        if (gameView.timer(shotDurationInMilliseconds, gameView) && gamePlayManager.getAmmunition() > 0) {
            Shot shot = new Shot(gameView, gamePlayManager, position.getX() + calculateAdditionalX(), position.getY() + calculateAdditionalY(), shotDirection());
            gamePlayManager.spawnGameObject(shot);
            gamePlayManager.shot();
            gameView.playSound("shot.wav", false);
        }
    }

    private double calculateAdditionalY() {
        return switch (currentState) {
            case DOWN_BW, DOWN_WB -> height;
            case UP_BW, UP_WB -> 0;
            case RIGHT_BW, RIGHT_WB, LEFT_BW, LEFT_WB -> height / HALF_FACTOR - 8;
            default -> DEFAULT_ADD;
        };
    }

    private double calculateAdditionalX() {
        return switch (currentState) {
            case DOWN_BW, DOWN_WB, UP_BW, UP_WB -> width / HALF_FACTOR - 9;
            case RIGHT_BW, RIGHT_WB -> width;
            case LEFT_BW, LEFT_WB -> 0;
            default -> DEFAULT_ADD;
        };
    }

    @Override
    public boolean tryToActivate(Object info) {
        return true;
    }

    @Override
    public String toString() {
        return "Tank: " + position;
    }

    private State lifeLostAnimation(){
        switch (currentState) {
            case UP_BW, UP_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    return State.RIGHT_BW;
                }
                break;
            case DOWN_BW, DOWN_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    return State.LEFT_WB;
                }
                break;
            case LEFT_BW, LEFT_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    return State.UP_WB;
                }
                break;
            case RIGHT_BW, RIGHT_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    return State.DOWN_WB;
                }
                break;
        }
        return State.RIGHT_BW;
    }

    @Override
    public void updateStatus(){
        if (unbeatable && gameView.timer(1000, this)){
            unbeatable = false;
        }
        if (!movable) {
            currentState = lifeLostAnimation();
        }
        switch (currentState) {
            case UP_BW:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.UP_WB;
                }
                break;
            case UP_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.UP_BW;
                }
                break;
            case DOWN_BW:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.DOWN_WB;
                }
                break;
            case DOWN_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.DOWN_BW;
                }
                break;
            case LEFT_BW:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.LEFT_WB;
                }
                break;
            case LEFT_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.LEFT_BW;
                }
                break;
            case RIGHT_BW:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.RIGHT_WB;
                }
                break;
            case RIGHT_WB:
                if (gameView.timer(TIMER_ANIMATION, this)) {
                    currentState = State.RIGHT_BW;
                }
                break;
        }
    }


    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(currentState.image, position.getX(), position.getY(), size, rotation);
    }
}
