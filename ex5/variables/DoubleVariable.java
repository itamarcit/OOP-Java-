package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;
import static oop.ex5.main.ConstantValues.*;

/**
 * Double Variable - an integer or a floating-point number (positive, 0 or negative)
 */
public class DoubleVariable extends Variable {

    /**
     * Constructor
     * @param name name of the variable.
     * @param isFinal is the variable final.
     * @param initialized is the variable initialized.
     */
    public DoubleVariable(String name, boolean isFinal, boolean initialized) {
        super(name, isFinal, initialized);
    }

    /**
     * check if value fit to double assignment meaning value is an integer or a floating-point
     * number (positive, 0 or negative)
     * @param value given value to be assignment
     * @throws IllegalCodeException if value doesn't fit
     */
    @Override
    public void checkVariable(String value) throws IllegalCodeException {
        if(!value.matches(DOUBLE_VARIABLE_REGEX)) {
            throw new IllegalCodeException(ILLEGAL_VARIABLE_VALUE);
        }
    }

    /**
     * return double representation
     * @return double type
     */
    @Override
    public int checkType() {
        return DOUBLE_REPRESENTATION;
    }
}
