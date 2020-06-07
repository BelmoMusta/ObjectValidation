package io.github.belmomusta.validation.exception;

/**
 * The validation exception
 *
 * @since 0.0.0.SNAPSHOT
 * @author default author
 * @version 0.0.0
 */
public class ValidationException extends Exception {

    /**
     * Constructor of the ValidationException exception
     *
     * @param message the exception message
     * @param cause   the cause of the exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor of the ValidationException exception
     *
     * @param message The exception message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor of the ValidationException exception
     *
     * @param cause the cause of the exception
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
