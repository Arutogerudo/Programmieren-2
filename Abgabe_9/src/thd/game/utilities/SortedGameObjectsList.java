package thd.game.utilities;

import thd.gameobjects.base.GameObject;

import java.util.LinkedList;

/**
 * Special List with automated sorting for List game objects in game object manager.
 */
public class SortedGameObjectsList extends LinkedList<GameObject> {
    @Override
    public boolean add(GameObject object) {
        int indexToSortIn = 0;
        for (GameObject gameObject : this) {
            if (gameObject.getDistanceToBackground() >= object.getDistanceToBackground()) {
                break;
            }
            indexToSortIn++;
        }
        this.add(indexToSortIn, object);
        return true;
    }
}
