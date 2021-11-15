package oop.ex5.main;

import oop.ex5.method.Method;
import oop.ex5.method.MethodFactory;
import oop.ex5.method.Scope;
import oop.ex5.variables.BooleanVariable;
import oop.ex5.variables.Variable;
import oop.ex5.variables.VariableFactory;

import static oop.ex5.main.ConstantValues.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

/**
 * This class checks the format of a code file, using the s-Java requirements.
 */
public class FormatChecker {
    private static final HashMap<String, Method> methodNames = new HashMap<>();
    private final static Scope globalScope = new Scope(null);
    private final static Method globalMethod = new Method(GLOBAL_METHOD, null, globalScope);
    private static String line;
    private static BufferedReader reader;
    private static int depth = 0;

    /**
     * Checks a given code-file.
     * @param path Path to the code file.
     * @throws IOException if an error occurred when reading from the file.
     * @throws IllegalCodeException if there's an error within the code file.
     */
    public static void checkCode(String path) throws IOException, IllegalCodeException {
        reader = new BufferedReader(new FileReader(path));
        try {
            firstCheck();
        } catch (Exception e) {
            reader.close();
            resetFormatChecker();
            throw e;
        }
        reader.close();
        reader = new BufferedReader(new FileReader(path));
        try {
            secondCheck();
        } catch (Exception e) {
            reader.close();
            resetFormatChecker();
            throw e;
        }
        reader.close();
        resetFormatChecker();
    }

    /**
     * Clears the global variables and the method names in case the class is called again on a different file.
     */
    private static void resetFormatChecker() {
        globalScope.getAllVariables().clear();
        methodNames.clear();
    }

    /**
     * The first format check. Stores global variables and skips the inside of the functions.
     * @throws IOException if an error occurred when reading the file.
     * @throws IllegalCodeException if there's an illegal part of code in the file.
     */
    private static void firstCheck() throws IOException, IllegalCodeException {
        getNextLine();
        while(line != null) {
            if(emptyCommentLine()) {
                getNextLine();
                continue;
            }
            else {
                line = line.trim();
            }
            if(firstCheckMethodLine()) {
                continue;
            }
            if(checkVariableLine(CHECK_ONE, globalMethod)) {
                getNextLine();
            }
            else if(checkVariableAssignment(CHECK_ONE, globalMethod)) {
                getNextLine();
            }
            else {
                throw new IllegalCodeException(INVALID_LINE);
            }
        }
    }

