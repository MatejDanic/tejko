package matej.tejkogames.exceptions;

/**
 * Thrown if a user tries to make a move that is illegal by the rules of the game.
 *
 * @author MatejDanic
 * @version 1.0
 * @since 2020-08-20
 */
public class IllegalMoveException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an <code>IllegalMoveException</code> with the specified message.
	 *
	 * @param message the detail message.
	 */
	public IllegalMoveException (String message) {
        super("Illegal move: " + message);
    }
}