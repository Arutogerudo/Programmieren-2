package thd.gameobjects.movable;
import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidingGameObject;
import thd.game.utilities.GameView;

/**
 * movable Gameobject Spy (enemy).
 */
public class Spy extends CollidingGameObject {
    private final TriangularMovementPattern triangularMovementPattern;
    private static final int WIDTH_MOVE = 450;
    private static final int HEIGHT_MOVE = 200;

    /**
     * Creates a spy in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Spy(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(800, 400);
        speedInPixel = 2;
        rotation = 0;
        width = 62;
        height = 65;
        hitBoxOffsets(0, 0, 0, 0);
        triangularMovementPattern = new TriangularMovementPattern(position, WIDTH_MOVE, HEIGHT_MOVE);
        targetPosition.updateCoordinates(triangularMovementPattern.nextTargetPosition());
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
            targetPosition.updateCoordinates(triangularMovementPattern.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("spybig.png", position.getX(), position.getY(), size, rotation);
    }
}
