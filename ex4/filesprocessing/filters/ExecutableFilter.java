package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks if the files can be executed or not by the current user.
 */
public class ExecutableFilter implements Filter {
    private final boolean checkIfExecutable;
    private final boolean not;

    /**
     * A constructor for the executable filter.
     * @param checkIfExecutable A boolean value that indicates if to filter executable files, or the non-executable
     *                          ones.
     */
    public ExecutableFilter(boolean checkIfExecutable, boolean not) {
        this.checkIfExecutable = checkIfExecutable;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(checkIfExecutable && not || !checkIfExecutable && !not) {
            fileList.removeIf(file -> file.canExecute());
        }
        else {
            fileList.removeIf(file -> !file.canExecute());
        }
    }
}
