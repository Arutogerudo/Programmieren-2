package thd.game.managers;

import thd.gameobjects.base.GameObject;

import java.util.*;

/**
 * Class to manage the gameobjects.
 */
public class GameObjectManager {
    private final List<GameObject> gameObjects;
    private final List<GameObject> gameObjectsToBeAdded;
    private final List<GameObject> gameObjectsToBeRemoved;

    GameObjectManager(){
        gameObjects = new LinkedList<>();
        gameObjectsToBeAdded = new LinkedList<>();
        gameObjectsToBeRemoved = new LinkedList<>();
    }

    void add(GameObject gameObject){
        gameObjectsToBeAdded.add(gameObject);
    }
    private void remove(GameObject gameObject){
        gameObjectsToBeRemoved.add(gameObject);
    }
    private void removeAll(){
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
    }
    private void updateLists() {
        removeFromGameObjects();
        addToGameObjects();
    }

    private void addToGameObjects() {
        gameObjects.addAll(gameObjectsToBeAdded);
        gameObjectsToBeAdded.clear();
    }

    private void removeFromGameObjects() {
        gameObjects.removeAll(gameObjectsToBeRemoved);
        gameObjectsToBeRemoved.clear();
    }

}
