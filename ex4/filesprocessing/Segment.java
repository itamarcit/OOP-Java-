package filesprocessing;

import filesprocessing.filters.*;
import filesprocessing.orders.*;

import java.util.ArrayList;
import java.io.File;

public class Segment {
    private final Filter filter;
    private final Order order;
    private ArrayList<Integer> warnings = null;
    private static final String WARNING_TEXT = "Warning in line ";

    /**
     * Constructor for a segment.
     * @param filter The filter for the files in this segment.
     * @param order The order type for the files in this segment.
     */
    Segment(Filter filter, Order order, ArrayList<Integer> warnings) {
        this.filter = filter;
        this.order = order;
        if(warnings.size() > 0) {
            this.warnings = new ArrayList<>(warnings);
        }
    }

    /**
     * Filters the given list according to the filter.
     * @param toFilter The array to filter.
     */
    public void filter(ArrayList<File> toFilter) {
        filter.applyFilter(toFilter);
    }

    /**
     * Sorts the given list according to the order.
     * @param toOrder The array to order.
     */
    public void order(ArrayList<File> toOrder) {
        order.sort(toOrder);
    }

    /**
     * Prints the files in the given segment.
     * @param segmentFiles All the files in the directory of the segment.
     */
    public void printSegment(ArrayList<File> segmentFiles) {
        if(warnings != null) {
            for (Integer line : warnings) {
                System.err.println(WARNING_TEXT + line);
            }
        }
        for(File file : segmentFiles) {
            System.out.println(file.getName());
        }
    }
}
