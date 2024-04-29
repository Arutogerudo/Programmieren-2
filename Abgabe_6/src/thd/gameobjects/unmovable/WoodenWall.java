package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;

/**
 * unmovable Gameobject Woodenwall (game field).
 */
public class WoodenWall extends CollidingGameObject {
    private static final String WOODEN_WALL = "LLOOOOOO\nLLOOLLOO\nLLLLOOOO\nLLOOOOLL\nLLLLOOOO\nLLOOOOOO\nLLLLLLOO\nLLOOOOOO";
    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public WoodenWall(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 3;
        position.updateCoordinates(50, 400);
        rotation = 0;
        width = widthOfBlockImage(WOODEN_WALL) * size;
        height = heightOfBlockImage(WOODEN_WALL) * size;
        hitBoxOffsets(0, 0, 0, 0);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(WOODEN_WALL, position.getX(), position.getY(), size, rotation);
    }
}
