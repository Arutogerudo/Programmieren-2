package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

/**
 * Class to manage the game.
 */
class GameManager extends GamePlayManager {

    GameManager(GameView gameView){
        super(gameView);
        spy = new Spy(gameView, this);
        spawnGameObject(spy);
        radioactivePack = new RadioactivePack(gameView, this);
        spawnGameObject(radioactivePack);
        tank = new Tank(gameView, this);
        spawnGameObject(tank);
        accordion = new Accordion(gameView, this);
        spawnGameObject(accordion);
        jet = new Jet(gameView, this);
        spawnGameObject(jet);
        rocket = new Rocket(gameView, this);
        spawnGameObject(rocket);
        startRamp = new StartRamp(gameView, this);
        spawnGameObject(startRamp);
    }
    @Override
    protected void gameLoopUpdate() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        super.gameLoopUpdate();
        gameManagement();
    }

    private void gameManagement() {
    }
}
