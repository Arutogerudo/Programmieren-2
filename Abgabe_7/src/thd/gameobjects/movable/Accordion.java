package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.*;

/**
 * movable Gameobject Accordion (enemy).
 */
public class Accordion extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject<GameObject> {
    private final EnemyMovementPatterns enemyMovementPatterns;
    private static final int WIDTH_MOVE = 450;
    private static final int HEIGHT_MOVE = 450;

    /**
     * Creates a Accordion in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Accordion(GameView gameView, GamePlayManager gamePlayManager, Position start, String pattern) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(new Position(start));
        size = 0.1;
        speedInPixel = 2;
        rotation = 0;
        width = 75;
        height = 53;
        hitBoxOffsets(0, 0, 0, 0);
        enemyMovementPatterns = new EnemyMovementPatterns(pattern, -99, "None", position, WIDTH_MOVE, HEIGHT_MOVE, "bottom-left", false);
        targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition());
        distanceToBackground = 1;
    }

    public void worldShift(double pixel){
        enemyMovementPatterns.worldShift(pixel);
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
    public String toString() {
        return "Accordion: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition());
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("accordionsmall.png", position.getX(), position.getY(), size, rotation);
    }
    // nur für Wichtel, damit einmal was übergeben. Wieder herausnehmen!
    @Override
    public boolean tryToActivate(GameObject info) {
        if (info instanceof Tank) {
            return false;
        }
        return position.getX() < GameView.WIDTH;
    }
}
