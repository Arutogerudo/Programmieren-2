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
    private final String world; //51:29
    private static final int WORLD_SCALLING_FACTOR = 25;
    private LinkedList<CollidingGameObject> collidingObjects;
    private final List<GameObject> activatableGameObjects;
    private final int worldOffsetColumns;
    private final int worldOffsetLines;

    protected GameWorldManager(GameView gameView) {
        super(gameView);
        // B - Bush, T - Tank, W - Wooden Wall, R - Radioactive Pack
        // G - Ghost quadratic, g - Ghost triangular, p - Ghost linear
        // A - Accordion quadratic, a - Accordion triangular, o - Accordion linear
        // S - Spy quadratic, s - Spy triangular, z - Spy linear

        world = """
                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\s
                BB                                                                                                 BBB\s
                B  T                                           G   a                                               BBB\s
                B                                                                                                   BB\s
                B                                                                                                   BB\s
                B                                                                                                    B\s
                B                                                                                                    B\s
                B                               WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWBBBBBBBBBBBBBB                      B\s
                B                                                                BBBBBBBBBBBBBB                       \s
                B                                                                            p                        \s
                B                                                                                                     \s
                B                                                                                                     \s
                B                                                                                                     \s
                B              BB                                                                                     \s
                B              BB                 BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                                   B\s
                B                                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB                                  B\s
                B                              BBBB  S                            BB  B  B  BB                       B\s
                B                             BBBBB                                 BB BB BB            BB           B\s
                B                             BBBB   R                                                  BB           B\s
                B                            BBBBB                                                                   B\s
                B                             BBBBB                     S                                            B\s
                B   A                        BBBBB                                                                   B\s
                BB                          BBBBB                                                                    B\s
                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s""";
        tank = new Tank(gameView, this);
        scoreboard = new Scoreboard(gameView, this);
        collidingObjects = new LinkedList<>();
        this.activatableGameObjects = new LinkedList<>();
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
    }

    private void spawnGameObjects() {
        spawnGameObject(scoreboard);
        spawnGameObject(tank);
    }

    private void spawnGameObjectsFromWorldString() {
        String[] lines = world.split("\\R");
        for (int scalledHeight = 0; scalledHeight < lines.length; scalledHeight++) {
            for (int scalledWidth = 0; scalledWidth < lines[scalledHeight].length(); scalledWidth++) {
                double x = (scalledWidth - worldOffsetColumns) * WORLD_SCALLING_FACTOR;
                double y = (scalledHeight - worldOffsetLines) * WORLD_SCALLING_FACTOR;
                char character = lines[scalledHeight].charAt(scalledWidth);
                if (character == 'B') {
                    Bush bush = new Bush(gameView, this);
                    bush.getPosition().updateCoordinates(x, y);
                    collidingObjects.add(bush);
                    spawnGameObject(bush);
                } else if (character == 'G') {
                    spawnGameObject(new Ghost(gameView, this, new Position(x, y), "quadratic"));
                } else if (character == 'g') {
                    spawnGameObject(new Ghost(gameView, this, new Position(x, y), "triangular"));
                } else if (character == 'p') {
                    spawnGameObject(new Ghost(gameView, this, new Position(x, y), "linear"));
                } else if (character == 'A') {
                    spawnGameObject(new Accordion(gameView, this, new Position(x, y), "quadratic"));
                } else if (character == 'a') {
                    spawnGameObject(new Accordion(gameView, this, new Position(x, y), "triangular"));
                } else if (character == 'o') {
                    spawnGameObject(new Accordion(gameView, this, new Position(x, y), "linear"));
                } else if (character == 'S') {
                    Spy spy = new Spy(gameView, this, new Position(x, y), "quadratic");
                    spawnGameObject(spy);
                } else if (character == 's') {
                    Spy spy = new Spy(gameView, this, new Position(x, y), "triangular");
                    spawnGameObject(spy);
                } else if (character == 'z') {
                    Spy spy = new Spy(gameView, this, new Position(x, y), "linear");
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
                }
            }
        }
        tank.setCollidingGameObjectsForPathDecision(collidingObjects);
    }

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
            } else if (gameObject instanceof WallRocketPad wallRocketPad) {
                if (wallRocketPad.tryToActivate(null)) {
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
}
