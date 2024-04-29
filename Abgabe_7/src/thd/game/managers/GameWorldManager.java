package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.movable.Accordion;
import thd.gameobjects.movable.Ghost;
import thd.gameobjects.movable.Spy;
import thd.gameobjects.movable.Tank;
import thd.gameobjects.unmovable.*;

import java.util.LinkedList;

public class GameWorldManager extends GamePlayManager {
    private final String world; //51:29
    private static final int WORLD_SCALLING_FACTOR = 25;
    private LinkedList<CollidingGameObject> collidingObjects;
    private final int worldOffsetColumns;
    private final int worldOffsetLines;

    protected GameWorldManager(GameView gameView) {
        super(gameView);
        world = """
                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\s
                BB                                                 \s
                B  T                                           G   \s
                B                                                  \s
                B                                                  \s
                B                                                  \s
                B                                                  \s
                B                               WWWWWWWWWWWWWWWWWWW\s
                B                                                  \s
                B                                                  \s
                B                                                  \s
                B                                                  \s
                B                                                  \s
                B              BB                                  \s
                B              BB                 BBBBBBBBBBBBBBBBB\s
                B                                BBBBBBBBBBBBBBBBBB\s
                B                              BBBB  S             \s
                B                             BBBBB                \s
                B                             BBBB   R             \s
                B                            BBBBB                 \s
                B                             BBBBB                \s
                B   A                        BBBBB                 \s
                BB                          BBBBB                  \s
                BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB\s
                                                                   \s
                                                                   \s
                                                                   \s
                                                                   \s
                                                                   \s""";
        tank = new Tank(gameView, this);
        scoreboard = new Scoreboard(gameView, this);
        collidingObjects = new LinkedList<>();
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
    }

    private void spawnGameObjects() {
        spawnGameObject(scoreboard);
    }

    private void spawnGameObjectsFromWorldString() {
        String[] lines = world.split("\\R");
        for (int scalledHeight = 0; scalledHeight < lines.length; scalledHeight++) {
            for (int scalledWidth = 0; scalledWidth < lines[scalledHeight].length(); scalledWidth++) {
                double x = (WORLD_SCALLING_FACTOR - worldOffsetColumns) * scalledWidth;
                double y = (WORLD_SCALLING_FACTOR - worldOffsetLines) * scalledHeight;
                char character = lines[scalledHeight].charAt(scalledWidth);
                if (character == 'B') {
                    Bush bush = new Bush(gameView, this);
                    bush.getPosition().updateCoordinates(x, y);
                    spawnGameObject(bush);
                } else if (character == 'G') {
                    spawnGameObject(new Ghost(gameView, this, x, y));
                } else if (character == 'A') {
                    Accordion accordion = new Accordion(gameView, this, x, y);
                    spawnGameObject(accordion);
                } else if (character == 'W') {
                    WoodenWall woodenWall = new WoodenWall(gameView, this);
                    collidingObjects.add(woodenWall);
                    woodenWall.getPosition().updateCoordinates(x, y);
                    spawnGameObject(woodenWall);
                } else if (character == 'R') {
                    RadioactivePack radioactivePack = new RadioactivePack(gameView, this);
                    radioactivePack.getPosition().updateCoordinates(x, y);
                    spawnGameObject(radioactivePack);
                } else if (character == 'S') {
                    Spy spy = new Spy(gameView, this, x, y);
                    spawnGameObject(spy);
                } else if (character == 'T') {
                    tank.getPosition().updateCoordinates(x, y);
                    tank.setCollidingGameObjectsForPathDecision(collidingObjects);
                    spawnGameObject(tank);
                }
            }
        }
    }
}
