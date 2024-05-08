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
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
    }
}
