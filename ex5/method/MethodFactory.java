package oop.ex5.method;

import oop.ex5.main.IllegalCodeException;
import oop.ex5.variables.VariableFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

import static oop.ex5.main.ConstantValues.*;

/**
 * MethodFactory - this class creates a method from given line
 */
public class MethodFactory {
    private static String line;

    /**
     * creates method from a given declaration line, and upper scope.
     * doesn't check all method lines, only create a method from declaration line
     * @param declarationLine given line
     * @param upperScope the upper scope this method was called from
     * @return a new method
     * @throws IllegalCodeException if there's an error with the variable.
     */
    public static Method createMethod(String declarationLine, Scope upperScope) throws IllegalCodeException {
        line = declarationLine;
        Scope newMethodScope = new Scope(upperScope);
        Method newMethod = new Method(getMethodName(declarationLine), new ArrayList<>(), newMethodScope);
        getMethodVariables(newMethod);
        return newMethod;
    }

    /**
     * Gets the name of a method from the line in the code.
     * @return The name of the method.
     */
    public static String getMethodName(String line) {
        String[] methodStrings = line.split(SPACE, TWO);
        return methodStrings[1].substring(0, methodStrings[1].indexOf(OPEN_BRACKET));
    }

    /**
     * inserts variables into the new method from declaration line
     * @param newMethod a given method
     * @throws IllegalCodeException if there's an error with the variable.
     */
    private static void getMethodVariables(Method newMethod) throws IllegalCodeException {
        String innerLine = line.substring(line.indexOf(OPEN_BRACKET) + 1, line.indexOf(CLOSED_BRACKET));
        if(innerLine.isEmpty()) {
            return;
        }
        Matcher finalQualifier;
        boolean isFinal;
        String[] variables = innerLine.split(COMMA);
        ArrayList<String> allMatches = new ArrayList<>(Arrays.asList(variables));
        String[] temp;
        String type, name;
        for (String match : allMatches) {
            finalQualifier = FINAL_QUALIFIER_PATTERN.matcher(match);
            isFinal = finalQualifier.find();
            String trimmed = match.trim();
            temp = trimmed.split(SPACE);
            if (isFinal) {
                type = temp[1];
                name = temp[TWO];
            } else {
                type = temp[0];
                name = temp[1];
            }
            if(newMethod.getScope().declarationVariable(name)) {
                throw new IllegalCodeException(SAME_VAR_NAME_IN_METHOD);
            }
            switch (type) {
                case INT:
                    newMethod.addVariableType(INT_REPRESENTATION);
                    break;
                case DOUBLE:
                    newMethod.addVariableType(DOUBLE_REPRESENTATION);
                    break;
                case CHAR:
                    newMethod.addVariableType(CHAR_REPRESENTATION);
                    break;
                case STRING:
                    newMethod.addVariableType(STRING_REPRESENTATION);
                    break;
                case BOOLEAN:
                    newMethod.addVariableType(BOOLEAN_REPRESENTATION);
                    break;
            }
            newMethod.getScope().addVariable(VariableFactory.createVariable(type, name, isFinal, true));
        }
    }
}
