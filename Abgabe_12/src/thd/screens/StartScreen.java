package thd.screens;

import thd.game.level.Difficulty;
import thd.game.utilities.GameView;

/**
 * The start screen of the game.
 */
public class StartScreen {
    private final GameView gameView;
    private Difficulty selectedDifficulty;

    /**
     * Creates the start screen.
     *
     * @param gameView the game view
     */
    public StartScreen(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Shows the start screen with the preselected difficulty.
     *
     * @param preselectedDifficulty the preselected difficulty
     */
    public void showStartScreenWithPreselectedDifficulty(Difficulty preselectedDifficulty) {
        String title = "Tiger Tank";
        String description = """
                Move the tank through the maze and select every Radioactive Packs
                to complete your mission to free the world!
                If you fail, the world is doomed!
                \s
                You move your tank with the WASD keys and you can shoot with SPACE.
                If you collect a Radioactive Pack you will get new ammunition.
                """;
        boolean difficulty = preselectedDifficulty == Difficulty.EASY;
        if (gameView.showSimpleStartScreen(title, description, difficulty)) {
            selectedDifficulty = Difficulty.EASY;
        } else {
            selectedDifficulty = Difficulty.STANDARD;
        }
    }

    /**
     * Returns the selected difficulty.
     *
     * @return the selected difficulty
     */
    public Difficulty getSelectedDifficulty() {
        return selectedDifficulty;
    }
}
