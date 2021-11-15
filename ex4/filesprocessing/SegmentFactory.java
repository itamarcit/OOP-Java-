package filesprocessing;

import filesprocessing.exceptions.*;
import filesprocessing.filters.*;
import filesprocessing.orders.*;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A factory-designed class responsible for creating segments, which consist of filters, orders, and warnings.
 */
public class SegmentFactory {
    private static final ArrayList<Integer> warnings = new ArrayList<>();
    private static int lineIndex = 0;
    private static String currentLine;
    private static String filter;
    private static String order;
    private static String argumentOne;
    private static String argumentTwo;
    private static boolean not = false;
    private static boolean reverse = false;
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String NOT = "NOT";
    private static final String ABS_SORTING = "abs";
    private static final String TYPE_SORTING = "type";
    private static final String SIZE_SORTING = "size";
    private static final String REVERSE = "REVERSE";
    private static final String DEFAULT_FILTER = "FILTER";
    private static final String DEFAULT_ORDER = "ORDER";
    private static final String HASHTAG = "#";
    private static final int TYPE = 0;
    private static final String HIDDEN_FILTER = "hidden";
    private static final String EQUAL_FILTER = "file";
    private static final String PREFIX_FILTER = "prefix";
    private static final String SUFFIX_FILTER = "suffix";
    private static final String ALL_FILTER = "all";
    private static final String WRITABLE_FILTER = "writable";
    private static final String EXECUTABLE_FILTER = "executable";
    private static final String GREATER_THAN_FILTER = "greater_than";
    private static final String BETWEEN_FILTER = "between";
    private static final String CONTAINS_FILTER = "contains";
    private static final String SMALLER_THAN_FILTER = "smaller_than";
    private static final int FIRST_ARGUMENT = 1;
    private static final int SECOND_ARGUMENT = 2;
    private static final int THIRD_ARGUMENT = 3;
    private static final int BYTES_TO_KBS = 1024;
    private static final HashSet<String> LEGAL_FILTER_PREFIX = new HashSet<>(Arrays.asList("greater_than", "between",
            "smaller_than", "file", "contains", "prefix", "suffix", "writable", "executable", "hidden", "all"));
    private static final HashSet<String> LEGAL_ORDER_PREFIX = new HashSet<>(Arrays.asList("abs", "type", "size"));

    /**
     * Creates all the segments based on the command file.
     * @param commandFile The command file.
     * @return an array list of the segments.
     * @throws IOException if there's a problem reading the file.
     * @throws BadSubSectionException if there's a problem with the subsection name.
     * @throws BadFormatException if there's a problem with the command file format.
     */
    static ArrayList<Segment> segmentFactory(File commandFile) throws IOException, BadSubSectionException,
            BadFormatException {
        ArrayList<Segment> segments = new ArrayList<>();
        FileReader fileReader = new FileReader(commandFile);
        BufferedReader bufferedReader= new BufferedReader(fileReader);
        getNextLine(bufferedReader);
        String[] filterArgs;
        while(currentLine != null) {
            if (currentLine.equals(DEFAULT_FILTER)) {
                try {
                    getNextLine(bufferedReader);
                }
                catch(NullPointerException e) {

                }
            } else {
                bufferedReader.close();
                throw new BadSubSectionException(null); // type 2
            }
            filterArgs = currentLine.split(HASHTAG);
            try {
                checkFilterType(filterArgs[TYPE]);
                switch(filter) {
                    case HIDDEN_FILTER:
                    case WRITABLE_FILTER:
                    case EXECUTABLE_FILTER:
                        getFilterBooleanArguments(filterArgs);
                        break;
                    case GREATER_THAN_FILTER:
                    case SMALLER_THAN_FILTER:
                        getFilterNumberArguments(filterArgs);
                        break;
                    case BETWEEN_FILTER:
                        getBetweenArguments(filterArgs);
                        break;
                    case ALL_FILTER:
                        if(filterArgs.length > 1 && filterArgs[FIRST_ARGUMENT].equals(NOT)) {
                            not = true;
                        }
                        break;
                    default:
                        getFilterStringArguments(filterArgs);
                }
            }
            catch(TypeOneExceptions e) {
                warnings.add(lineIndex);
                filter = ALL_FILTER;
            }
            getNextLine(bufferedReader);
            if(currentLine.equals(DEFAULT_ORDER)) {
                getNextLine(bufferedReader);
            }
            else {
                bufferedReader.close();
                throw new BadFormatException(null);
            }
            if(currentLine == null || currentLine.equals(DEFAULT_FILTER)) {
                order = ABS_SORTING;
                segments.add(createSegment());
                reset();
                continue;
            }
            String[] orderArgs = currentLine.split(HASHTAG);
            try {
                checkOrderType(orderArgs);
            }
            catch(TypeOneExceptions e) {
                warnings.add(lineIndex);
                order = ABS_SORTING;
            }
            segments.add(createSegment());
            reset();
            getNextLine(bufferedReader);
        }
        return segments;
    }

    /*
     * Resets the static variables for a new segment.
     */
    private static void reset() {
        warnings.clear();
        reverse = false;
        not = false;
    }

    /*
     * Gets the next line from the reader and updates the line index.
     * @param reader The reader to read from.
     * @throws IOException If the reading isn't successful.
     */
    private static void getNextLine(BufferedReader reader) throws IOException {
        currentLine = reader.readLine();
        lineIndex++;
    }

    /*
     * Creates a single segment using the static variables.
     * @return the created segment.
     */
    private static Segment createSegment() {
        Order order = createOrder();
        Filter filter = createFilter();
        return new Segment(filter, order, warnings);
    }

