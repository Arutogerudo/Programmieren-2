package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.Ghost;
import thd.gameobjects.movable.Square;

/**
 * Manages the gameplay, e.g. dynamically creation of gameObjects / general spawning and destroying of gameobjects.
 */
public class GamePlayManager extends UserControlledGameObjectPool {
    private final GameObjectManager gameObjectManager;
    private int currentNumberOfVisibleSquares;
    private int currentNumberOfVisibleGhosts;
    private static final int MAXIMUM_NUMBER_OF_VISIBLE_SQUARES = 5;
    private static final int MAXIMUM_NUMBER_OF_VISIBLE_GHOSTS = 11;

    protected GamePlayManager(GameView gameView) {
        super(gameView);
        gameObjectManager = new GameObjectManager();
        currentNumberOfVisibleSquares = 0;
        currentNumberOfVisibleGhosts = 0;
    }

    /**
     * Method to spawn a given gameobject in the GameView.
     *
     * @param gameObject gameobject to be created
     */
    public void spawnGameObject(GameObject gameObject) {
        gameObjectManager.add(gameObject);
    }

    /**
     * Method to destroy a given gameobject in the Gameview.
     *
     * @param gameObject gameobject to be destroyed
     */
    public void destroyGameObject(GameObject gameObject) {
        gameObjectManager.remove(gameObject);
    }

    protected void destroyAllGameObjects() {
        gameObjectManager.removeAll();
    }

    @Override
    protected void gameLoopUpdate() {
        super.gameLoopUpdate();
        gameObjectManager.gameLoopUpdate();
        gamePlayManagement();
    }

    private void gamePlayManagement() {
        if (gameView.timer(1000, this)) {
            currentNumberOfVisibleSquares++;
            if (currentNumberOfVisibleSquares <= MAXIMUM_NUMBER_OF_VISIBLE_SQUARES) {
                spawnGameObject(new Square(gameView, this));
            }
            currentNumberOfVisibleGhosts++;
            if (currentNumberOfVisibleGhosts <= MAXIMUM_NUMBER_OF_VISIBLE_GHOSTS) {
                spawnGameObject(new Ghost(gameView, this));
            }
        }
    }
}
