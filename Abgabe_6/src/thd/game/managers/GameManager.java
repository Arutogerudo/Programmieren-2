package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

/**
 * Class to manage the game.
 */
class GameManager extends GamePlayManager {

    GameManager(GameView gameView) {
        super(gameView);
        ghost = new Ghost(gameView, this);
        spawnGameObject(ghost);
        spy = new Spy(gameView, this);
        spawnGameObject(spy);
        radioactivePack = new RadioactivePack(gameView, this);
        spawnGameObject(radioactivePack);
        accordion = new Accordion(gameView, this);
        spawnGameObject(accordion);
        jet = new Jet(gameView, this);
        spawnGameObject(jet);
        rocket = new Rocket(gameView, this);
        spawnGameObject(rocket);
        startRamp = new StartRamp(gameView, this);
        spawnGameObject(startRamp);
        blackWallRocketPad = new BlackWallRocketPad(gameView, this);
        spawnGameObject(blackWallRocketPad);
        woodenWall = new WoodenWall(gameView, this);
        spawnGameObject(woodenWall);
        wallRocketPad = new WallRocketPad(gameView, this);
        spawnGameObject(wallRocketPad);
        CollidingGameObject[] collidingObjects = new CollidingGameObject[]{blackWallRocketPad, woodenWall, wallRocketPad};
        tank = new Tank(gameView, this, collidingObjects);
        spawnGameObject(tank);
        scoreboard = new Scoreboard(gameView, this);
        spawnGameObject(scoreboard);
    }

    @Override
    protected void gameLoopUpdate() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        super.gameLoopUpdate();
        gameManagement();
    }

    private void gameManagement() {
    }
}
