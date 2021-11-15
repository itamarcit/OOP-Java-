package filesprocessing.orders;

import java.io.File;
import java.util.ArrayList;

/**
 * An interface all orders implement.
 */
public interface Order {

    boolean isReversed();

    /**
     * A function that sorts the given array of files.
     */
    void sort(ArrayList<File> fileList);

    /**
     * A compare function between two files, defined by each order.
     * @param fileOne The first file.
     * @param fileTwo The second file.
     * @return 0 if files are equal, -1 if fileTwo > fileOne, 1 otherwise.
     */
    int compare(File fileOne, File fileTwo);
}
