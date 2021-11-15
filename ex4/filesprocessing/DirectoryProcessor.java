package filesprocessing;

import filesprocessing.exceptions.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The main class for the program. Run with "java filesprocessing.DirectoryProcessor sourcedir commandfile".
 */
public class DirectoryProcessor {
    private static final String IOEXCEPTION_MSG = "Cannot open the commands file.";
    private static final String BAD_SUBSECTION_MSG = "Bad subsection name.";
    private static final String BAD_FORMAT_MSG = "Bad format of the command file.";
    private static final String TYPE2_ERROR = "ERROR: ";
    private static final String INVALID_USAGE = "Program received an invalid amount of arguments.";
    private static final int ARG_AMOUNT = 2;
    private static final int SOURCE_DIRECTORY = 0;
    private static final int COMMANDS_FILE = 1;

    /**
     * The main function for the program.
     * @param args Arguments as specified.
     */
    public static void main(String[] args) {
        try {
            checkArgs(args);
        }
        catch(InvalidUsageException e) {
            System.err.println(e.getMessage());
            return;
        }
        File directory = new File(args[SOURCE_DIRECTORY]);
        File commandsFile = new File(args[COMMANDS_FILE]);
        ArrayList<File> directoryFiles = getAllFiles(directory);
        try {
            handleSegments(SegmentFactory.segmentFactory(commandsFile), directoryFiles);
        }
        catch(IOException e) {
            System.err.println(TYPE2_ERROR + IOEXCEPTION_MSG);
        }
        catch(BadSubSectionException e) {
            System.err.println(TYPE2_ERROR + BAD_SUBSECTION_MSG);
        }

        catch(BadFormatException e) {
            System.err.println(TYPE2_ERROR + BAD_FORMAT_MSG);
        }

    }

    /*
     * Checks the given command line arguments.
     * @param args CLI Arguments.
     * @throws InvalidUsageException if args don't fit the required criteria.
     */
    private static void checkArgs(String[] args) throws InvalidUsageException {
        if(args.length != ARG_AMOUNT) {
            throw new InvalidUsageException(TYPE2_ERROR + INVALID_USAGE);
        }
    }
    /**
     * Handles all segments. Filters and orders them, and then prints them as required.
     * @param segments an array list of all the segments.
     * @param allFiles an array list of all the files.
     */
    private static void handleSegments(ArrayList<Segment> segments, ArrayList<File> allFiles) {
        ArrayList<File> segmentFiles;
        for (Segment segment : segments) {
            segmentFiles = new ArrayList<>(allFiles);
            segment.filter(segmentFiles);
            segment.order(segmentFiles);
            segment.printSegment(segmentFiles);
        }
    }

    /*
     * Gets all the files in the directory into an array list of files.
     * @param directory The directory to get the files from.
     * @return A new array list of all the files in the directory.
     */
    private static ArrayList<File> getAllFiles(File directory) {
        File[] tempDirectoryFiles = directory.listFiles();
        ArrayList<File> newDirectoryFiles = new ArrayList<>();
        if(tempDirectoryFiles == null) {
            return null;
        }
        for (File file : tempDirectoryFiles) {
            if(file.isFile()) {
                newDirectoryFiles.add(file);
            }
        }
        return newDirectoryFiles;
    }
}