    /**
     * Checks if the line is a variable assignment, if it is checks it.
     * @return true if it was a variable assignment, false otherwise.
     * @throws IllegalCodeException if there's a problem with the line.
     */
    private static boolean checkVariableAssignment(int checkNum, Method methodToCheck) throws IllegalCodeException {
        Matcher variableAssignment = VARIABLE_ASSIGNMENT_PATTERN.matcher(line);
        if (variableAssignment.matches()) {
            String[] varSplit = line.split(EQUALS_SIGN);
            String name = varSplit[0].replaceAll(WHITESPACE, EMPTY_STRING);
            Variable newVar = methodToCheck.getScope().getVariable(name);
            if (newVar == null) {
                throw new IllegalCodeException(VAR_DOESNT_EXIST);
            }
            if (newVar.isFinal()) {
                throw new IllegalCodeException(FINAL_VAR_ASSIGNMENT);
            }
            String other = varSplit[1].trim();
            other = other.replaceAll(SEMICOLON, EMPTY_STRING);
            Variable otherVar = methodToCheck.getScope().getVariable(other);

            //checking for assigment of other value
            if (otherVar != null && otherVar.isInitialized()) {
                VariableFactory.compareTypes(newVar, otherVar.checkType());
            } else {
                newVar.checkVariable(other);
            }
            if(checkNum == CHECK_ONE) {
                globalScope.getVariable(name).initializeGlobally();
            }
            else {
                newVar.initialize();
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the current line starts with 'void' and then processes it. Advances line to after the method.
     * @return true if it does, false otherwise.
     */
    private static boolean firstCheckMethodLine() throws IllegalCodeException, IOException {
        Matcher matcher = VOID_PATTERN.matcher(line);
        if(matcher.find()) {
            checkMethodDeclarationLine();
            Method newMethod = MethodFactory.createMethod(line, globalScope);
            methodNames.put(newMethod.getName(), newMethod);
            checkIfMethodCloses();
            return true;
        }
        return false;
    }

    /**
     * Checks if the method closes, and advances the line to be after the current method it's in.
     * To be called in check one, after checking the method declaration line.
     * @throws IllegalCodeException if there's no closing bracket.
     * @throws IOException if there's a problem reading from the file.
     */
    private static void checkIfMethodCloses() throws IllegalCodeException, IOException {
        getNextLine();
        int parenthesis = 1;
        Matcher openBracket;
        Matcher closeBracket;
        while (parenthesis != 0 && line != null) {
            openBracket = OPENING_BRACKET_PATTERN.matcher(line);
            closeBracket = CLOSING_BRACKET_PATTERN.matcher(line);
            if (openBracket.find()) {
                parenthesis++;
            } else if (closeBracket.matches()) {
                parenthesis--;
            }
            line = reader.readLine();
        }
        if (parenthesis != 0) {
            throw new IllegalCodeException(METHOD_MISSING_BRACKET);
        }
    }

    /**
     * Checks if the given line fits a method declaration line.
     * @throws IllegalCodeException if it doesn't fit.
     */
    private static void checkMethodDeclarationLine() throws IllegalCodeException {
        Matcher methodDeclarationMatcher = METHOD_DECLARATION_PATTERN.matcher(line);
        if (!methodDeclarationMatcher.matches()) {
            throw new IllegalCodeException(METHOD_DECLARATION_ERROR);
        }
    }

    /**
     * Checks if the current line is a variable declaration/assignment line.
     * @return true if it is a variable line, false otherwise.
     * @throws IllegalCodeException if it was a variable line and there's an error with it.
     */
    private static boolean checkVariableLine(int checkNum, Method methodToCheck) throws IllegalCodeException {
        Matcher variableLine = VARIABLE_LINE_PATTERN.matcher(line);
        if (variableLine.matches()) {
            Map<String, Variable> variables;
            if(checkNum == CHECK_ONE) {
                variables = getVariablesFromLine(CHECK_ONE, globalMethod);
            }
            else {
                variables = getVariablesFromLine(CHECK_TWO, methodToCheck);
            }
            if (variables != null) {
                for (Variable var : variables.values()) {
                    methodToCheck.getScope().addVariable(var);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the variables from the current line.
     * @param checkNum Either 1 or 2, depending on if it's used in check one or check two.
     * @param currentMethod The current method we're in.
     * @return null if no variables from current line, a hashmap of varName:Variable hashmap if found variables.
     * @throws IllegalCodeException if there's an error with the line.
     */
    private static Map<String, Variable> getVariablesFromLine(int checkNum, Method currentMethod)
            throws IllegalCodeException{
        String type, restOfLine;
        boolean isFinal = false;
        String[] finalQualifierCheck = line.split(SPACE, TWO);
        if(finalQualifierCheck.length == 1) {
            return null;
        }
        if(finalQualifierCheck[0].equals(FINAL_QUALIFIER)) {
            String[] varNameSplit = finalQualifierCheck[1].split(SPACE, TWO);
            type = varNameSplit[0];
            restOfLine = varNameSplit[1];
            isFinal = true;
        }
        else {
            type = finalQualifierCheck[0];
            restOfLine = finalQualifierCheck[1];
        }
        if(VARIABLE_TYPES.contains(type)) {
            Map<String, Variable> result = new HashMap<>();
            restOfLine = restOfLine.substring(0, restOfLine.lastIndexOf(SEMICOLON));
            String[] variables = restOfLine.split(COMMA);
            for (String variable : variables) {
                String[] isInitialized = variable.split(EQUALS_SIGN);
                Variable newVar;
                String name = isInitialized[0].replaceAll(WHITESPACE, EMPTY_STRING);
                checkVarDeclaration(checkNum, currentMethod, name);
                switch(isInitialized.length) {
                    case NOT_INITIALIZED:
                        if(isFinal) {
                            throw new IllegalCodeException(FINAL_WITHOUT_ASSIGNMENT);
                        }
                        newVar = VariableFactory.createVariable(type, name, false, false);
                        result.put(newVar.getName(), newVar);
                        break;
                    case INITIALIZED:
                        newVar = VariableFactory.createVariable(type, name, isFinal, true);
                        //checking if the initialization is with another variable
                        String other = isInitialized[1].trim();
                        Variable otherVar = currentMethod.getScope().getVariable(other);
                        if (otherVar != null && otherVar.isInitialized()) {
                            VariableFactory.compareTypes(newVar, otherVar.checkType());
                        } else {
                            newVar.checkVariable(other);
                        }
                        result.put(newVar.getName(), newVar);
                        break;
                    default:
                        throw new IllegalCodeException(SYNTAX_ERROR);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * Checks if the given name is already defined in the given method's scope.
     * @param checkNum 1 if check one, 2 if check two.
     * @param method the current method we're checking.
     * @param name the name of the variable being declared.
     * @throws IllegalCodeException if it's already declared in the scope.
     */
     private static void checkVarDeclaration(int checkNum, Method method, String name)
            throws IllegalCodeException {
        if (checkNum == CHECK_TWO) {
            if (method.getScope().declarationVariable(name)) {
                throw new IllegalCodeException(VAR_ALREADY_EXISTS);
            }
        } else {
            if (globalScope.getVariable(name) != null) { // status means first trans if false.
                throw new IllegalCodeException(VAR_ALREADY_EXISTS);
            }
        }
    }

    /**
     * The second format check. Checks the inside of functions.
     * @throws IOException if there's an error reading from the file.
     * @throws IllegalCodeException if there's an error in the given code file.
     */
    private static void secondCheck() throws IOException, IllegalCodeException {
        getNextLine();
        while(line != null) {
            Matcher methodMatcher = VOID_PATTERN.matcher(line);
            if(!methodMatcher.find()) {
                getNextLine();
                continue;
            }
            String name = MethodFactory.getMethodName(line);
            checkMethod(methodNames.get(name));
            resetGlobalVariables();
            getNextLine();
        }
    }

    /**
     * Checks the inside of a method. To be called when line is a declaration of a method.
     * @param methodToCheck the method to be checked.
     * @throws IllegalCodeException if there's an error in the code inside the method.
     * @throws IOException if there's a problem reading from the file.
     */
    private static void checkMethod(Method methodToCheck) throws IllegalCodeException, IOException {
        getNextLine();
        Matcher returnMatcher;
        Matcher closeBracket;
        Matcher voidMatcher;
        while(line != null) {
            returnMatcher = RETURN_PATTERN.matcher(line);
            closeBracket = CLOSING_BRACKET_PATTERN.matcher(line);
            voidMatcher = VOID_PATTERN.matcher(line);
            /* Recursion break conditions */
            if(returnMatcher.matches()) {
                if (depth != 0) {
                    depth--;
                }
                checkNextLineCloses();
                return;
            }
            else if(closeBracket.matches()) {
                if(depth == 0) {
                    throw new IllegalCodeException(NO_RETURN_STATEMENT);
                }
                else {
                    return;
                }
            }
            /* Line checks */
            if(emptyCommentLine()) {
                getNextLine();
                continue;
            }
            else {
                line = line.trim();
            }
            if(checkVariableLine(CHECK_TWO, methodToCheck)) {
                getNextLine();
            }
            else if(voidMatcher.matches()) {
                throw new IllegalCodeException(NESTED_FUNCTION);
            }
            else if(checkWhileIf(methodToCheck)) {
                getNextLine();
            }
            else if(methodCalled(methodToCheck)) {
                getNextLine();
            }
            else if(checkVariableAssignment(CHECK_TWO, methodToCheck)) {
                getNextLine();
            }
            else {
                throw new IllegalCodeException(INVALID_LINE);
            }
        }
        throw new IllegalCodeException(NO_RETURN_STATEMENT);
    }

    /**
     * Checks if the line is a method called, and if it is checks it.
     * @return true if it's a method call, false otherwise.
     */
    private static boolean methodCalled(Method methodToCheck) throws IllegalCodeException {
        Matcher matcher = METHOD_CALL_PATTERN.matcher(line);
        if (matcher.matches()) {
            Method methodCalled = methodNames.get(getMethodCalled());
            if(methodCalled != null)
            {
                CheckMethodCall(methodNames.get(getMethodCalled()), methodToCheck.getScope());
            }
            else {
                throw new IllegalCodeException(METHOD_DOESNT_EXIST);
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the line is calling a method and then handles the call.
     * @param method The method that is being executed.
     * @param scope The scope we are currently in.
     * @throws IllegalCodeException If there's an error in the code.
     */
    private static void CheckMethodCall(Method method, Scope scope) throws IllegalCodeException {
        String innerLine = line.substring(line.indexOf(OPEN_BRACKET) + 1, line.indexOf(CLOSED_BRACKET));
        String[] variables = innerLine.split(COMMA);
        if(innerLine.isEmpty()) {
            if(!method.getVariableTypes().isEmpty()) {
                throw new IllegalCodeException(METHOD_ARGUMENTS_MISMATCH);
            }
        }
        else {
            if(variables.length != method.getVariableTypes().size()) {
                throw new IllegalCodeException(METHOD_ARGUMENTS_MISMATCH);
            }
        }
        int index = 0;
        for (int requiredType : method.getVariableTypes()) {
            variables[index] = variables[index].trim();
            Variable doesExist = scope.getVariable(variables[index]); // does the variable exist?
            if (doesExist != null) { // if yes, check types
                VariableFactory.compareTypes(doesExist, requiredType);
                continue;
            }
            VariableFactory.checkVariable(variables[index], requiredType);
            index++;
        }
    }

    /**
     * Gets the method name from a method call line.
     * @return The method name.
     */
    private static String getMethodCalled() {
        return line.substring(0, line.indexOf(OPEN_BRACKET)).trim();
    }

    /**
     * Checks if the current line is a while or an if line, and if it is checks it. (until the next return)
     * @param methodToCheck the method we're currently in.
     * @return true if it is an if/while line, false otherwise.
     * @throws IllegalCodeException if there's an error in the while/if block.
     * @throws IOException if there's a problem reading from file.
     */
    private static boolean checkWhileIf(Method methodToCheck) throws IllegalCodeException, IOException {
        Matcher matcher = WHILE_IF_PATTERN.matcher(line);
        if (matcher.matches()) {
            String innerLine = line.substring(line.indexOf(OPEN_BRACKET) + 1, line.indexOf(CLOSED_BRACKET));
            String[] variables = innerLine.split(AND_OR_CONDITIONS);
            for (int i = 0; i < variables.length; i++) {
                variables[i] = variables[i].trim();
                Variable var = methodToCheck.getScope().getVariable(variables[i]);
                if(var != null) {
                    if(var.isInitialized()) {
                        if(!var.isBoolean()) {
                            throw new IllegalCodeException(INVALID_BOOLEAN_CONDITION);
                        }
                    }
                    else {
                        throw new IllegalCodeException(UNINITIALIZED_CONDITION);
                    }
                }
                else {
                    Variable tempBool = new BooleanVariable();
                    tempBool.checkVariable(variables[i]);
                }
            }
            Method whileIfMethod = new Method(null, null, new Scope(methodToCheck.getScope()));
            depth++;
            checkMethod(whileIfMethod);
            return true;
        }
        return false;
    }

    /**
     * Checks if the next line is a line that fits the format of the '}' line.
     * @throws IllegalCodeException If it doesn't fit the format.
     * @throws IOException if there's a problem reading the file.
     */
    private static void checkNextLineCloses() throws IllegalCodeException, IOException {
        getNextLine();
        Matcher closeBracket = CLOSING_BRACKET_PATTERN.matcher(line);
        if(!closeBracket.matches()) {
            throw new IllegalCodeException(MISSING_CLOSING_BRACKET);
        }
    }

    /**
     * Resets the global variables to their initial initialization status.
     */
    private static void resetGlobalVariables() {
        for (Variable variable : globalScope.getAllVariables().values()) {
            variable.resetInit();
        }
    }

    /**
     * Reads the next line and updates it in the static line variable.
     * @throws IOException if a problem occurred when reading the file.
     */
    private static void getNextLine() throws IOException {
        line = reader.readLine();
    }

    /**
     * Checks if the current line is a comment line or an empty line.
     *
     * @return True if it is, false otherwise.
     */
    private static boolean emptyCommentLine() {
        Matcher emptyLine = EMPTY_LINE_PATTERN.matcher(line);
        Matcher comment = COMMENT_PATTERN.matcher(line);
        return emptyLine.matches() || comment.matches();
    }
}
