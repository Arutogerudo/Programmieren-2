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
    private static final int FONT_SIZE = 25;
    private static final int ADD_120 = 120;
    private static final double THIRD_FACTOR = 3.0;
    private static final int ADD_90 = 90;
    private static final int ADD_190 = 190;
    private static final double EIGHT_FACTOR = 8.0;
    private static final int ADD_30 = 30;
    private static final int ADD_200 = 200;
    private static final double HALF_FACTOR = 2.0;
    private static final int ADD_110 = 110;
    private static final int ADD_60 = 60;

    /**
     * Crates a new GameObject.
     *
     * @param gameView        GameView to show the game object on.
     * @param gamePlayManager Game Play Manager
     */
    public Scoreboard(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        distanceToBackground = 3;
    }
    @Override
    public String toString() {
        return "Scoreboard: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(0, Y_COORDINATE, GameView.WIDTH, GameView.HEIGHT - Y_COORDINATE, 0.0, true, Color.BLACK);
        gameView.addTextToCanvas("SCORE:", X_COORDINATE, Y_COORDINATE, FONT_SIZE, true, Color.YELLOW, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getPoints(), X_COORDINATE + ADD_120, Y_COORDINATE, FONT_SIZE, true, Color.WHITE, 0);
        gameView.addTextToCanvas("MEN:", X_COORDINATE + GameView.WIDTH / THIRD_FACTOR, Y_COORDINATE, FONT_SIZE, true, Color.GREEN, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getLives(), X_COORDINATE + GameView.WIDTH / THIRD_FACTOR + ADD_90, Y_COORDINATE, FONT_SIZE, true, Color.WHITE, 0);
        gameView.addTextToCanvas("HIGH SCORE:", X_COORDINATE + GameView.WIDTH * 2 / THIRD_FACTOR, Y_COORDINATE, FONT_SIZE, true, Color.YELLOW, 0);
        gameView.addTextToCanvas("" + gamePlayManager.highscore, X_COORDINATE + GameView.WIDTH * 2 / THIRD_FACTOR + ADD_190, Y_COORDINATE, FONT_SIZE, true, Color.WHITE, 0);
        gameView.addTextToCanvas("AMMUNITION:", X_COORDINATE + GameView.WIDTH / EIGHT_FACTOR, Y_COORDINATE + ADD_30, FONT_SIZE, true, Color.CYAN, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getAmmunition(), X_COORDINATE + GameView.WIDTH / EIGHT_FACTOR + ADD_200, Y_COORDINATE + ADD_30, FONT_SIZE, true, Color.WHITE, 0);
        gameView.addTextToCanvas("PACKS:", X_COORDINATE + GameView.WIDTH / HALF_FACTOR, Y_COORDINATE + ADD_30, FONT_SIZE, true, Color.CYAN, 0);
        gameView.addTextToCanvas("" + gamePlayManager.getPacks(), X_COORDINATE + GameView.WIDTH / HALF_FACTOR + ADD_110, Y_COORDINATE + ADD_30, FONT_SIZE, true, Color.WHITE, 0);
        gameView.addTextToCanvas("LOCATION:", X_COORDINATE, Y_COORDINATE + ADD_60, FONT_SIZE, true, Color.WHITE, 0);
        gameView.addTextToCanvas(" " + gamePlayManager.accessLevelName(), X_COORDINATE + GAMEVIEW_WIDTH / EIGHT_FACTOR, Y_COORDINATE + ADD_60, FONT_SIZE, true, Color.WHITE, 0);
    }

}
