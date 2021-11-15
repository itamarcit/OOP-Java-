import java.util.Collection;
import java.util.Collections;

/**
 * Wraps an underlying Collection of strings and serves to both simplify its API and give it a common type
 * with the implemented SimpleHashSets. Therefore it implements SimpleSet.
 */
public class CollectionFacadeSet  implements SimpleSet {
    /**
     * The warped object by this facade.
     */
    protected Collection<String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap.
     */
    public CollectionFacadeSet(Collection<String> collection) {
        Object[] temp = collection.toArray();
        this.collection = collection;
        this.collection.clear();
        for (Object elem : temp) {
            if(elem != null) {
                this.add((String) elem);
            }
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False if newValue already exists in the set, true otherwise.
     */
    @Override
    public boolean add(String newValue) {
        if(newValue == null || contains(newValue)) {
            return false;
        }
        return collection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True if searchVal is found in the set, false otherwise.
     */
    @Override
    public boolean contains(String searchVal) {
        if(searchVal == null) {
            return false;
        }
        return collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return True if toDelete is found and deleted, false otherwise.
     */
    @Override
    public boolean delete(String toDelete) {
        if(toDelete != null) {
            return collection.remove(toDelete);
        }
        return false;
    }

    /**
     * Returns the number of actual objects held in the set, which is not necessarily equal to its storage capacity.
     * @return The number of elements currently in the set.
     */
    @Override
    public int size() {
        return collection.size();
    }

}
