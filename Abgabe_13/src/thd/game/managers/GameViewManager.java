package thd.game.managers;

import thd.game.utilities.GameView;

import java.awt.*;

/**
 * Class to manage the gameobjects in the gameview.
 */
public class GameViewManager extends GameView {
    private static final Color BACKGROUND_COLOR = new Color(168, 112, 47);
    private GameManager gameManager;
    @Override
    public void initialize() {
        gameManager = new GameManager(this);
        setWindowTitle("Tiger Tank");
        setStatusText("Lara Altgeld - Java Programmierung SS 2024");
        setWindowIcon("tigertank.png");
        changeBackgroundColor(new Color(BACKGROUND_COLOR.getRGB()));
        gameManager.startNewGame();
        showStatistic(false);
    }
    @Override
    public void gameLoop() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        gameManager.gameLoopUpdate();
    }
}
