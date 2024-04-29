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
    private static final int MAXIMUM_NUMBER_OF_GAME_OBJECTS = 500;

    GameObjectManager(){
        gameObjects = new LinkedList<>();
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
    }
    private void updateLists() {
        try {
            if (gameObjects.size() > MAXIMUM_NUMBER_OF_GAME_OBJECTS) {
                throw new TooManyGameObjectsException("Too many Game Objects are existing! " + gameObjects.size() + " are existing but just " + MAXIMUM_NUMBER_OF_GAME_OBJECTS + " are allowed.");
            } else {
                removeFromGameObjects();
                addToGameObjects();
            }
        } catch (TooManyGameObjectsException tooManyGameObjectsException) {
            System.err.println("->" + tooManyGameObjectsException.getMessage());
        }
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
