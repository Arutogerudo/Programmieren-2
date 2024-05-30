package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * movable Gameobject Pilons (game field).
 */
public class Pilons extends CollidingGameObject implements ShiftableGameObject {

    private static final double SIZE1 = 0.12;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 45;

    /**
     * Creates a Rocket in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Pilons(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE1;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString(){
        return "Pilons: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("pilonsrocketpad.png", position.getX(), position.getY(), size, rotation);
    }

}
