package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

class Shot extends CollidingGameObject implements ShiftableGameObject {
    private static final int POINTS_PER_KILL = 10;
    Shot(GameView gameView, GamePlayManager gamePlayManager, double xCoordinate, double yCoordinate) {
        super(gameView, gamePlayManager);
        size = 0.1;
        position.updateCoordinates(xCoordinate, yCoordinate);
        speedInPixel = 2;
        rotation = 0;
        width = 25;
        height = 14;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 1;
    }

    @Override
    public String toString() {
        return "Shot: " + position;
    }


    @Override
    public void updatePosition() {
        position.right(speedInPixel);
    }

    @Override
    public void updateStatus() {
        if (position.getX() < 0 || position.getX() > GAMEVIEW_WIDTH || position.getY() < 0 || position.getY() > GAMEVIEW_HEIGHT) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("shotright.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Accordion || other instanceof Ghost ||other instanceof Spy) {
            gamePlayManager.addPoints(POINTS_PER_KILL);
            gamePlayManager.destroyGameObject(this);
        }
    }
}
