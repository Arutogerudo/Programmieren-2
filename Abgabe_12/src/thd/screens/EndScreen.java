package thd.screens;

import thd.game.utilities.GameView;

/**
 * The end screen of the game.
 */
public class EndScreen {
    private final GameView gameView;

    /**
     * Creates the end screen.
     *
     * @param gameView the game view
     */
    public EndScreen(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Shows the end screen with the reached score.
     *
     * @param score the score of the player
     */
    public void showEndScreen(int score) {
        String message = String.format("""
                Congratulations!\s
                You earned %d points.\s
                \s
                You have braved the perils that the evil empire has sent before.\s
                You battle with heart and courage and succeeded.\s
                The free world is in your debt for now the people may live in peace.\s
                \s
                But alas your mission was not a total success.\s
                For the dark dictator has escaped justice and fled to the underground.\s
                The threat still exists so be prepared to battle another day.\s
                """, score);
        gameView.showEndScreen(message);
    }
}