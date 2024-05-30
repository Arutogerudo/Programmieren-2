package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;
import java.util.Arrays;

/**
 * This game object shows an overlay message in the game.
 */
public class Overlay extends GameObject {

    private static final int SIZE_PIXEL = 40;
    private static final int DISTANCE_TO_BACKGROUND = 20;
    private static final double SCALE = 0.60;
    private static final double HALF_FACTOR = 2d;
    private String message;
    private int millisecondsToShow;
    private boolean messageShown;

    /**
     * Crates a new GameObject.
     *
     * @param gameView        Window to show the GameObject on.
     * @param gamePlayManager Controls the gameplay.
     */
    public Overlay(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE_PIXEL;
        distanceToBackground = DISTANCE_TO_BACKGROUND;
    }

    /**
     * Shows the given message for the given number of seconds or until stopShowing() is called. Until then
     * all further calls of this method are ignored.
     *
     * @param message       Message to be shown.
     * @param secondsToShow Seconds to show the message.
     */
    public void showMessage(String message, int secondsToShow) {
        if (!messageShown) {
            this.message = message;
            millisecondsToShow = secondsToShow * 1000;
            centerMessage();
            gameView.resetTimers(this);
            messageShown = true;
        }
    }

    private void centerMessage() {
        String[] lines = message.split("\\R");
        int longestLine = Arrays.stream(lines).mapToInt(String::length).max().orElse(1);
        double textHeight = lines.length * size;
        double textWidth = longestLine * size * SCALE;
        position.updateCoordinates((GameView.WIDTH - textWidth) / HALF_FACTOR, (GameView.HEIGHT - textHeight) / HALF_FACTOR);
    }

    @Override
    public void updateStatus() {
        if (messageShown && gameView.timer(millisecondsToShow, this)) {
            messageShown = false;
        }
    }

    @Override
    public void addToCanvas() {
        if (messageShown) {
            gameView.addTextToCanvas(message, position.getX(), position.getY(), size, false, Color.WHITE, 0);
        }
    }

    @Override
    public boolean tryToActivate(Object info) {
        return false;
    }
}