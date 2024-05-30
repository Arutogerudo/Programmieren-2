package thd.game.managers;

/**
 * Exception class for unchecked exception.
 * Is thrown if there are more than a maximum number of objects existing in the game.
 */
class TooManyGameObjectsException extends RuntimeException {
    /**
     * Is thrown if there are more than a maximum number of objects existing in the game.
     *
     * @param message Gives the message including the exceeded limit of parallel existing gameobjects
     */
    TooManyGameObjectsException(String message){
        super(message);
    }
}
