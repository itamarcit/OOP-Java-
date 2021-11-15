package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

public class BetweenFilter implements Filter{
    private final double min;
    private final double max;
    private final boolean not;

    public BetweenFilter(double min, double max, boolean not) {
        this.min = min;
        this.max = max;
        this.not = not;
    }

    @Override
    public void applyFilter(ArrayList<File> fileList) {
        if(not) {
            fileList.removeIf(file -> file.length() >= min && file.length() <= max);
        }
        else {
            fileList.removeIf(file -> file.length() < min || file.length() > max);
        }
    }
}
