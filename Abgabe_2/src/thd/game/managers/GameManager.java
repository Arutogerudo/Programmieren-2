package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.Ghost;
import thd.gameobjects.movable.Spy;
import thd.gameobjects.unmovable.RadioactivePack;

/**
 * Class to manage the gameobjects.
 */
class GameManager {
    private final Ghost ghost;
    private final Spy spy;
    private final RadioactivePack radioactivePack;

    GameManager(GameView gameView){
        ghost = new Ghost(gameView);
        spy = new Spy(gameView);
        radioactivePack = new RadioactivePack(gameView);
    }

    void gameLoopUpdate() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        ghost.updatePosition();
        ghost.addToCanvas();
        spy.updatePosition();
        spy.addToCanvas();
        radioactivePack.addToCanvas();
    }
}
