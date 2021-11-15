import java.util.TreeSet;
import java.util.HashSet;
import java.util.LinkedList;
/**
 * Runs tests to measure each database (TreeSet, LinkedList, HashSet, OpenHashSet, ClosedHashSet) runtime of different
 * actions.
 */
public class SimpleSetPerformanceAnalyzer {
    /* CONSTANTS */
    private static final int TREE_SET = 0;
    private static final int LINKED_LIST = 1;
    private static final int HASH_SET = 2;
    private static final int OPEN_HASH_SET = 3;
    private static final int CLOSED_HASH_SET = 4;
    private static final int MAX_TESTS = 5;
    private static final int TEST_THREE = 3;
    private static final int TEST_FOUR = 4;
    private static final int TEST_FIVE = 5;
    private static final int TEST_SIX = 6;
    private static final int TOTAL_WORDS = 99999;
    private static final int NANO_TO_MILLI = 1000000;
    private static final int PERCENT_HELPER = 10;
    private static final int PERCENT_HELPER_TWO = 10000;
    private static final int WARMUP = 70000;
    private static final int TEST_ITERATIONS = 70000;
    private static final int LINKED_LIST_ITERATIONS = 7000;
    private static final String TEST_FOUR_STR = "-13170890158";
    private static final String HI_STR = "hi";
    private static final String TEST_FIVE_STR = "23";
    private static final String DATA_PATH_ONE = "src/data1.txt";
    private static final String DATA_PATH_TWO = "src/data2.txt";

    /* ACTIVATORS - Turn to false to deactivate tests or databases */
    private static final boolean TREE_SET_ACTIVATOR = true;
    private static final boolean LINKED_LIST_ACTIVATOR = true;
    private static final boolean HASH_SET_ACTIVATOR = true;
    private static final boolean OPEN_HASH_SET_ACTIVATOR = true;
    private static final boolean CLOSED_HASH_SET_ACTIVATOR = true;
    private static final boolean TEST_ONE_ACTIVATOR = true;
    private static final boolean TEST_TWO_ACTIVATOR = true;
    private static final boolean TEST_THREE_ACTIVATOR = true;
    private static final boolean TEST_FOUR_ACTIVATOR = true;
    private static final boolean TEST_FIVE_ACTIVATOR = true;
    private static final boolean TEST_SIX_ACTIVATOR = true;

    /* STATIC VARIABLES */
    private static final SimpleSet[] array = new SimpleSet[MAX_TESTS];
    private static String[] stringArray = new String[TOTAL_WORDS];

    /**
     * The main function of this class. Runs all the tests as
     * @param args Does not expect any arguments.
     */
    public static void main(String[] args) {
        int totalTests = TREE_SET_ACTIVATOR ? 1 : 0;
        totalTests += LINKED_LIST_ACTIVATOR ? 1 : 0;
        totalTests += HASH_SET_ACTIVATOR ? 1 : 0;
        totalTests += OPEN_HASH_SET_ACTIVATOR ? 1 : 0;
        totalTests += CLOSED_HASH_SET_ACTIVATOR ? 1 : 0;
        int index;
        if(TREE_SET_ACTIVATOR) {
            TreeSet<String> tree = new TreeSet<>();
            index = getNextIndex();
            array[index] = new CollectionFacadeSet(tree);
            System.out.println("============== TREE SET TESTS ==============");
            runTests(index, TREE_SET);
        }
        if(LINKED_LIST_ACTIVATOR) {
            LinkedList<String> linkedList = new LinkedList<>();
            index = getNextIndex();
            array[index] = new CollectionFacadeSet(linkedList);
            System.out.println("============== LINKED LIST TESTS ==============");
            runTests(index, LINKED_LIST);
        }
        if(HASH_SET_ACTIVATOR) {
            HashSet<String> hashSet = new HashSet<>();
            index = getNextIndex();
            array[index] = new CollectionFacadeSet(hashSet);
            System.out.println("============== HASH SET TESTS ==============");
            runTests(index, HASH_SET);
        }
        if(OPEN_HASH_SET_ACTIVATOR) {
            OpenHashSet openHashSet = new OpenHashSet();
            index = getNextIndex();
            array[index] = openHashSet;
            System.out.println("============== OPEN HASH SET TESTS ==============");
            runTests(index, OPEN_HASH_SET);
        }
        if(CLOSED_HASH_SET_ACTIVATOR) {
            ClosedHashSet closedHashSet = new ClosedHashSet();
            index = getNextIndex();
            array[getNextIndex()] = closedHashSet;
            System.out.println("============== CLOSED HASH SET TESTS ==============");
            runTests(index, CLOSED_HASH_SET);
        }
        System.out.println("Tests finished. Total databases checked: " + totalTests + ".");
    }

