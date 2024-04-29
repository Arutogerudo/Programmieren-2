package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

/**
 * Class to manage the game.
 */
class GameManager extends UserControlledGameObjectPool {
    private final GameObjectManager gameObjectManager;

    GameManager(GameView gameView){
        super(gameView);
        this.gameObjectManager = new GameObjectManager();
        ghost = new Ghost(gameView);
        gameObjectManager.add(ghost);
        spy = new Spy(gameView);
        gameObjectManager.add(spy);
        radioactivePack = new RadioactivePack(gameView);
        gameObjectManager.add(radioactivePack);
        tank = new Tank(gameView);
        gameObjectManager.add(tank);
        accordion = new Accordion(gameView);
        gameObjectManager.add(accordion);
        jet = new Jet(gameView);
        gameObjectManager.add(jet);
        rocket = new Rocket(gameView);
        gameObjectManager.add(rocket);
        startRamp = new StartRamp(gameView);
        gameObjectManager.add(startRamp);
        projectile = new Projectile(gameView);
        gameObjectManager.add(projectile);
    }
    @Override
    protected void gameLoopUpdate() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        super.gameLoopUpdate();
        gameObjectManager.gameLoopUpdate();
    }
}
