/*
 * TODO:
 *  - Add visualization
 *  - Add mergeSort
 *  - Add comparators to sorting algorithms (instead of Comparable type)
 *
 * DONE:
 *  - Add quickSort
 *      - Finish partition method
 *      - Add sort3 method
 *  - Add step method
 */

import java.util.*;
import comp100.*;

/** Application for visualizing the different sort methods. */
public class SortVisualizer {

    /** Return an array of random integers. */
    public Integer[] randomIntArray() {
        int ARRAY_LENGTH = 7;
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

    /** Print the given array and wait for user input to continue.*/
    private static void step(Comparable[] data) {
        printArray(data);
        // Wait for user input to run next step
        Scanner in = new Scanner(System.in);
        in.nextLine();
    }

    /** Sort a list of integers using QuickSort. */
    public static void quickSort(Comparable[] data) {
        quickSort(data, 0, data.length);
    }

    /** Sort a list of integers using QuickSort. */
    public static void quickSort(Comparable[] data, int low, int high) {
        if (high - low < 2) // only one item to sort.
            return;
        if (high - low < 4) // only two or three items to sort.
            sort3(data, low);
        else {
            int mid = quickSortPartition(data, low, high);
            System.out.println("high-low = " + (high - low));
            System.out.println("mid (pivot): data[" + mid
                              + "] = " + data[mid]);
            step(data);
            quickSort(data, low, mid);
            quickSort(data, mid, high);
        }
    }

    /** Find a pivot value, and sort the array into 2 halves
        based on the pivot value. */
    private static int quickSortPartition(Comparable[] data, 
                                          int low, int high) {
        Comparable pivot = median(data[low], data[high - 1],
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
    public static void sort3(Comparable[] data, int low) {
        int mid = low + 1;
        int high = low + 2;
        if (mid >= data.length)
            return;
        if (high >= data.length)
            high = mid;
        //System.out.println("low: " + low + ", high: " + high);
        Comparable a = data[low];
        Comparable b = data[mid];
        Comparable c = data[high];
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
        
    }

    /** Swap two indexes (i, j) in an array. */
    public static <E> void swap(E[] data, int i, int j){
        E a = data[i];
        data[i] = data[j];
        data[j] = a;
    }

    /** Uses insertionSort to find median value.
        Works well with small amount of values. */
    public static Comparable median(Comparable ... values) {
        insertionSort(values);
        return values[(values.length - 1)/2];
    }

    /** Sort a list of integers using Insertion Sort. */
    public static void insertionSort(Comparable[] data){
        insertionSort(data, 0, data.length-1);
    }

    /** Sort a list of integers using Insertion Sort. */
    public static void insertionSort(Comparable[] data, int low, int high){
        for (int i=low+1; i<high+1; i++) {
            Comparable item = data[i];
            int place = i;
            while ((place > low) && (item.compareTo(data[place - 1]) < low)) {
                data[place] = data[place - 1];
                place--;
            }
            data[place] = item;
        }
    }

    public static <E> void printArray(E[] data) {
        List<E> list = new ArrayList<E>();
        for (int i=0; i<data.length; i++)
            list.add(data[i]);
        System.out.println(list);
    }

    public static void test1() {
        SortVisualizer sv = new SortVisualizer();
        Integer[] data = sv.randomIntArray();
        Integer[] a = data.clone();
        Integer[] b = data.clone();
        System.out.println();
        System.out.println("insertionSort:");
        sv.printArray(a);
        sv.insertionSort(a);
        sv.printArray(a);
        System.out.println();
        System.out.println("quickSort:");
        sv.printArray(b);
        sv.quickSort(b);
        sv.printArray(b);
    }

    public static void test2() {
        Integer[] a = {2,5,4};
        printArray(a);
        insertionSort(a, 0, 2);
        printArray(a);
    }

    public static void main(String args[]) {
        test1();
    }
}
