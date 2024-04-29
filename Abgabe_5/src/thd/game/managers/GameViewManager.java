package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.unmovable.BlackWallRocketPadBlockImages;

import java.awt.*;

/**
 * Class to manage the gameobjects in the gameview.
 */
public class GameViewManager extends GameView {
    private GameManager gameManager;
    private static final double X_COORDINATE_BLACK_WALL = 0;
    private static final double Y_COORDINATE_BLACK_WALL = 0;
    private static final int BLOCK_SIZE = 3;
    private static final double ROTAION = 0;
    @Override
    public void initialize() {
        gameManager = new GameManager(this);
        setWindowTitle("Tiger Tank");
        setStatusText("Lara Altgeld - Java Programmierung SS 2024");
        setWindowIcon("tigertank.png");
        changeBackgroundColor(Color.GRAY);
    }
    @Override
    public void gameLoop() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        gameManager.gameLoopUpdate();
        addBlockImageToCanvas(BlackWallRocketPadBlockImages.BLACK_WALL, X_COORDINATE_BLACK_WALL, Y_COORDINATE_BLACK_WALL, BLOCK_SIZE, ROTAION);
    }
}
