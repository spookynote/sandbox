/*
 * TODO:
 *  - Fix glitch in visualization
 *      - Figure out what is causing glitch
 *      - (Glitch is a drop-out and also an occassional delay between
 *      sort steps)
 */

public class QuickSort {
    private Integer[] data1;
    private SortVisualizer sortVisualizer;

    public QuickSort(Integer[] data, SortVisualizer sortVisualizer) {
        this.sortVisualizer = sortVisualizer;
        data1 = data;
        quickSort(data1);
    }

    /** Sort a list of integers using QuickSort. */
    public void quickSort(Integer[] data) {
        quickSort(data, 0, data.length);
    }

    /** Sort a list of integers using QuickSort. */
    public void quickSort(Integer[] data, int low, int high) {
        if (high - low < 2) // only one item to sort.
            return;
        if (high - low < 4) // only two or three items to sort.
            sort3(data, low);
        else {
            int mid = partition(data, low, high);
            //System.out.println("high-low = " + (high - low));
            //System.out.println("mid (pivot): data[" + mid
                              //+ "] = " + data[mid]);
            quickSort(data, low, mid);
            quickSort(data, mid, high);
        }
    }

    /** Find a pivot value, and sort the array into 2 halves
        based on the pivot value. */
    private int partition(Integer[] data, int low, int high) {
        Integer pivot = median(data[low], data[high - 1],
                                  data[(low + high) / 2]);
        int left = low - 1;
        int right = high;
        while (left <= right){
            do { 
                left++;
            } while (left < high && data[left].compareTo(pivot) < 0);
            do { 
                right--;
            } while (right >= low && data[right].compareTo(pivot) > 0);
            if (left < right) swap(data, left, right);
        }
        return left;
    }

    /** Sort 3 (or less) values in an array. */
    public void sort3(Integer[] data, int low) {
        int mid = low + 1;
        int high = low + 2;
        if (mid >= data.length)
            return;
        if (high >= data.length)
            high = mid;
        //System.out.println("low: " + low + ", high: " + high);
        Integer a = data[low];
        Integer b = data[mid];
        Integer c = data[high];
        if (c.compareTo(b) < 0) {
            if (c.compareTo(a) < 0){
                data[low] = c;
                if (b.compareTo(a) < 0) {
                    data[mid] = b;
                    data[high] = a;
                }
                else {
                    data[mid] = a;
                    data[high] = b;
                }
            }
            else {
                data[low] = a;
                data[mid] = c;
                data[high] = b;
            }
        }
        else if (b.compareTo(a) < 0) {
            data[low] = b;
            if (c.compareTo(a) < 0) {
                data[mid] = c;
                data[high] = a;
            }
            else {
                data[mid] = a;
                data[high] = c;
            }
        }
        else {
            data[low] = a;
            data[mid] = b;
            data[high] = c;
        }
        step(); // sleep for a bit, then show new array
    }

    /** Swap two indexes (i, j) in an array. */
    public void swap(Integer[] data, int i, int j){
        Integer a = data[i];
        data[i] = data[j];
        data[j] = a;
        step(); // sleep for a bit, then show new array
    }

    /** Uses insertionSort to find median value.
        Works well with small amount of values. */
    public Integer median(Integer ... values) {
        sort3(values, 0);
        return values[(values.length - 1)/2];
    }

    private void step() {
        sortVisualizer.renderArray(data1, 1);
        try {
            Thread.sleep(sortVisualizer.STEP_DELAY);
        } catch(InterruptedException e) {}
    }
}
