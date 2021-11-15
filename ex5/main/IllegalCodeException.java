package oop.ex5.main;

/**
 * we will use this exception when the code gets a compilation error by the Sjavac program.
 */
public class IllegalCodeException extends Exception {
    /**
     * constructor
     * @param message gets a message that represents the reason for the exception.
     */
    public IllegalCodeException(String message) {
        super(message);
    }
}

