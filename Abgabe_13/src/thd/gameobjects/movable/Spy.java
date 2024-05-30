package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.Position;

/**
 * movable Gameobject Spy (enemy).
 */
public class Spy extends Enemy {
    private static final int WIDTH = 62;
    private static final int HEIGHT = 65;

    private enum State {
        SMALL("spysmall.png"), BIG("spybig.png");
        private final String image;

        State(String image){
            this.image = image;
        }
    }

    private State currentState;

    /**
     * Creates a spy in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param start           given start Position
     * @param pixelToGoWidth  pixels to go horizontal
     * @param pixelToGoHeight pixels to go vertical
     * @param direction       direction of first move
     * @param pattern         given movement pattern of object
     */
    public Spy(GameView gameView, GamePlayManager gamePlayManager, Position start, int pixelToGoWidth, int pixelToGoHeight, String direction, String pattern) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(new Position(start));
        size = SIZE;
        width = WIDTH;
        height = HEIGHT;
        hitBoxOffsets(0, 0, 0, 0);
        enemyMovementPatterns = new EnemyMovementPatterns(pattern, pixelToGoWidth, pixelToGoHeight, direction);
        targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition(position));
        currentState = State.SMALL;
    }

    @Override
    public String toString() {
        return "Spion: " + position;
    }


    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition(position));
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void updateStatus(){
        switch (currentState) {
            case SMALL, BIG:
                if (gameView.timer(TIMER_CHANGE_STATE, this)) {
                    switchToNextState();
                }
                break;
        }
    }

    private void switchToNextState() {
        int nextState = (currentState.ordinal() + 1) % Spy.State.values().length;
        currentState = Spy.State.values()[nextState];
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(currentState.image, position.getX(), position.getY(), size, rotation);
    }
}