package filesprocessing.orders;

import java.io.File;
import java.util.ArrayList;

/**
 * A singleton class which implements Order. Sorts files by comparing their absolute names. Going from 'a' to 'z'.
 */
public class AbsoluteOrder implements Order {
    private final boolean reverse;

    /**
     * Constructor for absolute order.
     * @param reverse whether the order should be reversed.
     */
    public AbsoluteOrder(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public boolean isReversed() {
        return reverse;
    }

    @Override
    public void sort(ArrayList<File> fileList) {
        QuickSort.sort(fileList, this);
    }

    @Override
    public int compare(File fileOne, File fileTwo) {
        if (fileOne.getAbsolutePath().compareTo(fileTwo.getAbsolutePath()) > 0) {
            return 1;
        } else if (fileOne.getAbsolutePath().compareTo(fileTwo.getAbsolutePath()) < 0) {
            return -1;
        }
        return 0;
    }
}