    /*
     * Runs all the tests, according to the activators.
     * @param index of the database in the array.
     * @param type of database.
     */
    private static void runTests(int index, int type) {
        boolean filled = false;
        /* data1.txt tests */
        if(TEST_ONE_ACTIVATOR) {
            filled = true;
            testOne(index);
        }
        if(TEST_THREE_ACTIVATOR) {
            if(!filled && fillData(index, DATA_PATH_ONE)) {
                filled = true;
            }
            testThree(index, type);
        }
        if(TEST_FOUR_ACTIVATOR) {
            if(!filled && fillData(index, DATA_PATH_ONE)) {
                filled = true;
            }
            testFour(index, type);
        }
        if(filled) {
            resetData(index, type);
            filled = false;
        }
        /* data2.txt tests */
        if(TEST_TWO_ACTIVATOR) {
            testTwo(index);
            filled = true;
        }
        if(TEST_FIVE_ACTIVATOR) {
            if(!filled && fillData(index, DATA_PATH_TWO)) {
                filled = true;
            }
            testFive(index, type);
        }
        if(TEST_SIX_ACTIVATOR) {
            if(!filled) {
                fillData(index, DATA_PATH_TWO);
            }
            testSix(index, type);
        }
    }

    /*
     * Runs test five.
     * @param index of database in the array.
     */
    private static void testFive(int index, int type) {
        long difference;
        System.out.println("============== TEST 5 ==============");
        difference = containsTest(index, type, TEST_FIVE);
        System.out.println("Results: " + difference + " ns.");
    }

    /*
     * Runs test six.
     * @param index of database in the array.
     */
    private static void testSix(int index, int type) {
        long difference;
        System.out.println("============== TEST 6 ==============");
        difference = containsTest(index, type, TEST_SIX);
        System.out.println("Results: " + difference + " ns.");
    }

    /*
     * Runs test two
     * @param index of database in the array.
     */
    private static void testTwo(int index) {
        long timeBefore, difference;
        System.out.println("============== TEST 2 ==============");
        stringArray = Ex3Utils.file2array(DATA_PATH_TWO);
        if(stringArray == null) {
            System.out.println("Error reading file.");
            return;
        }
        timeBefore = System.nanoTime();
        addArray(index);
        System.out.println();
        difference = System.nanoTime() - timeBefore;
        System.out.println("Results: " + difference / NANO_TO_MILLI + " ms.");
    }

    /*
     * Runs tests one
     * @param index the index of the DB in the array
     */
    private static void testOne(int index) {
        long timeBefore, difference;
        System.out.println("============== TEST 1 ==============");
        stringArray = Ex3Utils.file2array(DATA_PATH_ONE);
        if(stringArray == null) {
            System.out.println("Error reading file.");
            return;
        }
        timeBefore = System.nanoTime();
        addArray(index);
        difference = System.nanoTime() - timeBefore;
        System.out.println("Results: " + difference / NANO_TO_MILLI + " ms.");
    }

    /*
     * Fills the data only if test 1 or test 2 is not selected to run.
     * @param index of the database in the array.
     * @param filePath a file path to read into the database.
     * @return true if successful, false otherwise.
     */
    private static boolean fillData(int index, String filePath) {
        stringArray = Ex3Utils.file2array(filePath);
        if(stringArray == null) {
            System.out.println("Error reading file.");
            return false;
        }
        System.out.println("Filling data..");
        for (int i = 0; i < TOTAL_WORDS; i++) {
            array[index].add(stringArray[i]);
        }
        return true;
    }

