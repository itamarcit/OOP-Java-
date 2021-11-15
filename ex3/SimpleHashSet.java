/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 * Specifically a super class for OpenHashSet and ClosedHashSet in this exercise.
 */
public abstract class SimpleHashSet implements SimpleSet {
    /* Constants */
    /**
     * The default upper load factor for a hashset
     */
    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;
    /**
     * The default lower load factor for a hashset
     */
    protected static float DEFAULT_LOWER_CAPACITY = 0.25f;
    /**
     * The initial capacity for a hashset
     */
    protected static int INITIAL_CAPACITY = 16;

    /* Private Members */

    private float higherCapacity = DEFAULT_HIGHER_CAPACITY;

    private float lowerCapacity = DEFAULT_LOWER_CAPACITY;

    /**
     * Default Constructor
     */
    protected SimpleHashSet() {

    }

    /**
     * Constructor with custom upper/lower load factors
     * @param upperLoadFactor the requested upper load factor
     * @param lowerLoadFactor the requested lower load factor
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        higherCapacity = upperLoadFactor;
        lowerCapacity = lowerLoadFactor;
    }

    /**
     * Returns the size of the storage space currently allocated for the set.
     * @return The current capacity (number of cells) of the table.
     */
    abstract int capacity();

    /**
     * Getter for lower load factor of the table.
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor() {
        return lowerCapacity;
    }

    /**
     * Getter for upper load factor of the table.
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor() {
        return higherCapacity;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index The index before clamping.
     * @return An index properly clamped.
     */
    protected abstract int clamp(int index);
}
