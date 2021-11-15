package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;
import static oop.ex5.main.ConstantValues.*;

/**
 * int Variable - a number (positive, 0 or negative)
 */
public class IntVariable extends Variable {

    /**
     * Constructor
     * @param name name of the variable.
     * @param isFinal is the variable final.
     * @param initialized is the variable initialized.
     */
    public IntVariable(String name, boolean isFinal, boolean initialized) {
        super(name, isFinal, initialized);
    }

    /**
     * check if value fit to int assignment meaning value is a number (positive, 0 or negative)
     * @param value given value to be assignment
     * @throws IllegalCodeException if value doesn't fit
     */
    @Override
    public void checkVariable(String value) throws IllegalCodeException {
        if(!value.matches(INT_VARIABLE_REGEX)) {
            throw new IllegalCodeException(ILLEGAL_VARIABLE_VALUE);
        }
    }

    /**
     * return int representation
     * @return int type
     */
    @Override
    public int checkType() {
        return INT_REPRESENTATION;
    }


}
