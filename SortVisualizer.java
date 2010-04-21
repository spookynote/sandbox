/*
 * TODO:
 *  - Add mergeSort
 *  - Add comparators to sorting algorithms (instead of Comparable type)
 *
 * DONE:
 *  - Add visualization
 *
 * MAYBE:
 *  - Simplfy algorithm
 *      - Modify algorithm to work without sort3 function
 */

import java.util.*;
import comp100.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;

/** Application for visualizing the different sort methods. */
public class SortVisualizer {

    // Fields
    private JFrame frame;
    private DrawingCanvas canvas;
    private JTextArea messageArea; 
    private Integer[] data1;
    private int unitSize = 8; // screen size of array elements
    private int ARRAY_LENGTH = 20;

    /** Constructor. Create the SortVisualizer GUI. */
    public SortVisualizer() {
        frame = new JFrame("Sort Visualizer");
        frame.setSize(unitSize * ARRAY_LENGTH + 10, 
                      unitSize * ARRAY_LENGTH + 50);

        //The graphics area
        canvas = new DrawingCanvas();
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        frame.setVisible(true);

        data1 = randomIntArray();
        renderArray(data1);
        quickSort(data1);
        renderArray(data1); // bug makes quicksort not render last step
    }

    /** Return an array of random integers. */
    public <E> void renderArray(E[] data) {
        canvas.clear();
        for (int i=0; i<data.length; i++) {
            if (data instanceof Integer[]){
                Integer element = (Integer) data[i];
                canvas.setColor(Color.blue);
                canvas.fillRect(i*unitSize, element*unitSize, unitSize, unitSize, false);
            }
        }
        canvas.display();
    }

    /** Return an array of random integers. */
    public Integer[] randomIntArray() {
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

    /** Render the given array and sleep for a short period of time.*/
    private void step(Comparable[] data) {
        renderArray(data);
        try {
            Thread.sleep(900);
        } catch(InterruptedException e) {}
        //// Wait for user input to run next step
        //Scanner in = new Scanner(System.in);
        //in.nextLine();
    }

    /** Sort a list of integers using QuickSort. */
    public void quickSort(Comparable[] data) {
        quickSort(data, 0, data.length);
    }

    /** Sort a list of integers using QuickSort. */
    public void quickSort(Comparable[] data, int low, int high) {
        if (high - low < 2) // only one item to sort.
            return;
        if (high - low < 4) // only two or three items to sort.
            sort3(data, low);
        else {
            int mid = quickSortPartition(data, low, high);
            //System.out.println("high-low = " + (high - low));
            //System.out.println("mid (pivot): data[" + mid
                              //+ "] = " + data[mid]);
            quickSort(data, low, mid);
            quickSort(data, mid, high);
        }
    }

    /** Find a pivot value, and sort the array into 2 halves
        based on the pivot value. */
    private int quickSortPartition(Comparable[] data, 
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
    public void sort3(Comparable[] data, int low) {
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
        step(data); // sleep for a bit, then show new array
    }

    /** Swap two indexes (i, j) in an array. */
    public void swap(Comparable[] data, int i, int j){
        Comparable a = data[i];
        data[i] = data[j];
        data[j] = a;
        step(data); // sleep for a bit, then show new array
    }

    /** Uses insertionSort to find median value.
        Works well with small amount of values. */
    public Comparable median(Comparable ... values) {
        insertionSort(values);
        return values[(values.length - 1)/2];
    }

    /** Sort a list of integers using Insertion Sort. */
    public void insertionSort(Comparable[] data){
        insertionSort(data, 0, data.length-1);
    }

    /** Sort a list of integers using Insertion Sort. */
    public void insertionSort(Comparable[] data, int low, int high){
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
        // NOTE: This function no longer works because the 
        // SortVisualizer constructor has been modified.
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
        SortVisualizer sv = new SortVisualizer();
        Integer[] a = {2,5,4};
        printArray(a);
        sv.insertionSort(a, 0, 2);
        printArray(a);
    }

    public static void main(String args[]) {
        new SortVisualizer();
        //test1();
    }
}
