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
        size = 40;
        distanceToBackground = 20;
    }

    /**
     * Returns, if currently a message is shown.
     *
     * @return True, if currently a message is shown.
     */
    private boolean isMessageShown() {
        return messageShown;
    }

    /**
     * Shows the given message until {@link #stopShowing()} is called. Until then all further calls of this method are ignored.
     *
     * @param message Message to be shown.
     */
    private void showMessage(String message) {
        showMessage(message, 10_000);
    }

    /**
     * Shows the given message for the given number of seconds or until {@link #stopShowing()} is called. Until then
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

    /**
     * The overlay will stop to show the current message.
     */
    private void stopShowing() {
        messageShown = false;
    }

    private void centerMessage() {
        String[] lines = message.split("\\R");
        int longestLine = Arrays.stream(lines).mapToInt(String::length).max().orElse(1);
        double textHeight = lines.length * size;
        double textWidth = longestLine * size * 0.60;
        position.updateCoordinates((GameView.WIDTH - textWidth) / 2d, (GameView.HEIGHT - textHeight) / 2d);
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
}