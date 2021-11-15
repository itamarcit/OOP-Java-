package oop.ex5.variables;

import oop.ex5.main.IllegalCodeException;

import java.util.regex.Matcher;

import static oop.ex5.main.ConstantValues.*;

/**
 * Variable Factory - holds all the variable inheritors
 * this class create a Variable with a given String and holds all the methods
 * that connects the different inheritors
 */
public class VariableFactory {
    /**
     * Creates a variable from the given params.
     * @param variableType A string of the variable type.
     * @param variableName A string of the variable name.
     * @param isFinal true if var should be final, false otherwise.
     * @param isInitialized true if var should be initialized, false otherwise.
     * @return The newly created variable.
     * @throws IllegalCodeException If type is illegal or variable value is illegal.
     */
    public static Variable createVariable(String variableType, String variableName, boolean isFinal,
                                           boolean isInitialized) throws IllegalCodeException {
        Variable newVariable;
        switch(variableType) {
            case INT:
                newVariable = new IntVariable(variableName, isFinal, isInitialized);
                break;
            case DOUBLE:
                newVariable = new DoubleVariable(variableName, isFinal, isInitialized);
                break;
            case CHAR:
                newVariable = new CharVariable(variableName, isFinal, isInitialized);
                break;
            case STRING:
                newVariable = new StringVariable(variableName, isFinal, isInitialized);
                break;
            case BOOLEAN:
                newVariable = new BooleanVariable(variableName, isFinal, isInitialized);
                break;
            default:
                throw new IllegalCodeException(INVALID_TYPE_MSG);
        }
        return newVariable;
    }

    /**
     * Compares nowVar to the other representation for assignment of other to nowVar.
     * @param representation the type to compare this variable to.
     * @throws IllegalCodeException if other is not fit to variable type assigment
     */
    public static void compareTypes(Variable nowVar, int representation) throws IllegalCodeException {
        switch(nowVar.checkType())
        {
            case INT_REPRESENTATION:
                if (representation != INT_REPRESENTATION)
                {
                    throw new IllegalCodeException(TYPE_MISMATCH);
                }
                break;
            case DOUBLE_REPRESENTATION:
                if (representation == INT_REPRESENTATION || representation == DOUBLE_REPRESENTATION ) { break; }
                else { throw new IllegalCodeException(TYPE_MISMATCH); }
            case CHAR_REPRESENTATION:
                if (representation != CHAR_REPRESENTATION)
                {
                    throw new IllegalCodeException(TYPE_MISMATCH);
                }
                break;
            case STRING_REPRESENTATION:
                if (representation != STRING_REPRESENTATION)
                {
                    throw new IllegalCodeException(TYPE_MISMATCH);
                }
                break;
            case BOOLEAN_REPRESENTATION:
                if (representation == INT_REPRESENTATION || representation == DOUBLE_REPRESENTATION || representation == BOOLEAN_REPRESENTATION)
                { break; }
                else { throw new IllegalCodeException(TYPE_MISMATCH); }
        }
    }

    /**
     * Checks if the given value fits the type of the given type.
     * @param value the value to be checked.
     * @param requiredType the type to check the value in.
     * @throws IllegalCodeException if the value doesn't fit the type.
     */
    public static void checkVariable(String value, int requiredType) throws IllegalCodeException {
        Matcher typeMatcher = null;
        switch (requiredType) {
            case INT_REPRESENTATION:
                typeMatcher = INT_VARIABLE_PATTERN.matcher(value);
                break;
            case DOUBLE_REPRESENTATION:
                typeMatcher = DOUBLE_VARIABLE_PATTERN.matcher(value);
                break;
            case CHAR_REPRESENTATION:
                typeMatcher = CHAR_VARIABLE_PATTERN.matcher(value);
                break;
            case STRING_REPRESENTATION:
                typeMatcher = STRING_VARIABLE_PATTERN.matcher(value);
                break;
            case BOOLEAN_REPRESENTATION:
                typeMatcher = BOOLEAN_VARIABLE_PATTERN.matcher(value);
                break;
        }
        if (typeMatcher != null && !typeMatcher.matches()) {
            throw new IllegalCodeException(METHOD_ARGUMENT_TYPE_ERROR);
        }
    }
}
