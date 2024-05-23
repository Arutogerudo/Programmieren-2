package thd.game.managers;

/**
 * Exception class for unchecked exception.
 * Is thrown if the main character does not have any more lives in the game.
 */
public class GameOverException extends RuntimeException {
    /**
     * Is thrown if the main character does not have any more lives in the game.
     *
     * @param message Gives the message Game over
     */
    public GameOverException(String message){
        super(message);
    }
}
