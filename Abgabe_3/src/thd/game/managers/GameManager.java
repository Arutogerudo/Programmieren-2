package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.FollowerBall;
import thd.gameobjects.movable.Ghost;
import thd.gameobjects.movable.RandomBall;
import thd.gameobjects.movable.Spy;
import thd.gameobjects.unmovable.RadioactivePack;

/**
 * Class to manage the gameobjects.
 */
class GameManager {
    private final Ghost ghost;
    private final Spy spy;
    private final RadioactivePack radioactivePack;
    private final RandomBall randomBall;
    private final FollowerBall followerBall;

    GameManager(GameView gameView){
        ghost = new Ghost(gameView);
        spy = new Spy(gameView);
        radioactivePack = new RadioactivePack(gameView);
        randomBall = new RandomBall(gameView);
        followerBall = new FollowerBall(gameView, randomBall);
    }

    void gameLoopUpdate() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        radioactivePack.addToCanvas();
        ghost.addToCanvas();
        ghost.updatePosition();
        spy.addToCanvas();
        spy.updatePosition();
        randomBall.addToCanvas();
        randomBall.updatePosition();
        followerBall.addToCanvas();
        followerBall.updatePosition();
    }
}
