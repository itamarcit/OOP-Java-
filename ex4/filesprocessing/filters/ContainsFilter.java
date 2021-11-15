package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks if a given string is included in the files' name.
 */
public class ContainsFilter implements Filter {
    private final String containsValue;
    private final boolean not;

    /**
     * A constructor for the contains filter.
     * @param valueToCheckFor the string value to check if the filename contains.
     */
    public ContainsFilter(String valueToCheckFor, boolean not) {
        this.containsValue = valueToCheckFor;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(not) {
            fileList.removeIf(file -> file.getName().contains(containsValue));
        }
        else {
            fileList.removeIf(file -> !file.getName().contains(containsValue));
        }
    }

}
