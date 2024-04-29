package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * Manages the gameplay, e.g. dynamically creation of gameObjects / general spawning and destroying of gameobjects.
 */
public class GamePlayManager extends WorldShiftManager {
    private final GameObjectManager gameObjectManager;
    private static final int LIVES = 3;
    private static final int AMMUNITION = 5;
    protected int points;
    protected int lives;
    private int ammunition;
    private int packs;
    private final int highscore;

    protected GamePlayManager(GameView gameView) {
        super(gameView);
        gameObjectManager = new GameObjectManager();
        lives = LIVES;
        points = 0;
        ammunition = AMMUNITION;
        packs = 0;
        highscore = 0;
    }

    /**
     * Method to spawn a given gameobject in the GameView.
     *
     * @param gameObject gameobject to be created
     */
    @Override
    public void spawnGameObject(GameObject gameObject) {
        super.spawnGameObject(gameObject);
        gameObjectManager.add(gameObject);
    }

    /**
     * Method to destroy a given gameobject in the Gameview.
     *
     * @param gameObject gameobject to be destroyed
     */
    @Override
    public void destroyGameObject(GameObject gameObject) {
        super.destroyGameObject(gameObject);
        gameObjectManager.remove(gameObject);
    }

    @Override
    protected void destroyAllGameObjects() {
        super.destroyAllGameObjects();
        gameObjectManager.removeAll();
    }

    @Override
    protected void gameLoopUpdate() {
        super.gameLoopUpdate();
        gameObjectManager.gameLoopUpdate();
        gamePlayManagement();
    }

    private void gamePlayManagement() {
    }

    /**
     * Method that decreases life by 1 if Tank collides with enemies or field objects.
     */
    public void lifeLost(){
        lives -= 1;
    }

    /**
     * Method that decreases ammunition by 1 if Tank shoots.
     */
    public void shot(){
        ammunition -= 1;
    }
    /**
     * Method that adds Points to score.
     *
     * @param points to add
     */
    public void addPoints(int points){
        this.points += points;
    }
    /**
     * Method that increases packs by 1 if the tank collects a radioactive pack.
     */
    public void collectPack(){
        packs += 1;
    }
    /**
     * Method that increases ammunition by 3 if Tank collects a radioactive pack.
     */
    public void refillAmmunition(){
        ammunition += 3;
    }
    /**
     * Method that returns the remaining ammunition.
     *
     * @return remaining ammunition
     */
    public int getAmmunition(){
        return ammunition;
    }
    /**
     * Method that returns the current score.
     *
     * @return current score
     */
    public int getPoints(){
        return points;
    }
    /**
     * Method that returns the remaining lives.
     *
     * @return remaining lives
     */
    public int getLives(){
        return lives;
    }
    /**
     * Method that returns the amount of already collected packs.
     *
     * @return collected packs
     */
    public int getPacks(){
        return packs;
    }
    /**
     * Method that returns the currrent highscore.
     *
     * @return current highscore
     */
    public int getHighscore(){
        return highscore;
    }
}
