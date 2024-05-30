package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

class Shot extends CollidingGameObject implements ShiftableGameObject {
    private static final int POINTS_PER_KILL = 10;
    private static final int SIZE_PIXEL = 15;
    private final String direction;
    private String image;
    Shot(GameView gameView, GamePlayManager gamePlayManager, double xCoordinate, double yCoordinate, String direction) {
        super(gameView, gamePlayManager);
        size = SIZE;
        position.updateCoordinates(xCoordinate, yCoordinate);
        speedInPixel = 3;
        rotation = 0;
        width = SIZE_PIXEL;
        height = SIZE_PIXEL;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 1;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Shot: " + position;
    }


    @Override
    public void updatePosition() {
        switch (direction) {
            case "left":
                position.left(speedInPixel);
                break;
            case "right":
                position.right(speedInPixel);
                break;
            case "up":
                position.up(speedInPixel);
                break;
            case "down":
                position.down(speedInPixel);
                break;
        }
    }

    @Override
    public void updateStatus() {
        if (position.getX() < 0 || position.getX() > GAMEVIEW_WIDTH || position.getY() < 0 || position.getY() > GAMEVIEW_HEIGHT) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
        switch (direction) {
            case "left":
                image = "shotleft.png";
                break;
            case "right":
                image = "shotright.png";
                break;
            case "up":
                image = "shotup.png";
                break;
            case "down":
                image = "shotdown.png";
                break;
        }
        gameView.addImageToCanvas(image, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Accordion || other instanceof Ghost ||other instanceof Spy) {
            gamePlayManager.addPoints(POINTS_PER_KILL);
            gameView.playSound("hit.wav", false);
            gamePlayManager.destroyGameObject(this);
        } else if (!(other instanceof Tank)) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return false;
    }
}
