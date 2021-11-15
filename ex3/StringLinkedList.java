import java.util.LinkedList;
/**
 * My own implementation to linked list using delegation of collection's linked list.
 * (extends LinkedList of String type)
 */
public class StringLinkedList extends LinkedList<String>{
    /**
     * The delegated linked list.
     */
    private LinkedList<String> data = new LinkedList<>();
    @Override
    public boolean add(String str) {
        return data.add(str);
    }
    @Override
    public boolean remove(Object str) {
        return data.remove(str);
    }
    @Override
    public boolean contains(Object str) {
        return data.contains(str);
    }
    @Override
    public int size() {
        return data.size();
    }
    LinkedList<String> data() {
        return data;
    }
}
