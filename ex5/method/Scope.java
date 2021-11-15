package oop.ex5.method;

import oop.ex5.variables.Variable;

import java.util.HashMap;

/**
 * A scope class. an instance of method
 * holds all variable of current scope, and all upper scope - meaning all method's scopes.
 */
public class Scope {
    private final Scope upperScope;
    private final HashMap<String, Variable> variables;

    /**
     * constructor for empty scope - gets only an upper scope and creates an empty variables list
     * @param upper given upper scope
     */
    public Scope(Scope upper)
    {
        variables = new HashMap<>();
        upperScope = upper;
    }

    /**
     * Adds a variable to the scope.
     * @param variable a variable to be added.
     */
    public void addVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }

    /**
     * Getter for the upper scope.
     * @return The scope above this scope.
     */
    public Scope getUpperScope() {
        return upperScope;
    }

    /**
     * Checks if the given variable name can be declared in this scope.
     * @param name The name of a variable.
     * @return True if it can, false otherwise.
     */
    public boolean declarationVariable(String name)
    {
        return variables.containsKey(name);
    }

    /**
     * Gets the variable from the scope or from upper scopes if exists.
     * @param name The name of the variable.
     * @return The variable object, null if doesn't exist.
     */
    public Variable getVariable(String name)
    {
        Scope temp = this;
        while(temp != null) {
            if(temp.variables.containsKey(name)) {
                return temp.variables.get(name);
            }
            temp = temp.getUpperScope();
        }
        return null;
    }

    /**
     * Getter for all the variables.
     * @return a hashmap of all the variables in the scope, where the keys are strings of the names and the values are
     * the actual variables.
     */
    public HashMap<String, Variable> getAllVariables() {
        return variables;
    }
}
