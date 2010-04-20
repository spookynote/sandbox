/*
 * TODO:
 *  - Add quickSort
 *      - Finish partition method
 *      - Test median method
 *      - Test swap method
 *      - Test do/while syntax
 *      - Test entire quickSort method
 *  - Add mergeSort
 *  - Add visualization
 *  - Add comparators to sorting algorithms (instead of Comparable type)
 *
 * DONE:
 */

import java.util.*;

/** Application for visualizing the different sort methods. */
public class SortVisualizer {

    /** Return an array of random integers. */
    public Integer[] randomIntArray() {
        int ARRAY_LENGTH = 20;
        List<Integer> list = new ArrayList<Integer>();
        Integer[] data = new Integer[ARRAY_LENGTH];
        Random rand = new Random();
        Integer num;
        for (int i=0; i<ARRAY_LENGTH; i++) {
            num = rand.nextInt(ARRAY_LENGTH);
            while (list.contains(num))
                num = rand.nextInt(ARRAY_LENGTH);
            list.add(num);
        }
        for (int i=0; i<ARRAY_LENGTH; i++)
            data[i] = list.get(i);
        return data;
    }

    /** Sort a list of integers using Insertion Sort. */
    public static void insertionSort(Comparable[] data){
        for (int i=1; i<data.length; i++) {
            Comparable item = data[i];
            int place = i;
            while ((place > 0) && (item.compareTo(data[place - 1]) < 0)) {
                data[place] = data[place - 1];
                place--;
            }
            data[place] = item;
        }
    }

    /** Sort a list of integers using QuickSort. */
    public static void quickSort(Comparable[] data) {
        quickSort(data, 0, data.length);
    }

    /** Sort a list of integers using QuickSort. */
    public static void quickSort(Comparable[] data, int low, int high) {
        if (high - low < 2) // only one item to sort.
            return;
        //if (high - low < 4) // only two or three items to sort.
            //sort3(data, low, high, comp);
        else {
            int mid = partition(data, low, high);
            quickSort(data, low, mid);
            quickSort(data, mid, high);
        }
    }

    private static int quickSortPartition(Comparable[] data, 
                                          int low, int high) {
        Comparable pivot = median(data[low], data[high - 1],
                                  data[(low + high) / 2]);
        int left = low - 1;
        int right = high;
        while (left <= right){
            // TODO: is this correct do/while syntax?
            do left++;
            while (left < high && data[left].compareTo(pivot) < 0);
            do right--;
            while (right >= low && data[right].compareTo(pivot) > 0);
            if (left < right) swap(data, left, right);
        }
        return left;
    }

    /** Swap two indexes (i, j) in an array. */
    public static <E> swap(E[] data, int i, int j){
        E a = data[i];
        data[i] = data[j];
        data[j] = a;
    }

    /** Uses insertionSort to find median value.
        Works well with small amount of values. */
    public static Comparable median(Comparable ... values) {
        insertionSort(values);
        return values[(values.length - 1)/2]
    }

    public static <E> void printArray(E[] data) {
        List<E> list = new ArrayList<E>();
        for (int i=0; i<data.length; i++)
            list.add(data[i]);
        System.out.println(list);
    }

    public static void main(String args[]) {
        SortVisualizer sv = new SortVisualizer();
        Integer[] data = sv.randomIntArray();
        System.out.println();
        sv.printArray(data);
        sv.insertionSort(data);
        System.out.println();
        sv.printArray(data);
    }
}
