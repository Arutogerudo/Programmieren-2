package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Woodenwall (game field).
 */
public class WoodenWall extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject {
    private static final String WOODEN_WALL = "LLOOOOOO\nLLOOLLOO\nLLLLOOOO\nLLOOOOLL\nLLLLOOOO\nLLOOOOOO\nLLLLLLOO\nLLOOOOOO";
    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public WoodenWall(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 3.2;
        rotation = 0;
        singleWidth = widthOfBlockImage(WOODEN_WALL) * size;
        width = singleWidth * 19;
        height = heightOfBlockImage(WOODEN_WALL) * size;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }
    @Override
    public String toString() {
        return "Wooden Wall: " + position;
    }

    @Override
    public void addToCanvas() {
        for (int orderplace = 0; orderplace < 19; orderplace++) {
            gameView.addBlockImageToCanvas(WOODEN_WALL, position.getX() + orderplace * singleWidth, position.getY(), size, rotation);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}