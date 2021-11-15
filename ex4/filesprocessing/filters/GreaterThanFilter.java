package filesprocessing.filters;
import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks if a file is greater than a given size (in bytes).
 */
public class GreaterThanFilter implements Filter {
    private final double size;
    private final boolean not;

    /**
     * Constructor for the greater than filter.
     * @param size the size the filter will be based upon.
     */
    public GreaterThanFilter(double size, boolean not) {
        this.size = size;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(!not) {
            fileList.removeIf(file -> file.length() <= size);
        }
        else {
            fileList.removeIf(file -> file.length() > size);
        }
    }
}
