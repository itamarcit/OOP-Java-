package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * An interface that all filters implement.
 */
public interface Filter {
    /**
     * Applies the filter to the list, removing elements that do not fit the filter's criteria.
     */
    void applyFilter(ArrayList<File> fileList);
}
