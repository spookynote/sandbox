/*
 * TODO:
 *  - Work on QuickSort/MergeSort classes
 *  - Move insertionSort to its own class
 *  - Improve GUI
 *      - Create different "surfaces" containing the arrays
 *      that can be moved around and clicked on
 *      - Add borders to arrays
 *      - Make clicking start/stop sort
 *
 * DONE:
 *
 * SOMEDAY:
 *  - Make it so multiple sorts can happen at once
 *      - Perhaps requires threading?
 *  - Make sort methods generic
 *  - Make sort methods use comparators
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
    private int NO_OF_ARRAYS = 2; // number of arrays we're rendering
    private int UNIT_SIZE = 8; // screen size of array elements
    private int ARRAY_LENGTH = 20;
    private int ARRAY_PADDING = 20; // pixel-padding between arrays
    private int SCREEN_WIDTH = UNIT_SIZE * ARRAY_LENGTH * NO_OF_ARRAYS + 
                               NO_OF_ARRAYS * ARRAY_PADDING;
    private int SCREEN_HEIGHT = UNIT_SIZE * ARRAY_LENGTH + 50;
    public int STEP_DELAY = 500; // delay (ms) between each sort-step

    /** Constructor. Create the SortVisualizer GUI. */
    public SortVisualizer() {
        frame = new JFrame("Sort Visualizer");
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        //The graphics area
        canvas = new DrawingCanvas();
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        frame.setVisible(true);

        Integer[] data1 = randomIntArray();
        Integer[] data2 = data1.clone();
        QuickSort qs = new QuickSort(data1, this);
        MergeSort ms = new MergeSort(data2, this);
    }

    /** Render an array of integers at given position.
        Position is given as an integer 1, 2, 3, etc.
        RenderArray will position arrays so 1 doesn't 
        overlap with 2, 2 doesn't overlap 3, etc.*/
    public <E> void renderArray(E[] data) {
        renderArray(data, 1);
    }

    /** Render an array of integers. */
    public <E> void renderArray(E[] data, int pos) {
        canvas.clear();
        pos = pos - 1;
        for (int i=0; i<data.length; i++) {
            if (data instanceof Integer[]){
                Integer element = (Integer) data[i];
                canvas.setColor(Color.blue);
                int x = i * UNIT_SIZE + pos * ARRAY_LENGTH * UNIT_SIZE + 
                        (pos + 1) * ARRAY_PADDING;
                int y = element * UNIT_SIZE;
                int wd = UNIT_SIZE;
                int ht = UNIT_SIZE;
                canvas.fillRect(x, y, wd, ht, false);
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

    public static void main(String args[]) {
        new SortVisualizer();
        //test1();
    }
}
