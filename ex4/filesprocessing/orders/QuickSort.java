package filesprocessing.orders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 */
public class QuickSort {
    private static Order order;

    /*
     * QuickSort's partition function.
     * @param arr The array to sort.
     * @param low Lower bound.
     * @param high Upper bound.
     * @return The partitioned index.
     */
    static int partition(ArrayList<File> arr, int low, int high)
    {
        int i = (low - 1);
        for(int j = low; j <= high - 1; j++)
        {
            if(order.isReversed()) {
                if(order.compare(arr.get(j), arr.get(high)) > 0)
                {
                    i++;
                    Collections.swap(arr, i, j);
                }
            }
            else {
                if(order.compare(arr.get(j), arr.get(high)) < 0)
                {
                    i++;
                    Collections.swap(arr, i, j);
                }
            }
        }
        Collections.swap(arr, i + 1, high);
        return (i + 1);
    }

    /*
     * Quicksort the given array.
     * @param arr Array to be sorted.
     * @param low Lower bound.
     * @param high Upper bound.
     */
    static void quickSort(ArrayList<File> arr, int low, int high)
    {
        if (low < high)
        {
            int partitionIndex = partition(arr, low, high);
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    /*
     * The main function received from each order's sort function. Sorts the given array by using the given order's
     * compare function.
     * @param arr The array to sort.
     * @param order the order to base the sorting upon.
     */
    static void sort(ArrayList<File> arr, Order order) {
        QuickSort.order = order;
        quickSort(arr, 0, arr.size() - 1);
        completeSort(arr);
    }

    /*
     * Completes the sort by checking if any of the compares were equal to 0, and if not counts them and reorganizes
     * them by absolute sort.
     * @param arr
     */
    private static void completeSort(ArrayList<File> arr) {
        int count = 0, startIndex = -1;
        for (int i = 0; i < arr.size() - 1; i++) {
            if(order.compare(arr.get(i), arr.get(i + 1)) == 0) {
                if(count == 0) {
                    startIndex = i;
                }
                count++;
            }
            else {
                if(count > 1) {
                    sortSubArray(arr, startIndex, startIndex + count, order);
                }
                count = 0;
            }
        }
    }

    /*
     * A function that sorts the arraylist from index start to index end, based on absolute path order.
     * Note: only on ones that the original order found equal.
     * @param arr the array list of files.
     * @param start index to start sorting from.
     * @param end index to start until.
     */
    private static void sortSubArray(ArrayList<File> arr, int start, int end, Order originalOrder) {
        order = new AbsoluteOrder(false);
        ArrayList<File> temp = new ArrayList<>(arr.subList(start, end + 1));
        quickSort(temp, 0, temp.size() - 1);
        int index = start;
        for (File file : temp) {
            arr.set(start + index, file);
            index++;
        }
        order = originalOrder;
    }
}
