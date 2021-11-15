/**
 * A hash-set based on chaining. Extends SimpleHashSet.
 */
public class OpenHashSet extends SimpleHashSet {

    /*
    The container for the hashset.
     */
    private StringLinkedList[] arr = new StringLinkedList [INITIAL_CAPACITY];
    /*
    The current capacity for the hashset.
     */
    private int capacity = INITIAL_CAPACITY;

    /*
    The current size for the hashset.
     */
    private int size = 0;

    /* Constants */
    private static final boolean INCREASE = true;
    private static final boolean DECREASE = false;
    /*
    The amount to multiply/divide each time you change the capacity.
     */
    private static final int LOAD_FACTOR_CHANGE = 2;

    /**
     * Default Constructor
     */
    public OpenHashSet() {
        super();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            arr[i] = new StringLinkedList();
        }
    }

    /**
     * Constructor with custom upper/lower load factors
     * @param upperLoadFactor the requested upper load factor
     * @param lowerLoadFactor the requested lower load factor
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        for (int i = 0; i < capacity; i++) {
            arr[i] = new StringLinkedList();
        }
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {
        this();
        for (String str : data) {
            if(!contains(str)) {
                add(str);
            }
        }
    }

    /* Public Methods */
    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False if newValue already exists in the set, true otherwise.
     */
    public boolean add(String newValue) {
        if(contains(newValue)) {
            return false;
        }
        size++;
        handleSize(INCREASE);
        arr[stringHashFunc(newValue)].add(newValue);
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True if searchVal is found in the set, false otherwise.
     */
    public boolean contains(String searchVal) {
        for (String str : arr[stringHashFunc(searchVal)].data()) {
            if(str.equals(searchVal)) {
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
    public boolean delete(String toDelete) {
        if(!arr[stringHashFunc(toDelete)].remove(toDelete)) {
            return false;
        }
        size--;
        handleSize(DECREASE);
        return true;
    }

    /**
     * Returns the number of actual objects held in the set, which is not necessarily equal to its storage capacity.
     * @return The number of elements currently in the set.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the size of the storage space currently allocated for the set.
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Clamps an index to fit the array of this hashset.
     * @param index The index before clamping.
     * @return The index after clamping
     */
    @Override
    protected int clamp(int index) {
        return index & (capacity - 1);
    }

    /* Private Methods */
    /*
     * Wrapper for the hashCode function
     * @param str Given string to get the hash code for
     * @return The hash code
     */
    private int stringHashFunc(String str) {
        return clamp(str.hashCode());
    }

    /*
     * Resizes the table according to type and LOAD_FACTOR_CHANGE (Does not check if table needs to be resized)
     * @param type either increase or decrease size
     */
    private void resizeTableOpen(boolean type) {
        StringLinkedList[] newHashSet;
        if(type == INCREASE) {
            newHashSet = new StringLinkedList[capacity * LOAD_FACTOR_CHANGE];
            for (int i = 0; i < capacity * LOAD_FACTOR_CHANGE; i++) {
                newHashSet[i] = new StringLinkedList();
            }
            capacity *= LOAD_FACTOR_CHANGE;
        }
        else { // type == DECREASE
            newHashSet = new StringLinkedList[capacity / LOAD_FACTOR_CHANGE];
            for (int i = 0; i < capacity / LOAD_FACTOR_CHANGE; i++) {
                newHashSet[i] = new StringLinkedList();
            }
            capacity /= LOAD_FACTOR_CHANGE;
        }
        rehashTable(newHashSet, type);
        arr = newHashSet;
    }

    /*
     * Rehashes the table into the given table
     * @param newContainer an array of StringLinkedList for the new array
     */
    private void rehashTable(StringLinkedList[] newContainer, boolean type) {
        int originalCapacity;
        if(type == INCREASE) {
            originalCapacity = capacity / LOAD_FACTOR_CHANGE;
        }
        else { // type == DECREASE
            originalCapacity = capacity * LOAD_FACTOR_CHANGE;
        }
        for (int i = 0; i < originalCapacity; i++) {
            for (String str : arr[i].data()) {
                newContainer[stringHashFunc(str)].add(str);
            }
        }
    }

    /*
     * Checks if you can add an element to the table without adding more capacity, and if needed adds more space.
     * @param type type of check, if you wish to decrease or increase.
     */
    private void handleSize(boolean type) {
        float currLoadFactor = (float) size / capacity;
        if(type == INCREASE) {
            if (currLoadFactor > getUpperLoadFactor()) {
                resizeTableOpen(type);
            }
        }
        else { // type == DECREASE
            if (capacity >= 1 && currLoadFactor < getLowerLoadFactor()) {
                resizeTableOpen(type);
            }
        }
    }
}
