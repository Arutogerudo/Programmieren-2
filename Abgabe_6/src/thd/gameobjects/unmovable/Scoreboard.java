package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * unmovable Gameobject Scoreboard (gives information of game data).
 */
public class Scoreboard extends GameObject {
    private static final double Y_COORDINATE = 600;
    private static final double X_COORDINATE = 0;

    /**
     * Crates a new GameObject.
     *
     * @param gameView        GameView to show the game object on.
     * @param gamePlayManager Game Play Manager
     */
    public Scoreboard(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(0, Y_COORDINATE, GameView.WIDTH, GameView.HEIGHT - Y_COORDINATE, 0.0, true, Color.BLACK);
        gameView.addTextToCanvas("SCORE:", X_COORDINATE, Y_COORDINATE, 25, true, Color.YELLOW, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getPoints(), X_COORDINATE + 120, Y_COORDINATE, 25, true, Color.WHITE, 0);
        gameView.addTextToCanvas("MEN:", X_COORDINATE + GameView.WIDTH / 3.0, Y_COORDINATE, 25, true, Color.GREEN, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getLives(), X_COORDINATE + GameView.WIDTH / 3.0 + 90, Y_COORDINATE, 25, true, Color.WHITE, 0);
        gameView.addTextToCanvas("HIGH SCORE:", X_COORDINATE + GameView.WIDTH * 2 / 3.0, Y_COORDINATE, 25, true, Color.YELLOW, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getHighscore(), X_COORDINATE + GameView.WIDTH * 2 / 3.0 + 190, Y_COORDINATE, 25, true, Color.WHITE, 0);
        gameView.addTextToCanvas("AMMUNITION:", X_COORDINATE + GameView.WIDTH / 8.0, Y_COORDINATE + 30, 25, true, Color.CYAN, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getAmmunition(), X_COORDINATE + GameView.WIDTH / 8.0 + 200, Y_COORDINATE + 30, 25, true, Color.WHITE, 0);
        gameView.addTextToCanvas("PACKS:", X_COORDINATE + GameView.WIDTH / 2.0, Y_COORDINATE + 30, 25, true, Color.CYAN, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getPacks(), X_COORDINATE + GameView.WIDTH / 2.0 + 110, Y_COORDINATE + 30, 25, true, Color.WHITE, 0);
    }

}
