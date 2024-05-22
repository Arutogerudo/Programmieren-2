package thd.game.managers;

import thd.game.utilities.SortedGameObjectsList;
import thd.gameobjects.base.GameObject;

import java.util.*;

/**
 * Class to manage the gameobjects.
 */
class GameObjectManager extends CollisionManager {
    private final List<GameObject> gameObjects;
    private final List<GameObject> gameObjectsToBeAdded;
    private final List<GameObject> gameObjectsToBeRemoved;
    private static final int MAXIMUM_NUMBER_OF_GAME_OBJECTS = 1000;

    GameObjectManager(){
        gameObjects = new SortedGameObjectsList();
        gameObjectsToBeAdded = new LinkedList<>();
        gameObjectsToBeRemoved = new LinkedList<>();
    }

    void add(GameObject gameObject){
        gameObjectsToBeAdded.add(gameObject);
    }
    void remove(GameObject gameObject){
        gameObjectsToBeRemoved.add(gameObject);
    }
    void removeAll(){
        gameObjectsToBeAdded.clear();
        gameObjectsToBeRemoved.addAll(gameObjects);
    }

    void gameLoopUpdate() {
        updateLists();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }
        manageCollisions(true);
    }
    private void updateLists() {
        removeFromGameObjects();
        addToGameObjects();

        if (gameObjects.size() > MAXIMUM_NUMBER_OF_GAME_OBJECTS) {
            throw new TooManyGameObjectsException("Too many Game Objects are existing! " + gameObjects.size() + " are existing but just " + MAXIMUM_NUMBER_OF_GAME_OBJECTS + " are allowed.");
        }
    }

    private void addToGameObjects() {
        for (GameObject toAdd:gameObjectsToBeAdded) {
            addToCollisionManagement(toAdd);
            gameObjects.add(toAdd);
        }
        gameObjectsToBeAdded.clear();
    }

    private void removeFromGameObjects() {
        for (GameObject gameObject:gameObjectsToBeRemoved) {
            removeFromCollisionManagement(gameObject);
            gameObjects.remove(gameObject);
        }
        gameObjectsToBeRemoved.clear();
    }
}
