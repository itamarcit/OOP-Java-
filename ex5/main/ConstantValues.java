package oop.ex5.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * A class used to store all the program's constants.
 */
public final class ConstantValues {
    /**
     * No constructor for this class as it's only for constants.
     */
    private ConstantValues() {}

    /* Regex Constants */
    public static final String AND_OR_CONDITIONS = "(?:[|][|])|(?:[&][&])";
    public static final String VOID = "^\\s*void";
    public static final Pattern VOID_PATTERN = Pattern.compile(VOID);
    public static final String EMPTY_STRING = "";
    public static final String WHITESPACE = "\\s";
    public static final String SPACE = "\\s+";
    public static final String COMMA = "\\s*,\\s*";
    public static final String EQUALS_SIGN = "=";
    public static final String SEMICOLON = ";";
    public static final String STRING_VARIABLE_REGEX = "^\".*\"$";
    public static final String CHAR_VARIABLE_REGEX = "^'.'$";
    public static final String INT_VARIABLE_REGEX = "^-?\\d+$";
    public static final String DOUBLE_VARIABLE_REGEX = "^-?\\d+[.]\\d+$|(?:" + INT_VARIABLE_REGEX + ")";
    public static final String BOOLEAN_VARIABLE_REGEX = "^\\s*(?:true|false)\\s*$|(?:" + DOUBLE_VARIABLE_REGEX + ")";
    public static final Pattern STRING_VARIABLE_PATTERN = Pattern.compile(STRING_VARIABLE_REGEX);
    public static final Pattern CHAR_VARIABLE_PATTERN = Pattern.compile(CHAR_VARIABLE_REGEX);
    public static final Pattern INT_VARIABLE_PATTERN = Pattern.compile(INT_VARIABLE_REGEX);
    public static final Pattern DOUBLE_VARIABLE_PATTERN = Pattern.compile(DOUBLE_VARIABLE_REGEX);
    public static final Pattern BOOLEAN_VARIABLE_PATTERN = Pattern.compile(BOOLEAN_VARIABLE_REGEX);
    public static final String EMPTY_LINE_REGEX = "^\\s*$";
    public static final String COMMENT_REGEX = "^[/][/].*";
    public static final Pattern EMPTY_LINE_PATTERN = Pattern.compile(EMPTY_LINE_REGEX);
    public static final Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REGEX);
    public static final String OPENING_BRACKET_REGEX = "\\s*[{]\\s*$";
    public static final String CLOSING_BRACKET_REGEX = "\\s*}\\s*";
    public static final Pattern OPENING_BRACKET_PATTERN = Pattern.compile(OPENING_BRACKET_REGEX);
    public static final Pattern CLOSING_BRACKET_PATTERN = Pattern.compile(CLOSING_BRACKET_REGEX);
    public static final String METHOD_DECLARATION_REGEX = "^\\s*void\\s+(?:[a-zA-Z]\\w*)\\s*[(]\\s*(?:final)?\\s*(?:" +
            "(?:int|boolean|double|char|String)\\s+(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+)))?(?:(?:\\s*,\\s*(?:final)?\\s*" +
            "(?:int|boolean|double|char|String)\\s+(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+))))*\\s*[)]\\s*[{]\\s*$";
    public static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile(METHOD_DECLARATION_REGEX);
    public static final String METHOD_CALL_REGEX = "^\\s*[a-zA-Z]\\w*\\s*[(].*[)]\\s*[;]\\s*$";
    public static final Pattern METHOD_CALL_PATTERN = Pattern.compile(METHOD_CALL_REGEX);
    public static final String WHILE_IF_REGEX = "^\\s*(?:while|if)\\s*[(]\\s*(?:(?:true|false)|(?:\\d+[.]\\d+)|" +
            "(?:\\d+)|(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+)))\\s*(?:(?:&&|[|][|])\\s*(?:(?:true|false)|(?:\\d+[.]\\d+)|" +
            "(?:\\d+)|(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+)))\\s*)*\\s*[)]\\s*[{]\\s*$";
    public static final Pattern WHILE_IF_PATTERN = Pattern.compile(WHILE_IF_REGEX);
    public static final String RETURN_REGEX = "^\\s*return;\\s*$";
    public static final Pattern RETURN_PATTERN = Pattern.compile(RETURN_REGEX);
    /**
     * Checks if the line fits the format of a variable declaration, and if the variable names are legal.
     */
    public static final String VARIABLE_INITIALIZATION = "^\\s*(?:final)?\\s*(?:int|double|char|boolean|String)\\s*" +
            "(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+))\\s*(?:=\\s*[^\\s,]+)*\\s*(?:,\\s*(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+))\\s*" +
            "(?:=\\s*[^\\s,]+)*\\s*)*[;]\\s*$";
    public static final Pattern VARIABLE_LINE_PATTERN = Pattern.compile(VARIABLE_INITIALIZATION);
    /**
     * Checks if the line fits the format of a variable assignment (variable names, divided by commas and ends with
     * a semicolon)
     */
    public static final String VARIABLE_ASSIGNMENT_REGEX = "^\\s*(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+))\\s*=\\s*[^,\\s]+" +
            "\\s*(?:,\\s*(?:(?:[a-zA-Z]\\w*)|(?:[_]\\w+))\\s*=\\s*[^,\\s]+)*\\s*;\\s*$";
    public static final Pattern VARIABLE_ASSIGNMENT_PATTERN = Pattern.compile(VARIABLE_ASSIGNMENT_REGEX);
    public static final String FINAL_QUALIFIER_REGEX = "^\\s*final";
    public static final Pattern FINAL_QUALIFIER_PATTERN = Pattern.compile(FINAL_QUALIFIER_REGEX);

    /* Error Messages Constants */
    public static final String INVALID_TYPE_MSG = "Error: Invalid type of a variable.";
    public static final String SYNTAX_ERROR = "Error: Syntax error.";
    public static final String VAR_ALREADY_EXISTS = "Error: Variable name already exists in the scope.";
    public static final String METHOD_DECLARATION_ERROR = "Error: Method declaration not in the correct format.";
    public static final String METHOD_MISSING_BRACKET = "Error: Method missing closing bracket.";
    public static final String VAR_DOESNT_EXIST = "Error: Assignment of a variable that doesn't exist.";
    public static final String FINAL_VAR_ASSIGNMENT = "Error: Assignment of a final variable.";
    public static final String INVALID_LINE = "Error: Line doesn't match any valid lines.";
    public static final String MISSING_CLOSING_BRACKET = "Error: Missing } after return.";
    public static final String NO_RETURN_STATEMENT = "Error: Missing return statement in a method.";
    public static final String NESTED_FUNCTION = "Error: Nested functions are illegal.";
    public static final String INVALID_BOOLEAN_CONDITION = "Error: Invalid boolean condition in while/if.";
    public static final String UNINITIALIZED_CONDITION = "Error: Uninitialized condition in while/if.";
    public static final String METHOD_ARGUMENTS_MISMATCH = "Error: Wrong number of parameters in method call.";
    public static final String METHOD_ARGUMENT_TYPE_ERROR = "Error: Wrong type of parameter in method call.";
    public static final String METHOD_DOESNT_EXIST = "Error: Called a method which doesn't exist.";
    public static final String IO_MSG = "Error: Could not read file.";
    public static final String INVALID_CLI_ARGS = "Error: Invalid CLI arguments.";
    public static final String FINAL_WITHOUT_ASSIGNMENT = "Error: Declaration of a final without value.";
    public static final String SAME_VAR_NAME_IN_METHOD = "Error: Method has the same variable names in declaration.";
    public static final String TYPE_MISMATCH = "Error: Types do not match.";
    public static final String ILLEGAL_VARIABLE_VALUE = "Error: Illegal variable value.";


    /* Other constants */
    public static final int INT_REPRESENTATION = 0;
    public static final int DOUBLE_REPRESENTATION = 1;
    public static final int CHAR_REPRESENTATION = 2;
    public static final int STRING_REPRESENTATION = 3;
    public static final int BOOLEAN_REPRESENTATION = 4;
    public static final String INT = "int";
    public static final String DOUBLE = "double";
    public static final String STRING = "String";
    public static final String CHAR = "char";
    public static final String BOOLEAN = "boolean";
    public static final HashSet<String> VARIABLE_TYPES = new HashSet<>(Arrays.asList(BOOLEAN, DOUBLE, INT,
            STRING, CHAR));
    public static final int NOT_INITIALIZED = 1;
    public static final int INITIALIZED = 2;
    public static final String FINAL_QUALIFIER = "final";
    public static final char OPEN_BRACKET = '(';
    public static final char CLOSED_BRACKET = ')';
    public static final int CHECK_ONE = 1;
    public static final int CHECK_TWO = 2;
    public static final String GLOBAL_METHOD = "Global Method";
    public static final int TWO = 2;
    public static final int REQUIRED_ARG_NUM = 1;
}
