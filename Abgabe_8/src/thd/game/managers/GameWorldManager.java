package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameobjects.movable.Accordion;
import thd.gameobjects.movable.Ghost;
import thd.gameobjects.movable.Spy;
import thd.gameobjects.movable.Tank;
import thd.gameobjects.unmovable.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

class GameWorldManager extends GamePlayManager {
    private static final int WORLD_SCALLING_FACTOR = 25; //51:29
    private LinkedList<CollidingGameObject> collidingObjects;
    private final List<GameObject> activatableGameObjects;

    protected GameWorldManager(GameView gameView) {
        super(gameView);
        tank = new Tank(gameView, this);
        scoreboard = new Scoreboard(gameView, this);
        collidingObjects = new LinkedList<>();
        this.activatableGameObjects = new LinkedList<>();
    }

    private void spawnGameObjects() {
        spawnGameObject(scoreboard);
        spawnGameObject(tank);
    }

    private void spawnGameObjectsFromWorldString() {
        String[] lines = level.world.split("\\R");
        for (int scalledHeight = 0; scalledHeight < lines.length; scalledHeight++) {
            for (int scalledWidth = 0; scalledWidth < lines[scalledHeight].length(); scalledWidth++) {
                double x = (scalledWidth - level.worldOffsetColumns) * WORLD_SCALLING_FACTOR;
                double y = (scalledHeight - level.worldOffsetLines) * WORLD_SCALLING_FACTOR;
                char character = lines[scalledHeight].charAt(scalledWidth);
                if (character == 'B') {
                    Bush bush = new Bush(gameView, this);
                    bush.getPosition().updateCoordinates(x, y);
                    collidingObjects.add(bush);
                    spawnGameObject(bush);
                } else if (character == 'G') {
                    spawnGameObject(new Ghost(gameView, this, new Position(x, y), 250, 250, "down", "quadratic"));
                } else if (character == 'g') {
                    spawnGameObject(new Ghost(gameView, this, new Position(x, y), 700, 250, "right", "triangular"));
                } else if (character == 'p') {
                    spawnGameObject(new Ghost(gameView, this, new Position(x, y), 300, 0, "down", "linear"));
                } else if (character == 'A') {
                    spawnGameObject(new Accordion(gameView, this, new Position(x, y), 250, 250, "up", "quadratic"));
                } else if (character == 'a') {
                    spawnGameObject(new Accordion(gameView, this, new Position(x, y), 700, 250, "right", "triangular"));
                } else if (character == 'o') {
                    spawnGameObject(new Accordion(gameView, this, new Position(x, y), 300, 0, "right", "linear"));
                } else if (character == 'S') {
                    Spy spy = new Spy(gameView, this, new Position(x, y), 250, 100, "right", "quadratic");
                    spawnGameObject(spy);
                } else if (character == 's') {
                    Spy spy = new Spy(gameView, this, new Position(x, y), 700, 250, "right", "triangular");
                    spawnGameObject(spy);
                } else if (character == 'z') {
                    Spy spy = new Spy(gameView, this, new Position(x, y), 0, 300, "up", "linear");
                    spawnGameObject(spy);
                } else if (character == 'W') {
                    WoodenWall woodenWall = new WoodenWall(gameView, this);
                    collidingObjects.add(woodenWall);
                    woodenWall.getPosition().updateCoordinates(x, y);
                    spawnGameObject(woodenWall);
                } else if (character == 'R') {
                    RadioactivePack radioactivePack = new RadioactivePack(gameView, this);
                    radioactivePack.getPosition().updateCoordinates(x, y);
                    spawnGameObject(radioactivePack);
                } else if (character == 'b') {
                    WallRocketPad wallRocketPad = new WallRocketPad(gameView, this, "left");
                    collidingObjects.add(wallRocketPad);
                    wallRocketPad.getPosition().updateCoordinates(x, y);
                    spawnGameObject(wallRocketPad);
                } else if (character == 'C') {
                    WallRocketPad wallRocketPad = new WallRocketPad(gameView, this, "right");
                    collidingObjects.add(wallRocketPad);
                    wallRocketPad.getPosition().updateCoordinates(x, y);
                    spawnGameObject(wallRocketPad);
                } else if (character == 'c') {
                    WallRocketPad wallRocketPad = new WallRocketPad(gameView, this, "top");
                    collidingObjects.add(wallRocketPad);
                    wallRocketPad.getPosition().updateCoordinates(x, y);
                    spawnGameObject(wallRocketPad);
                } else if (character == 'D') {
                    WallRocketPad wallRocketPad = new WallRocketPad(gameView, this, "bottom");
                    collidingObjects.add(wallRocketPad);
                    wallRocketPad.getPosition().updateCoordinates(x, y);
                    spawnGameObject(wallRocketPad);
                } else if (character == 'd') {
                    Rocket rocket = new Rocket(gameView, this);
                    collidingObjects.add(rocket);
                    rocket.getPosition().updateCoordinates(x, y);
                    spawnGameObject(rocket);
                } else if (character == 'E') {
                    Chain chain = new Chain(gameView, this);
                    collidingObjects.add(chain);
                    chain.getPosition().updateCoordinates(x, y);
                    spawnGameObject(chain);
                } else if (character == 'e') {
                    BlackWallRocketPad blackWallRocketPad = new BlackWallRocketPad(gameView, this);
                    collidingObjects.add(blackWallRocketPad);
                    blackWallRocketPad.getPosition().updateCoordinates(x, y);
                    spawnGameObject(blackWallRocketPad);
                } else if (character == 'F') {
                    StartRamp startRamp = new StartRamp(gameView, this);
                    collidingObjects.add(startRamp);
                    startRamp.getPosition().updateCoordinates(x, y);
                    spawnGameObject(startRamp);
                } else if (character == 'f') {
                    Pilons pilons = new Pilons(gameView, this);
                    collidingObjects.add(pilons);
                    pilons.getPosition().updateCoordinates(x, y);
                    spawnGameObject(pilons);
                }  else if (character == 'H') {
                    StoneWall stoneWall = new StoneWall(gameView, this);
                    collidingObjects.add(stoneWall);
                    stoneWall.getPosition().updateCoordinates(x, y);
                    spawnGameObject(stoneWall);
                }  else if (character == 'h') {
                    CentrelineRunway centrelineRunway = new CentrelineRunway(gameView, this);
                    collidingObjects.add(centrelineRunway);
                    centrelineRunway.getPosition().updateCoordinates(x, y);
                    spawnGameObject(centrelineRunway);
                }  else if (character == 'I') {
                    Fence fence = new Fence(gameView, this);
                    collidingObjects.add(fence);
                    fence.getPosition().updateCoordinates(x, y);
                    spawnGameObject(fence);
                }  else if (character == 'i') {
                    Jet jet = new Jet(gameView, this);
                    collidingObjects.add(jet);
                    jet.getPosition().updateCoordinates(x, y);
                    spawnGameObject(jet);
                }  else if (character == 'J') {
                    Parkbox parkbox = new Parkbox(gameView, this);
                    collidingObjects.add(parkbox);
                    parkbox.getPosition().updateCoordinates(x, y);
                    spawnGameObject(parkbox);
                }  else if (character == 'j') {
                    SafetyBox safetyBox = new SafetyBox(gameView, this);
                    collidingObjects.add(safetyBox);
                    safetyBox.getPosition().updateCoordinates(x, y);
                    spawnGameObject(safetyBox);
                }  else if (character == 'K') {
                    WallAirport wallAirport = new WallAirport(gameView, this);
                    collidingObjects.add(wallAirport);
                    wallAirport.getPosition().updateCoordinates(x, y);
                    spawnGameObject(wallAirport);
                }  else if (character == 'k') {
                    WallRunway wallRunway = new WallRunway(gameView, this);
                    collidingObjects.add(wallRunway);
                    wallRunway.getPosition().updateCoordinates(x, y);
                    spawnGameObject(wallRunway);
                }
            }
        }
        tank.setCollidingGameObjectsForPathDecision(collidingObjects);
    }
    // d - rocket, E - chain rocket pad, e - black wall rocket pad, F - StartRamp, f - pilons
    private void addActivatableGameObject(GameObject gameObject) {
        activatableGameObjects.add(gameObject);
        addToShiftableGameObjectsIfShiftable(gameObject);
    }

    @Override
    protected void gameLoopUpdate() {
        super.gameLoopUpdate();
        activateGameObjects();
    }

    private void activateGameObjects() {
        ListIterator<GameObject> iterator = activatableGameObjects.listIterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            if (gameObject instanceof Accordion accordion) {
                if (accordion.tryToActivate(tank)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Ghost ghost) {
                if (ghost.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Spy spy) {
                if (spy.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof BlackWallRocketPad blackWallRocketPad) {
                if (blackWallRocketPad.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Bush bush) {
                if (bush.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Jet jet) {
                if (jet.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof RadioactivePack radioactivePack) {
                if (radioactivePack.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Rocket rocket) {
                if (rocket.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof StartRamp startRamp) {
                if (startRamp.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRocketPad wallRocketPadDown) {
                if (wallRocketPadDown.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof WoodenWall woodenWall) {
                if (woodenWall.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            }
        }
    }

    protected void initializeLevel() {
        activatableGameObjects.clear();
        destroyAllGameObjects();
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
        clearListsForPathDecisionsInGameObjects();
    }

    private void clearListsForPathDecisionsInGameObjects() {
        tank.setCollidingGameObjectsForPathDecision(new LinkedList<>());
    }
}
