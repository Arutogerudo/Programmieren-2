package thd.gameobjects.base;

/**
 * Interface of game Objects that can be spawned active or inactive.
 *
 * @param <T> data type of Parameter info.
 */
public interface ActivatableGameObject<T> {
    /**
     * Method that checks if the Game Object should be updatet or not.
     *
     * @param info  Object for decision wether the GameObject should be activated or not.
     * @return      Returns true if GameObject should be created active.
     */
    boolean tryToActivate(T info);
}
