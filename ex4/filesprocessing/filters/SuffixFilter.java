package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks for all files that contain a certain string in the end (suffix) of the file name.
 */
public class SuffixFilter implements Filter {
    private final String suffix;
    private final boolean not;

    /**
     * A constructor for the suffix filter.
     * @param suffix The prefix to check for.
     */
    public SuffixFilter(String suffix, boolean not) {
        this.suffix = suffix;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(!not) {
            fileList.removeIf(file -> !file.getName().endsWith(suffix));
        }
        else {
            fileList.removeIf(file -> file.getName().endsWith(suffix));
        }
    }
}
