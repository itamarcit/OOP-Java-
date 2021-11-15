package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks if the files are hidden or not.
 */
public class HiddenFilter implements Filter {
    private final boolean checkIfHidden;
    private final boolean not;

    /**
     * A constructor for the hidden filter.
     * @param checkIfHidden A boolean value that indicates if to filter hidden files, or the non-hidden ones.
     */
    public HiddenFilter(boolean checkIfHidden, boolean not) {
        this.checkIfHidden = checkIfHidden;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(checkIfHidden && !not || !checkIfHidden && not) {
            fileList.removeIf(file -> !file.isHidden());
        }
        else {
            fileList.removeIf(file -> file.isHidden());
        }
    }
}
