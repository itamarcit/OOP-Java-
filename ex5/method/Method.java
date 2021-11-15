package oop.ex5.method;

import java.util.ArrayList;

/**
 * Method class. Will be created for each method declaration in the code.
 */
public class Method {
    private final String name;
    private final ArrayList<Integer> variableTypes;
    private final Scope scope;

    /**
     * constructor
     * @param name given method name.
     * @param variableTypes the declaration line variables type.
     * @param scope a given method scope.
     */
    public Method(String name, ArrayList<Integer> variableTypes, Scope scope) {
        this.variableTypes = variableTypes;
        this.name = name;
        this.scope = scope;
    }

    /**
     * get method's name.
     * @return method's name.
     */
    public String getName() {
        return name;
    }

    /**
     * get method's scope.
     * @return method's scope.
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * get the method's variableTypes : the declaration line variablesTypes.
     * @return method's variableTypes.
     */
    public ArrayList<Integer> getVariableTypes() {
        return variableTypes;
    }

    /**
     * Adds a representation to the variable types arraylist.
     * @param representation the representation to add.
     */
    public void addVariableType(int representation) {
        variableTypes.add(representation);
    }


}
