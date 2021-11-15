package filesprocessing.orders;

import java.io.File;
import java.util.ArrayList;

/**
 * A singleton class which implements Order. Sorts files by comparing their file types. Going from 'a' to 'z'.
 */
public class TypeOrder implements Order {
    private static final String DOT = ".";
    private final boolean reverse;

    /**
     * Constructor for type order.
     * @param reverse whether the order should be reversed.
     */
    public TypeOrder(boolean reverse) {
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
        String fileOneName = fileOne.getName();
        String fileTwoName = fileTwo.getName();
        String fileOneType = fileOneName.substring(fileOneName.lastIndexOf(DOT));
        String fileTwoType = fileTwoName.substring(fileTwoName.lastIndexOf(DOT));
        if (fileOneType.compareTo(fileTwoType) > 0) {
            return 1;
        } else if (fileOneType.compareTo(fileTwoType) < 0) {
            return -1;
        }
        return 0;
    }
}
