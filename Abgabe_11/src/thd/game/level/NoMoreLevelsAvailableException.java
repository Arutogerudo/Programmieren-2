package thd.game.level;

/**
 * Exception class for unchecked exception.
 * Is thrown if there are no more levels available and the game is done.
 */
public class NoMoreLevelsAvailableException extends RuntimeException {
    /**
     * Is thrown if there are no more levels available and the game is done.
     *
     * @param message message that player have mastered all levels
     */
    public NoMoreLevelsAvailableException(String message) {
        super(message);
    }
}
