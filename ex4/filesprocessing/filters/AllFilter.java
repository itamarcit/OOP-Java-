package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter for the 'all' keyword.
 */
public class AllFilter implements Filter {
    private final boolean not;

    /**
     * A constructor for the all filter.
     * @param not true if the #NOT keyword was applied, false otherwise.
     */
    public AllFilter(boolean not) {
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(not) {
            fileList.clear();
        }
    }
}
