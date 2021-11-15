package oop.ex5.main;

import java.io.*;

import static oop.ex5.main.ConstantValues.*;

/**
 * The main method of the program. Run with "java oop.ex5.main.Sjavac source_file_name".
 */
public class Sjavac {
    private static final int LEGAL_CODE = 0;
    private static final int ILLEGAL_CODE = 1;
    private static final int IO_ERROR = 2;

    /**
     * Main method to run the program.
     * @param args should contain the file path in args[0] and nothing more.
     */
    public static void main(String[] args) {
        if(!checkArgs(args)) {
            System.err.println(INVALID_CLI_ARGS);
            System.out.println(IO_ERROR);
            return;
        }
        try {
            String filePath = args[0];
            FormatChecker.checkCode(filePath);
        }
        catch (IllegalCodeException e)
        {
            System.err.println(e.getMessage());
            System.out.println(ILLEGAL_CODE);
            return;
        }
        catch(IOException e) {
            handleIO();
            return;
        }
        System.out.println(LEGAL_CODE);
    }

    /**
     * Handles IO messages in case of an IO error.
     */
    private static void handleIO() {
        System.err.println(IO_MSG);
        System.out.println(IO_ERROR);
    }

    /**
     * Checks if the arguments fit the requirement.
     * @param args an array of strings. should contain only one string.
     * @return true if it fits the requirement, false otherwise.
     */
    private static boolean checkArgs(String[] args) {
        return args.length == REQUIRED_ARG_NUM;
    }
}