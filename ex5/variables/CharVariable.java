package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;
import static oop.ex5.main.ConstantValues.*;

/**
 * Char Variable - any character (inside single quotation marks)
 */
public class CharVariable extends Variable {

    /**
     * Constructor
     * @param name name of the variable.
     * @param isFinal is the variable final.
     * @param initialized is the variable initialized.
     */
    public CharVariable(String name, boolean isFinal, boolean initialized) {
        super(name, isFinal, initialized);
    }

    /**
     * check if value fit to char assignment meaning value is any character (inside single quotation marks)
     * @param value given value to be assignment
     * @throws IllegalCodeException if value doesn't fit
     */
    public void checkVariable(String value) throws IllegalCodeException {
        if(!value.matches(CHAR_VARIABLE_REGEX)) {
            throw new IllegalCodeException(ILLEGAL_VARIABLE_VALUE);
        }
    }

    /**
     * return char representation
     * @return char type
     */
    @Override
    public int checkType() {
        return CHAR_REPRESENTATION;
    }
}
