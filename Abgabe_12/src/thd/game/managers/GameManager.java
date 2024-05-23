package thd.game.managers;

import thd.game.level.Difficulty;
import thd.game.level.Level;
import thd.game.level.Level3;
import thd.game.utilities.FileAccess;
import thd.game.utilities.GameView;
import thd.screens.EndScreen;
import thd.screens.StartScreen;

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
            if (!overlay.isMessageShown()) {
                overlay.showMessage("Game over");
                gameView.stopAllSounds();
            } else if (gameView.timer(2000, this)) {
                overlay.stopShowing();
            }
            highscore = getPoints();
            EndScreen endScreen = new EndScreen(gameView);
            endScreen.showEndScreen(points);
            startNewGame();
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
        //tank.changeMovingAbility();
        gameView.changeBackgroundColor(level.backgroundColor);
        overlay.showMessage(level.name, 2);
    }

    @Override
    protected void initializeGame() {
        super.initializeGame();
        initializeLevel();
    }
    void startNewGame() {
        Difficulty difficulty = FileAccess.readDifficultyFromDisc();
        StartScreen startScreen = new StartScreen(gameView);
        startScreen.showStartScreenWithPreselectedDifficulty(difficulty);
        difficulty = startScreen.getSelectedDifficulty();
        FileAccess.writeDifficultyToDisc(difficulty);
        Level.difficulty = difficulty;
        initializeGame();
        gameView.playSound("background.wav", true);
    }
}
