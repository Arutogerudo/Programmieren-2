package thd.game.managers;

import thd.game.level.Level3;
import thd.game.utilities.GameView;

/**
 * Class to manage the game.
 */
class GameManager extends LevelManager {

    GameManager(GameView gameView) {
        super(gameView);
    }

    @Override
    protected void gameLoopUpdate() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        super.gameLoopUpdate();
        gameManagement();
    }

    private void gameManagement() {
        if (endOfGame()) {
            initializeGame();
        } else if (endOfLevel()) {
            switchToNextLevel();
            initializeLevel();
        }
    }

    private boolean endOfGame() {
        return lives == 0 || (!hasNextLevel() && endOfLevel());
    }

    private boolean endOfLevel() {
        if (level instanceof Level3) {
            return shiftCounterPerLevel == 3;
        } else {
            return shiftCounterPerLevel == 2;
        }
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
        gameView.changeBackgroundColor(level.backgroundColor);
    }

    @Override
    protected void initializeGame() {
        super.initializeGame();
        initializeLevel();
    }
}
