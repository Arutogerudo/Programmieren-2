package thd.game.managers;

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
        return gameView.timer(10000, 1);
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
