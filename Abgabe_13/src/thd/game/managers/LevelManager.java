package thd.game.managers;
import thd.game.level.*;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.Enemy;

import java.util.List;

class LevelManager extends GameWorldManager {
    private List<Level> levels;
    private static final int LIVES = 3;
    LevelManager(GameView gameView) {
        super(gameView);
        initializeGame();
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
        initializeGameObjects();
    }

    private void initializeGameObjects() {
        switch (Level.difficulty) {
            case EASY -> {
                for (GameObject gameObject : activatableGameObjects) {
                    if (gameObject instanceof Enemy) {
                        ((Enemy) gameObject).updateSpeedInPixel(2);
                    }
                }
            }
            case STANDARD -> {
                for (GameObject gameObject : activatableGameObjects) {
                    if (gameObject instanceof Enemy) {
                        ((Enemy) gameObject).updateSpeedInPixel(3);
                    }
                }
            }
        }
    }

    boolean hasNextLevel() {
        int lastIndex = levels.size() - 1;
        return level != levels.get(lastIndex);
    }

    void switchToNextLevel() {
        if (hasNextLevel()) {
            level = levels.get(levels.indexOf(level) + 1);
        } else {
            throw new NoMoreLevelsAvailableException("You mastered all levels!");
        }
        shiftCounterPerLevel = 0;
    }

    protected void initializeGame() {
        levels = List.of(new Level1(), new Level2(), new Level3(), new Level4(), new Level5());
        level = levels.get(0);
        lives = LIVES;
        points = 0;
    }
}
