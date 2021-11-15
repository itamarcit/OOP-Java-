package filesprocessing.orders;

import java.io.File;
import java.util.ArrayList;

/**
 * A singleton class which implements Order. Sorts files by comparing the file sizes.
 */
public class SizeOrder implements Order {
    private final boolean reverse;

    /**
     * Constructor for size order.
     * @param reverse whether the order should be reversed.
     */
    public SizeOrder(boolean reverse) {
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
        if (fileOne.length() > fileTwo.length()) {
            return 1;
        } else if (fileOne.length() < fileTwo.length()) {
            return -1;
        }
        return 0;
    }
}
