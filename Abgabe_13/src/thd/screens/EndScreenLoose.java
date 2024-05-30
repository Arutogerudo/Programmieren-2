package thd.screens;

import thd.game.utilities.GameView;

/**
 * The loosing end screen of the game.
 */
public class EndScreenLoose {
    private final GameView gameView;

    /**
     * Creates the end screen when you have lost all lives.
     *
     * @param gameView the game view
     */
    public EndScreenLoose(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Shows the loosing end screen.
     */
    public void showEndScreen() {
        String message = """
                Game over!
                \s
                You have failed in your mission to save the world
                and lost all your lifes.
                The evil Empire has won and the world is finally in shackles.
                There will be no peace because you didn't fight hard enough!
                \s
                But there is one last chance, your colleague has found a potion
                from the evil dictator to revive you.
                Take the chance and free the world.
                """;
        gameView.showEndScreen(message);
    }
}