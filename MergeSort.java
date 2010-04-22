/*
 * TODO:
 *  - Fix totally screwy visualization (perhaps it's to do with 
 *  the double-arrays)
 */

public class MergeSort {
    
    private Integer[] data1;
    private SortVisualizer sortVisualizer;
    
    public MergeSort(Integer[] data, SortVisualizer sortVisualizer) {
        this.sortVisualizer = sortVisualizer;
        data1 = data;
        mergeSort(data1);
    }

    /** Sort a list of integers using MergeSort. */
    public void mergeSort(Integer[] data) {
        Integer[] other = new Integer[data.length];
        for (int i=0; i<data.length; i++)
            other[i] = data[i];
        mergeSort(data, other, 0, data.length);
    }

    /** Sort a list of integers using MergeSort. */
    private void mergeSort(Integer[] data, Integer[] temp, 
                          int low, int high) {
        if (high > low + 1) {
            int mid = (low + high) / 2;
            mergeSort(temp, data, low, mid);
            mergeSort(temp, data, mid, high);
            merge(temp, data, low, mid, high);
        }
    }

    /** Merge from[low..mid-1] with from[mid..high-1] into to[low..high-1] */
    private void merge(Integer[] from, Integer[] to,
                       int low, int mid, int high) {
        int index = low;
        int indexL = low;
        int indexR = mid;
        while(indexL<mid && indexR < high) {
            step(); // render array
            if (from[indexL].compareTo(from[indexR]) <= 0)
                to[index++] = from[indexL++];
            else
                to[index++] = from[indexR++];
        }

        // copy over the remainder
        while (indexL < mid) {
            step(); // render array
            to[index++] = from[indexL++];
        }
        while (indexR < high) {
            step(); // render array
            to[index++] = from[indexR++];
        }
        step(); // render array
    }

    private void step() {
        sortVisualizer.renderArray(data1, 2);
        try {
            Thread.sleep(sortVisualizer.STEP_DELAY);
        } catch(InterruptedException e) {}
    }
}
