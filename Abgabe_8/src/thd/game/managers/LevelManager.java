package thd.game.managers;
import thd.game.level.Level1;
import thd.game.utilities.GameView;

public class LevelManager extends GameWorldManager {
    protected LevelManager(GameView gameView) {
        super(gameView);
        level = new Level1();
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
    }
}
