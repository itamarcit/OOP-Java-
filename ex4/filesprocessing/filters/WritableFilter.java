package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * A filter that checks if the files can be writable or not by the current user.
 */
public class WritableFilter implements Filter {
    private final boolean checkIfWritable;
    private final boolean not;

    /**
     * A constructor for the writable filter.
     * @param checkIfWritable A boolean value that indicates if to filter writable files, or the non-writable ones.
     */
    public WritableFilter(boolean checkIfWritable, boolean not) {
        this.checkIfWritable = checkIfWritable;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(checkIfWritable && !not) {
            fileList.removeIf(file -> !file.canWrite());
        }
        else {
            fileList.removeIf(file -> file.canWrite());
        }
    }
}
