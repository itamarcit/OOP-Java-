package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;
import static oop.ex5.main.ConstantValues.*;

/**
 * an abstract class for variable instance in Sjavac.
 */
public abstract class Variable {
    private String name;
    private final boolean isFinal;
    private boolean initialized;
    private boolean originalInit;

    /**
     * constructor
     * @param name variable name
     * @param isFinal true if final, false otherwise
     * @param initialized true if initialized, false otherwise
     */
    Variable(String name, boolean isFinal, boolean initialized) {
        this.name = name;
        this.isFinal = isFinal;
        this.initialized = initialized;
        originalInit = initialized;
    }

    /**
     * this method returns variable name
     * @return variable name
     */
    public String getName() {
        return name;
    }

    /**
     * set variable name
     * @param name a given name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * change variable status to be initialized
     */
    public void initialize() {
        this.initialized = true;
    }

    /**
     * Initializes a global variable, that was initialized in the global scope.
     */
    public void initializeGlobally() {
        originalInit = true;
        this.initialized = true;
    }

    /**
     * returns variable final status
     * @return true if final, false otherwise.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * returns variable initialized status
     * @return true if initialized, false otherwise.
     */
    public boolean isInitialized() { return initialized; }

    /**
     * an abstract method that check if the given value fit to current variable type
     * @param value given value to be assignment
     * @throws IllegalCodeException if the given value doesn't fit
     */
    public abstract void checkVariable(String value) throws IllegalCodeException;

    /**
     * an abstract method that return the current variable type
     * each variable type represented by a different number
     * those number are in the beginning of this class
     * @return an int represented the variable type
     */
    public abstract int checkType();


    /**
     * Checks if this can be used as a boolean.
     * @return true if it can, false otherwise.
     */
    public boolean isBoolean() {
        return this.checkType() == INT_REPRESENTATION || this.checkType() == DOUBLE_REPRESENTATION ||
                this.checkType() == BOOLEAN_REPRESENTATION;
    }

    /**
     * Resets the variable's initialization to the original one. Should only be used on global variables at the end
     * of a function.
     */
    public void resetInit() {
        initialized = originalInit;
    }
}