    /*
     * Adds the stringArray to the database. (helper for tests one and two)
     * @param index of database in the array.
     */
    private static void addArray(int index) {
        for (int i = 0; i < TOTAL_WORDS; i++) {
            array[index].add(stringArray[i]);
            if(i % PERCENT_HELPER_TWO == 0) {
                System.out.print(" - " + (i / PERCENT_HELPER_TWO)* PERCENT_HELPER + "% - ");
            }
        }
        System.out.println();
    }

    /*
     * Runs test three
     * @param index index of database in the array.
     */
    private static void testThree(int index, int type) {
        long difference;
        System.out.println("============== TEST 3 ==============");
        difference = containsTest(index, type, TEST_THREE);
        System.out.println("Results: " + difference + " ns.");
    }

    /*
     * Runs test four.
     * @param index index of database in the array.
     * @param type type of database to reset it afterwards.
     */
    private static void testFour(int index, int type) {
        long difference;
        System.out.println("============== TEST 4 ==============");
        difference = containsTest(index, type, TEST_FOUR);
        System.out.println("Results: " + difference + " ns.");
    }


    /*
     * Resets the data of the databases after finishing with data1.txt
     * @param index of database in the array.
     * @param type type of database.
     */
    private static void resetData(int index, int type) {
        switch(type) {
            case TREE_SET:
                TreeSet<String> tree = new TreeSet<>();
                array[index] = new CollectionFacadeSet(tree);
                break;
            case LINKED_LIST:
                LinkedList<String> linkedList = new LinkedList<>();
                array[index] = new CollectionFacadeSet(linkedList);
                break;
            case HASH_SET:
                HashSet<String> hashSet = new HashSet<>();
                array[index] = new CollectionFacadeSet(hashSet);
                break;
            case OPEN_HASH_SET:
                OpenHashSet openHashSet = new OpenHashSet();
                array[index] = openHashSet;
                break;
            case CLOSED_HASH_SET:
                ClosedHashSet closedHashSet = new ClosedHashSet();
                array[index] = closedHashSet;
                break;
        }
    }

    /*
     * The contains test (tests 3-6).
     * @param index of the database in the array.
     * @param type of database.
     * @param test number of test to run.
     * @return
     */
    private static long containsTest(int index, int type, int test) {
        long timeBefore, difference;
        if(type != LINKED_LIST) {
            containsTestHelper(index, test, WARMUP); // warmup
            timeBefore = System.nanoTime();
            containsTestHelper(index, test, TEST_ITERATIONS);
            difference = (System.nanoTime() - timeBefore) / TEST_ITERATIONS;
        }
        else {
            timeBefore = System.nanoTime();
            containsTestHelper(index, test, LINKED_LIST_ITERATIONS);
            difference = (System.nanoTime() - timeBefore) / LINKED_LIST_ITERATIONS;
        }
        return difference;
    }

    /*
     * A helper for the contains test.
     * @param index of the database in the array.
     * @param test number of test.
     * @param iterations the amount of iterations to go through.
     */
    private static void containsTestHelper(int index, int test, int iterations) {
        for (int i = 0; i < iterations; i++) {
            switch(test) {
                case TEST_THREE:
                case TEST_SIX:
                    array[index].contains(HI_STR);
                    break;
                case TEST_FOUR:
                    array[index].contains(TEST_FOUR_STR);
                    break;
                case TEST_FIVE:
                    array[index].contains(TEST_FIVE_STR);
                    break;
            }
        }
    }


    /*
     * Gets the next open index in the array.
     * @return The next open index. -1 if no test activators.
     */
    private static int getNextIndex() {
        if(array[TREE_SET] == null) {
            return TREE_SET;
        }
        else if(array[LINKED_LIST] == null) {
            return LINKED_LIST;
        }
        else if(array[HASH_SET] == null) {
            return HASH_SET;
        }
        else if(array[OPEN_HASH_SET] == null) {
            return OPEN_HASH_SET;
        }
        else if(array[CLOSED_HASH_SET] == null) {
            return CLOSED_HASH_SET;
        }
        return -1;
    }
}
