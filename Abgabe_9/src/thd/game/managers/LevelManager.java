package thd.game.managers;
import thd.game.level.*;
import thd.game.utilities.GameView;

import java.util.List;

class LevelManager extends GameWorldManager {
    private List<Level> levels;
    private static final int LIVES = 3;
    protected LevelManager(GameView gameView) {
        super(gameView);
        initializeGame();
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
        initializeGameObjects();
    }

    private void initializeGameObjects() {
        /*Die Methode initializeGameObjects() soll in Zukunft dazu genutzt werden um Spielelemente an ein
        neues Level anzupassen, z.B.
                o Anpassungen für das Level am Hintergrund machen.
                o Die Lebensanzeige aktualisieren.
                o Den Punktestand aus dem vorherigen Level übernehmen.
                o Einen Countdown neu starten.
        ➔ Kopieren Sie diesen Text als Kommentar zur Erinnerung für später in diese Methode*/
    }

    protected boolean hasNextLevel() {
        int lastIndex = levels.size() - 1;
        return level != levels.get(lastIndex);
    }

    protected void switchToNextLevel() {
        if (hasNextLevel()) {
            level = levels.get(levels.indexOf(level) + 1);
        } else {
            throw new NoMoreLevelsAvailableException("You mastered all levels!");
        }
        shiftCounterPerLevel = 0;
    }

    protected void initializeGame() {
        levels = List.of(new Level1(), new Level2(), new Level3());
        level = levels.get(0);
        lives = LIVES;
        points = 0;
    }
}
