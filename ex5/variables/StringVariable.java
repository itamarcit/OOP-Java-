package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;
import static oop.ex5.main.ConstantValues.*;

/**
 * String Variable - a string of characters
 */
public class StringVariable extends Variable {

    /**
     * Constructor
     * @param name name of the variable.
     * @param isFinal is the variable final.
     * @param initialized is the variable initialized.
     */
    public StringVariable(String name, boolean isFinal, boolean initialized) {
        super(name, isFinal, initialized);
    }

    /**
     * check if value fit to String assignment meaning value is a string of characters
     * @param value given value to be assignment
     */
    public void checkVariable(String value) throws IllegalCodeException {
        if(!value.matches(STRING_VARIABLE_REGEX)) {
            throw new IllegalCodeException(ILLEGAL_VARIABLE_VALUE);
        }
    }

    /**
     * return String representation
     * @return String type
     */
    @Override
    public int checkType() {
        return STRING_REPRESENTATION;
    }
}
