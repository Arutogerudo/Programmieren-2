package thd.gameobjects.movable;
import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidingGameObject;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * movable Gameobject Spy (enemy).
 */
public class Spy extends CollidingGameObject implements ShiftableGameObject {
    private final QuadraticMovementPatternUse quadraticMovementPatternUse;
    private static final int WIDTH_MOVE = 250;
    private static final int HEIGHT_MOVE = 100;

    /**
     * Creates a spy in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Spy(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        size = 0.1;
        speedInPixel = 2;
        rotation = 0;
        width = 62;
        height = 65;
        hitBoxOffsets(0, 0, 0, 0);
        quadraticMovementPatternUse = new QuadraticMovementPatternUse(position, WIDTH_MOVE, HEIGHT_MOVE, "top-left", true);
        targetPosition.updateCoordinates(quadraticMovementPatternUse.nextTargetPosition());
        distanceToBackground = 1;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Shot) {
            gamePlayManager.destroyGameObject(this);
        } else if (other instanceof Tank){
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public String toString(){
        return "Spion: " + position;
    }


    @Override
    public void updatePosition(){
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(quadraticMovementPatternUse.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("spybig.png", position.getX(), position.getY(), size, rotation);
    }
}
