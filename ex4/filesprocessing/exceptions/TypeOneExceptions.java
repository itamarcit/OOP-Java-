package filesprocessing.exceptions;

/**
 * Abstract class for all exceptions that do not stop the program from running.
 */
public abstract class TypeOneExceptions extends Exception {
    TypeOneExceptions(String message) {
        super(message);
    }
}
