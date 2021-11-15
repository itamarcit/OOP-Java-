/**
 * A hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet.
 */
public class ClosedHashSet extends SimpleHashSet {
    /*
    The array for the hashset.
     */
    private String[] arr = new String[INITIAL_CAPACITY];
    /*
    The current capacity for the hashset.
     */
    private int capacity = INITIAL_CAPACITY;
    /*
    The current size for the hashset.
     */
    private int size = 0;
    /*
    If last contains() method returned true, it updated this variable to the index of the element.
     */
    private static int lastContainsIndex = -1;

    /* CONSTANTS */
    private static final boolean INCREASE = true;
    private static final boolean DECREASE = false;
    /*
    A constant to help with the quadratic probing.
    */
    private static final double CONSTANT_HASH = 2;
    /*
    The amount to multiply/divide each time you change the capacity.
     */
    private static final int LOAD_FACTOR_CHANGE = 2;
    /*
    A place in the memory which will be assigned to deleted strings from the hashset.
     */
    private static final String DELETED_REFERENCE = new String("");

    /**
     * Default Constructor
     */
    public ClosedHashSet() {
        super();
    }

    /**
     * Constructor with custom upper/lower load factors
     * @param upperLoadFactor the requested upper load factor
     * @param lowerLoadFactor the requested lower load factor
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        this();
        for (String str : data) {
            add(str);
        }
    }

    /* Public Methods */
    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False if newValue already exists in the set, true otherwise.
     */
    @Override
    public boolean add(String newValue) {
        if(contains(newValue)) {
            return false;
        }
        size++;
        handleSize(INCREASE);
        arr[hashFuncHelper(newValue)] = newValue;
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True if searchVal is found in the set, false otherwise.
     */
    @Override
    public boolean contains(String searchVal) {
        int index = hashFunc(searchVal);
        int originalIndex = index;
        for (int i = 0; i < capacity; i++) {
            index = (int)(originalIndex + ((i + (i * i)) / CONSTANT_HASH)) & (capacity - 1);
            if(arr[index] == null) {
                break;
            }
            else if(arr[index] == DELETED_REFERENCE) {
                continue;
            }
            if(arr[index].equals(searchVal)) {
                lastContainsIndex = index;
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return True if toDelete is found and deleted, false otherwise.
     */
    @Override
    public boolean delete(String toDelete) {
        if(!(contains(toDelete))) {
            return false;
        }
        arr[lastContainsIndex] = DELETED_REFERENCE;
        size--;
        handleSize(DECREASE);
        return true;
    }

    /**
     * Returns the number of actual objects held in the set, which is not necessarily equal to its storage capacity.
     * @return The number of elements currently in the set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the size of the storage space currently allocated for the set.
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * Clamps an index to fit the array of this hashset.
     * @param index The index before clamping.
     * @return The index after clamping
     */
    @Override
    public int clamp(int index) {
        int clampedIndex = index & (capacity - 1);
        return clampHelper(clampedIndex);
    }

    /* Private Methods */
    /*
    Checks if you need to add more space to the hashset, and if you do, forwards to other functions to do it.
     */
    private void handleSize(boolean type) {
        float newLoadFactor = (float) size / capacity;
        if(type == INCREASE) {
            if (newLoadFactor > getUpperLoadFactor()) {
                resizeTableClosed(type);
            }
        }
        else { // type == DECREASE
            if (capacity >= 1 && newLoadFactor < getLowerLoadFactor()) {
                resizeTableClosed(type);
            }
        }
    }

    /*
     * Resizes the table according to type and LOAD_FACTOR_CHANGE (Does not check if table needs to be resized)
     * @param type either increase or decrease size.
     */
    private void resizeTableClosed(boolean type) {
        String[] newHashSet;
        if(type == INCREASE) {
            capacity *= LOAD_FACTOR_CHANGE;
        }
        else { // type == DECREASE
            capacity /= LOAD_FACTOR_CHANGE;
        }
        newHashSet = new String[capacity];
        rehashTable(newHashSet, type);
    }

    /*
     * Rehashes the table into the given table
     * @param newContainer an array of StringLinkedList for the new array
     */
    private void rehashTable(String[] newContainer, boolean type) {
        int originalCapacity;
        if(type == INCREASE) {
            originalCapacity = capacity / LOAD_FACTOR_CHANGE;
        }
        else { // type == DECREASE
            originalCapacity = capacity * LOAD_FACTOR_CHANGE;
        }
        String[] temp = arr;
        arr = newContainer;
        for (int i = 0; i < originalCapacity; i++) {
            if(temp[i] != null && temp[i] != DELETED_REFERENCE) {
                newContainer[hashFuncHelper(temp[i])] = temp[i];
            }
        }
    }

    /*
    A function to help clamp the index normally, which will get you the next vacant index in the array.
    We also receive the original index, to change the AmountAfter variable accordingly to the number of iterations.
     */
    private int clampHelper(int index) {
        int iteration = 0;
        int originalIndex = index;
        while(arr[index] != null || arr[index] == DELETED_REFERENCE) {
            iteration++;
            index = (int)(originalIndex + ((iteration + (iteration * iteration)) / CONSTANT_HASH)) & (capacity - 1);
        }
        return index;
    }

    /*
     * Checks the hashcode for the string and then clamps it.
     * @param str Given string to check the index for, cannot be null.
     * @return the clamped index
     */
    private int hashFuncHelper(String str) {
        return clamp(str.hashCode());
    }

    /*
     * Performs the hash func to get the first (not necessarily available) spot.
     * @param str the string to perform the hash func on
     * @return the index as mentioned
     */
    private int hashFunc(String str) {
        return str.hashCode() & (capacity - 1);
    }
}
