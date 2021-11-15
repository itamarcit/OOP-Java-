package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks if the given string value is identical to a file name. ('file' filter name)
 */
public class NameEqualsFilter implements Filter {
    private final String name;
    private final boolean not;

    /**
     * Constructor for the name filter.
     * @param name Name to check for.
     */
    public NameEqualsFilter(String name, boolean not) {
        this.name = name;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(!not) {
            fileList.removeIf(file -> !file.getName().equals(name));
        }
        else {
            fileList.removeIf(file -> file.getName().equals(name));
        }
    }
}
