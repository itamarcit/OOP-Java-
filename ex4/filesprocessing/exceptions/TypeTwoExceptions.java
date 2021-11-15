package filesprocessing.exceptions;

/**
 * Abstract class for all exceptions that stop the program from running.
 */
public abstract class TypeTwoExceptions extends Exception {
    TypeTwoExceptions(String message) {
        super(message);
    }
}
