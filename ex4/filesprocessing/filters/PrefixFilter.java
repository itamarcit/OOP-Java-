package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks for all files that contain a certain string in the beginning (prefix) of the file name.
 */
public class PrefixFilter implements Filter {
    private final String prefix;
    private final boolean not;

    /**
     * A constructor for the prefix filter.
     * @param prefix The prefix to check for.
     */
    public PrefixFilter(String prefix, boolean not) {
        this.prefix = prefix;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(!not) {
            fileList.removeIf(file -> !file.getName().startsWith(prefix));
        }
        else {
            fileList.removeIf(file -> file.getName().startsWith(prefix));
        }
    }
}
