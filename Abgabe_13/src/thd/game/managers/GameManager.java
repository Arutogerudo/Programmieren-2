package thd.game.managers;

import thd.game.level.Difficulty;
import thd.game.level.Level;
import thd.game.level.Level3;
import thd.game.level.Level4;
import thd.game.utilities.FileAccess;
import thd.game.utilities.GameView;
import thd.screens.EndScreen;
import thd.screens.EndScreenLoose;
import thd.screens.StartScreen;

import java.awt.*;

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
            if (getLives() == 0) {
                EndScreenLoose endScreen = new EndScreenLoose(gameView);
                endScreen.showEndScreen();
            } else {
                if (highscore < getPoints()) {
                    highscore = getPoints();
                    FileAccess.writeHighscoreToDisc(highscore);
                }
                EndScreen endScreen = new EndScreen(gameView);
                endScreen.showEndScreen(points);
            }
            gameView.stopAllSounds();
            startNewGame();
        } else if (endOfLevel()) {
            switchToNextLevel();
            initializeLevel();
        } else if (lifeLost) {
            gameView.changeBackgroundColor(Color.RED);
            if (gameView.timer(1000, this)) {
                initializeLevel();
                lifeLost = false;
                shiftCounterPerLevel = 0;
            }
        }
    }

    private boolean endOfGame() {
        return lives == 0 || (!hasNextLevel() && endOfLevel());
    }

    private boolean endOfLevel() {
        if (level instanceof Level3 || level instanceof Level4) {
            return shiftCounterPerLevel == 3;
        } else {
            return shiftCounterPerLevel == 2;
        }
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
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
        highscore = FileAccess.readHighscoreFromDisc();
        StartScreen startScreen = new StartScreen(gameView);
        startScreen.showStartScreenWithPreselectedDifficulty(difficulty);
        difficulty = startScreen.getSelectedDifficulty();
        FileAccess.writeDifficultyToDisc(difficulty);
        Level.difficulty = difficulty;
        lifeLost = false;
        initializeGame();
        if (Level.difficulty == Difficulty.EASY) {
            lives = 5;
        }
        gameView.playSound("background.wav", true);
    }
}
