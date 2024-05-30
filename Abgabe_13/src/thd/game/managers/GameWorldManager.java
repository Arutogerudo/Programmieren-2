package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

class GameWorldManager extends GamePlayManager {
    private static final int WORLD_SCALLING_FACTOR = 25; //51:29
    private static final int PIXEL_TO_GO_250 = 250;
    private static final int PIXEL_TO_GO_700 = 700;
    private static final int PIXEL_TO_GO_300 = 300;
    private static final int AMOUNT_BUSHES_TWO_SCREENS = 103;
    private static final int AMOUNT_WALL_ONE_SCREEN = 51;
    private static final int AMOUNT_VERTICAL_WALLS_REFINERY = 15;
    final List<GameObject> activatableGameObjects;
    private final LinkedList<CollidingGameObject> collidingGameObjectsForPathDecision;

    GameWorldManager(GameView gameView) {
        super(gameView);
        collidingGameObjectsForPathDecision = new LinkedList<>();
        scoreboard = new Scoreboard(gameView, this);
        this.activatableGameObjects = new LinkedList<>();
        overlay = new Overlay(gameView, this);
    }

    private void spawnGameObjects() {
        spawnGameObject(scoreboard);
        spawnGameObject(overlay);
    }

    private void spawnGameObjectsFromWorldString() {
        String[] lines = level.world.split("\\R");
        for (int scalledHeight = 0; scalledHeight < lines.length; scalledHeight++) {
            for (int scalledWidth = 0; scalledWidth < lines[scalledHeight].length(); scalledWidth++) {
                double x = (scalledWidth - level.worldOffsetColumns) * WORLD_SCALLING_FACTOR;
                double y = (scalledHeight - level.worldOffsetLines) * WORLD_SCALLING_FACTOR;
                char character = lines[scalledHeight].charAt(scalledWidth);
                if (character == 'T'){
                    tank = new Tank(gameView, this, new Position(x, y));
                    addActivatableGameObject(tank);
                } else if (character == 'G') {
                    addActivatableGameObject(new Ghost(gameView, this, new Position(x, y), PIXEL_TO_GO_250, PIXEL_TO_GO_250, "down", "quadratic"));
                } else if (character == 'g') {
                    addActivatableGameObject(new Ghost(gameView, this, new Position(x, y), PIXEL_TO_GO_700, PIXEL_TO_GO_250, "right", "triangular"));
                } else if (character == 'p') {
                    addActivatableGameObject(new Ghost(gameView, this, new Position(x, y), PIXEL_TO_GO_300, 0, "right", "linear"));
                } else if (character == 'A') {
                    addActivatableGameObject(new Accordion(gameView, this, new Position(x, y), PIXEL_TO_GO_250, PIXEL_TO_GO_250, "up", "quadratic"));
                } else if (character == 'a') {
                    addActivatableGameObject(new Accordion(gameView, this, new Position(x, y), PIXEL_TO_GO_700, PIXEL_TO_GO_250, "right", "triangular"));
                } else if (character == 'o') {
                    addActivatableGameObject(new Accordion(gameView, this, new Position(x, y), PIXEL_TO_GO_300, 0, "left", "linear"));
                } else if (character == 'S') {
                    addActivatableGameObject(new Spy(gameView, this, new Position(x, y), PIXEL_TO_GO_250, 100, "right", "quadratic"));
                } else if (character == 's') {
                    addActivatableGameObject(new Spy(gameView, this, new Position(x, y), PIXEL_TO_GO_700, PIXEL_TO_GO_250, "left", "triangular"));
                } else if (character == 'z') {
                    addActivatableGameObject(new Spy(gameView, this, new Position(x, y), 0, PIXEL_TO_GO_300, "up", "linear"));
                } else if (character == 'B') {
                    Bush bush = new Bush(gameView, this, 1);
                    bush.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(bush);
                } else if (character == 'L') {
                    Bush bush = new Bush(gameView, this, AMOUNT_BUSHES_TWO_SCREENS);
                    bush.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(bush);
                } else if (character == 'l') {
                    Bush bush = new Bush(gameView, this, 4);
                    bush.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(bush);
                } else if (character == 'W') {
                    WoodenWall woodenWall = new WoodenWall(gameView, this);
                    woodenWall.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(woodenWall);
                } else if (character == 'R') {
                    RadioactivePack radioactivePack = new RadioactivePack(gameView, this);
                    radioactivePack.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(radioactivePack);
                } else if (character == 'b') {
                    WallRocketPadLeft wallRocketPadLeft = new WallRocketPadLeft(gameView, this);
                    wallRocketPadLeft.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRocketPadLeft);
                } else if (character == 'C') {
                    WallRocketPadRight wallRocketPadRight = new WallRocketPadRight(gameView, this);
                    wallRocketPadRight.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRocketPadRight);
                } else if (character == 'c') {
                    WallRocketPadTop wallRocketPadTop = new WallRocketPadTop(gameView, this);
                    wallRocketPadTop.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRocketPadTop);
                } else if (character == 'D') {
                    WallRocketPadDown wallRocketPadDown = new WallRocketPadDown(gameView, this);
                    wallRocketPadDown.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRocketPadDown);
                } else if (character == 'd') {
                    Rocket rocket = new Rocket(gameView, this);
                    rocket.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(rocket);
                } else if (character == 'E') {
                    Chain chain = new Chain(gameView, this);
                    chain.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(chain);
                } else if (character == 'e') {
                    BlackWallRocketPad blackWallRocketPad = new BlackWallRocketPad(gameView, this);
                    blackWallRocketPad.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(blackWallRocketPad);
                } else if (character == 'F') {
                    StartRamp startRamp = new StartRamp(gameView, this);
                    startRamp.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(startRamp);
                } else if (character == 'f') {
                    Pilons pilons = new Pilons(gameView, this);
                    pilons.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(pilons);
                } else if (character == 'H') {
                    StoneWall stoneWall = new StoneWall(gameView, this);
                    stoneWall.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(stoneWall);
                } else if (character == 'h') {
                    CentrelineRunway centrelineRunway = new CentrelineRunway(gameView, this);
                    centrelineRunway.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(centrelineRunway);
                } else if (character == 'I') {
                    Fence fence = new Fence(gameView, this);
                    fence.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(fence);
                } else if (character == 'i') {
                    Jet jet = new Jet(gameView, this);
                    jet.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(jet);
                } else if (character == 'J') {
                    Parkbox parkbox = new Parkbox(gameView, this);
                    parkbox.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(parkbox);
                } else if (character == 'j') {
                    SafetyBox safetyBox = new SafetyBox(gameView, this);
                    safetyBox.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(safetyBox);
                } else if (character == 'K') {
                    WallAirport wallAirport = new WallAirport(gameView, this);
                    wallAirport.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallAirport);
                } else if (character == 'k') {
                    WallRunway wallRunway = new WallRunway(gameView, this);
                    wallRunway.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRunway);
                } else if (character == 'M') {
                    SideWallBridge sideWallBridge = new SideWallBridge(gameView, this);
                    sideWallBridge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(sideWallBridge);
                } else if (character == 'm') {
                    BottomWallBridge bottomWallBridge = new BottomWallBridge(gameView, this, "top");
                    bottomWallBridge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(bottomWallBridge);
                } else if (character == 'N') {
                    BottomWallBridge bottomWallBridge = new BottomWallBridge(gameView, this, "bottom");
                    bottomWallBridge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(bottomWallBridge);
                } else if (character == 'n') {
                    CommanderBridge commanderBridge = new CommanderBridge(gameView, this);
                    commanderBridge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(commanderBridge);
                } else if (character == 'O') {
                    CDPlateBridge cdPlateBridge = new CDPlateBridge(gameView, this);
                    cdPlateBridge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(cdPlateBridge);
                } else if (character == 'P') {
                    WallRefineryHorizontal wallRefineryHorizontal = new WallRefineryHorizontal(gameView, this, AMOUNT_WALL_ONE_SCREEN);
                    wallRefineryHorizontal.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRefineryHorizontal);
                } else if (character == 'Q') {
                    WallRefineryVertical wallRefineryVertical = new WallRefineryVertical(gameView, this, AMOUNT_VERTICAL_WALLS_REFINERY);
                    wallRefineryVertical.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRefineryVertical);
                } else if (character == 'q') {
                    WallRefineryVertical wallRefineryVertical = new WallRefineryVertical(gameView, this, 10);
                    wallRefineryVertical.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(wallRefineryVertical);
                } else if (character == 'r') {
                    CandyCaneVertical candyCaneVertical = new CandyCaneVertical(gameView, this);
                    candyCaneVertical.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(candyCaneVertical);
                } else if (character == 't') {
                    CandyCaneEdge candyCaneEdge = new CandyCaneEdge(gameView, this, "topleft");
                    candyCaneEdge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(candyCaneEdge);
                } else if (character == 'u') {
                    CandyCaneEdge candyCaneEdge = new CandyCaneEdge(gameView, this, "topright");
                    candyCaneEdge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(candyCaneEdge);
                } else if (character == 'v') {
                    CandyCaneEdge candyCaneEdge = new CandyCaneEdge(gameView, this, "bottomright");
                    candyCaneEdge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(candyCaneEdge);
                } else if (character == 'w') {
                    CandyCaneEdge candyCaneEdge = new CandyCaneEdge(gameView, this, "bottomleft");
                    candyCaneEdge.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(candyCaneEdge);
                } else if (character == 'X') {
                    CandyCaneHorizontal candyCaneHorizontal = new CandyCaneHorizontal(gameView, this);
                    candyCaneHorizontal.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(candyCaneHorizontal);
                } else if (character == 'x') {
                    ChainRefinery chainRefinery = new ChainRefinery(gameView, this, "horizontal");
                    chainRefinery.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(chainRefinery);
                } else if (character == 'Y') {
                    ChainRefinery chainRefinery = new ChainRefinery(gameView, this, "vertical");
                    chainRefinery.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(chainRefinery);
                }
            }
        }
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
            if (gameObject instanceof Tank) {
                if (tank.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Accordion accordion) {
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
                    spawnPathBlockingGameObject(blackWallRocketPad);
                    iterator.remove();
                }
            } else if (gameObject instanceof Bush bush) {
                if (bush.tryToActivate(null)) {
                    spawnPathBlockingGameObject(bush);
                    iterator.remove();
                }
            } else if (gameObject instanceof Jet jet) {
                if (jet.tryToActivate(null)) {
                    spawnPathBlockingGameObject(jet);
                    iterator.remove();
                }
            } else if (gameObject instanceof RadioactivePack radioactivePack) {
                if (radioactivePack.tryToActivate(null)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            } else if (gameObject instanceof Rocket rocket) {
                if (rocket.tryToActivate(null)) {
                    spawnPathBlockingGameObject(rocket);
                    iterator.remove();
                }
            } else if (gameObject instanceof StartRamp startRamp) {
                if (startRamp.tryToActivate(null)) {
                    spawnPathBlockingGameObject(startRamp);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRocketPadLeft wallRocketPadLeft) {
                if (wallRocketPadLeft.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRocketPadLeft);
                    iterator.remove();
                }
            } else if (gameObject instanceof WoodenWall woodenWall) {
                if (woodenWall.tryToActivate(null)) {
                    spawnPathBlockingGameObject(woodenWall);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRunway wallRunway) {
                if (wallRunway.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRunway);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallAirport wallAirport) {
                if (wallAirport.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallAirport);
                    iterator.remove();
                }
            } else if (gameObject instanceof SafetyBox safetyBox) {
                if (safetyBox.tryToActivate(null)) {
                    spawnPathBlockingGameObject(safetyBox);
                    iterator.remove();
                }
            } else if (gameObject instanceof Parkbox parkbox) {
                if (parkbox.tryToActivate(null)) {
                    spawnPathBlockingGameObject(parkbox);
                    iterator.remove();
                }
            } else if (gameObject instanceof CentrelineRunway centrelineRunway) {
                if (centrelineRunway.tryToActivate(null)) {
                    spawnPathBlockingGameObject(centrelineRunway);
                    iterator.remove();
                }
            } else if (gameObject instanceof StoneWall stoneWall) {
                if (stoneWall.tryToActivate(null)) {
                    spawnPathBlockingGameObject(stoneWall);
                    iterator.remove();
                }
            } else if (gameObject instanceof Pilons pilons) {
                if (pilons.tryToActivate(null)) {
                    spawnPathBlockingGameObject(pilons);
                    iterator.remove();
                }
            } else if (gameObject instanceof Fence fence) {
                if (fence.tryToActivate(null)) {
                    spawnPathBlockingGameObject(fence);
                    iterator.remove();
                }
            } else if (gameObject instanceof Chain chain) {
                if (chain.tryToActivate(null)) {
                    spawnPathBlockingGameObject(chain);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRocketPadDown wallRocketPadDown) {
                if (wallRocketPadDown.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRocketPadDown);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRocketPadTop wallRocketPadTop) {
                if (wallRocketPadTop.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRocketPadTop);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRocketPadRight wallRocketPadRight) {
                if (wallRocketPadRight.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRocketPadRight);
                    iterator.remove();
                }
            } else if (gameObject instanceof SideWallBridge sideWallBridge) {
                if (sideWallBridge.tryToActivate(null)) {
                    spawnPathBlockingGameObject(sideWallBridge);
                    iterator.remove();
                }
            } else if (gameObject instanceof BottomWallBridge bottomWallBridge) {
                if (bottomWallBridge.tryToActivate(null)) {
                    spawnPathBlockingGameObject(bottomWallBridge);
                    iterator.remove();
                }
            } else if (gameObject instanceof CommanderBridge commanderBridge) {
                if (commanderBridge.tryToActivate(null)) {
                    spawnPathBlockingGameObject(commanderBridge);
                    iterator.remove();
                }
            } else if (gameObject instanceof CDPlateBridge cdPlateBridge) {
                if (cdPlateBridge.tryToActivate(null)) {
                    spawnPathBlockingGameObject(cdPlateBridge);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRefineryHorizontal wallRefineryHorizontal) {
                if (wallRefineryHorizontal.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRefineryHorizontal);
                    iterator.remove();
                }
            } else if (gameObject instanceof WallRefineryVertical wallRefineryVertical) {
                if (wallRefineryVertical.tryToActivate(null)) {
                    spawnPathBlockingGameObject(wallRefineryVertical);
                    iterator.remove();
                }
            } else if (gameObject instanceof CandyCaneVertical candyCaneVertical) {
                if (candyCaneVertical.tryToActivate(null)) {
                    spawnPathBlockingGameObject(candyCaneVertical);
                    iterator.remove();
                }
            } else if (gameObject instanceof CandyCaneEdge candyCaneEdge) {
                if (candyCaneEdge.tryToActivate(null)) {
                    spawnPathBlockingGameObject(candyCaneEdge);
                    iterator.remove();
                }
            } else if (gameObject instanceof CandyCaneHorizontal candyCaneHorizontal) {
                if (candyCaneHorizontal.tryToActivate(null)) {
                    spawnPathBlockingGameObject(candyCaneHorizontal);
                    iterator.remove();
                }
            } else if (gameObject instanceof ChainRefinery chainRefinery) {
                if (chainRefinery.tryToActivate(null)) {
                    spawnPathBlockingGameObject(chainRefinery);
                    iterator.remove();
                }
            }
        }
        tank.setCollidingGameObjectsForPathDecision(collidingGameObjectsForPathDecision);
    }
    private void spawnPathBlockingGameObject(CollidingGameObject collidingGameObject) {
        spawnGameObject(collidingGameObject);
        collidingGameObjectsForPathDecision.add(collidingGameObject);
    }

    protected void initializeLevel() {
        activatableGameObjects.clear();
        destroyAllGameObjects();
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
        clearListsForPathDecisionsInGameObjects();
    }

    private void clearListsForPathDecisionsInGameObjects() {
        collidingGameObjectsForPathDecision.clear();
        tank.setCollidingGameObjectsForPathDecision(new LinkedList<>());
    }
}
