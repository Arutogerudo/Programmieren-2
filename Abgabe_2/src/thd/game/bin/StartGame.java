package thd.game.bin;

import thd.game.managers.GameViewManager;

/**
 * Class to start the game.
 */
public class StartGame {
    /**
     * Main method to start the game.
     *
     * @param args given arguments in string array
     */
    public static void main(String[] args) {
        GameViewManager gameViewManager = new GameViewManager();
        gameViewManager.startGame();
    }
}

