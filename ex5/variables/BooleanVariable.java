package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;
import static oop.ex5.main.ConstantValues.*;

/**
 * Boolean Variable - true, false or any number (int or double)
 */
public class BooleanVariable extends Variable {

    /**
     * Constructor
     * @param name name of the variable.
     * @param isFinal is the variable final.
     * @param initialized is the variable initialized.
     */
    public BooleanVariable(String name, boolean isFinal, boolean initialized) {
        super(name, isFinal, initialized);
    }

    /**
     * constructor - WARNING:
     * Used to check temporary variables, should not be used otherwise.
     */
    public BooleanVariable() {
        super(null, false, false);
    }

    /**
     * check if value fit to boolean assignment meaning value is true, false or any number (int or double)
     * @param value given value to be assignment
     * @throws IllegalCodeException if value doesn't fit
     */
    public void checkVariable(String value) throws IllegalCodeException {
        if(!value.matches(BOOLEAN_VARIABLE_REGEX)) {
            throw new IllegalCodeException(ILLEGAL_VARIABLE_VALUE);
        }
    }

    @Override
    public int checkType() {
        return BOOLEAN_REPRESENTATION;
    }
}