    /*
     * Creates a new order.
     * @return The new created order.
     */
    private static Order createOrder() {
        Order newOrder = null;
        switch(order) {
            case ABS_SORTING:
                newOrder = new AbsoluteOrder(reverse);
                break;
            case TYPE_SORTING:
                newOrder = new TypeOrder(reverse);
                break;
            case SIZE_SORTING:
                newOrder = new SizeOrder(reverse);
                break;
        }
        return newOrder;
    }

    /*
     * Creates a new filter.
     * @return The created filter.
     */
    private static Filter createFilter() {
        Filter newFilter = null;
        switch(filter) {
            case ALL_FILTER:
                newFilter = new AllFilter(not);
                break;
            case BETWEEN_FILTER:
                newFilter = new BetweenFilter(Double.parseDouble(argumentOne), Double.parseDouble(argumentTwo), not);
                break;
            case CONTAINS_FILTER:
                newFilter = new ContainsFilter(argumentOne, not);
                break;
            case EXECUTABLE_FILTER:
                newFilter = new ExecutableFilter(argumentOne.equals(YES), not);
                break;
            case GREATER_THAN_FILTER:
                newFilter = new GreaterThanFilter(Double.parseDouble(argumentOne), not);
                break;
            case HIDDEN_FILTER:
                newFilter = new HiddenFilter(argumentOne.equals(YES), not);
                break;
            case EQUAL_FILTER:
                newFilter = new NameEqualsFilter(argumentOne, not);
                break;
            case PREFIX_FILTER:
                newFilter = new PrefixFilter(argumentOne, not);
                break;
            case SUFFIX_FILTER:
                newFilter = new SuffixFilter(argumentOne, not);
                break;
            case SMALLER_THAN_FILTER:
                newFilter = new SmallerThanFilter(Double.parseDouble(argumentOne), not);
                break;
            case WRITABLE_FILTER:
                newFilter = new WritableFilter(argumentOne.equals(YES), not);
                break;
        }
        return newFilter;
    }

    /*
     * Checks if the order type is valid, and saves it.
     * @param orderType a split string from the ORDER subsection.
     * @throws BadNamingException if the order type is invalid.
     */
    private static void checkOrderType(String[] orderType) throws BadNamingException {
        if(LEGAL_ORDER_PREFIX.contains(orderType[TYPE])) {
            order = orderType[TYPE];
            if(orderType.length > 1 && orderType[FIRST_ARGUMENT].equals(REVERSE)) {
                reverse = true;
            }
        }
        else {
            throw new BadNamingException(null);
        }
    }

    /*
     * Checks if the filter type is valid, and saves it.
     * @param filterType a string from the FILTER subsection (the part until the first #).
     * @throws BadNamingException if the type is invalid.
     */
    private static void checkFilterType(String filterType) throws BadNamingException {
        if (LEGAL_FILTER_PREFIX.contains(filterType)) {
            filter = filterType;
        }
        else {
            throw new BadNamingException(null);//todo
        }
    }

    /*
     * Gets the arguments from every boolean-type filter.
     * @param args a list of split strings from the filter subsection.
     * @throws NonBooleanException If the boolean part is  not boolean ("YES" or "NO")
     */
    private static void getFilterBooleanArguments(String[] args) throws NonBooleanException {
        if(args[FIRST_ARGUMENT] != null && (args[FIRST_ARGUMENT].equals(YES) || args[FIRST_ARGUMENT].equals(NO))) {
            argumentOne = args[FIRST_ARGUMENT];
        }
        else {
            throw new NonBooleanException(null);
        }
    }

    /*
     * Gets the arguments from every string-type filter.
     * @param args a list of split strings from the filter subsection.
     */
    private static void getFilterStringArguments(String[] args) {
        argumentOne = args[FIRST_ARGUMENT];
        if(args.length > SECOND_ARGUMENT && args[SECOND_ARGUMENT].equals(NOT)) {
            not = true;
        }
    }

    /*
     * Gets the arguments from every number-type filter.
     * @param args a list of split strings from the filter subsection.
     * @throws InvalidNumberException If the number given is negative.
     */
    private static void getFilterNumberArguments(String[] args) throws InvalidNumberException {
        if(args[FIRST_ARGUMENT] != null && Double.parseDouble(args[FIRST_ARGUMENT]) > 0) {
            argumentOne = String.valueOf(Double.parseDouble(args[FIRST_ARGUMENT]) * BYTES_TO_KBS);
        }
        else {
            throw new InvalidNumberException(null);
        }
        if(args.length > SECOND_ARGUMENT && args[SECOND_ARGUMENT].equals(NOT)) {
            not = true;
        }
    }

    /*
     * Gets the arguments from between type filters.
     * @param args a list of split strings from the filter subsection.
     * @throws BadBetweenValueException if the range is invalid.
     */
    private static void getBetweenArguments(String[] args) throws BadBetweenValueException {
        if(args[FIRST_ARGUMENT] != null && Double.parseDouble(args[FIRST_ARGUMENT]) > 0 &&
                Double.parseDouble(args[SECOND_ARGUMENT]) > 0) {
            argumentOne = String.valueOf(Double.parseDouble(args[FIRST_ARGUMENT]) * BYTES_TO_KBS);
            argumentTwo = String.valueOf(Double.parseDouble(args[SECOND_ARGUMENT]) * BYTES_TO_KBS);
        }
        else {
            throw new BadBetweenValueException(null);
        }
        if(args.length > THIRD_ARGUMENT && args[THIRD_ARGUMENT].equals(NOT)) {
            not = true;
        }
    }
}
