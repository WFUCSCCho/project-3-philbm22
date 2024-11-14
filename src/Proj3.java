/********************************************************************************
 ∗ @file: Proj3.java
 ∗ @description: This program implements quick, bubble, odd-even transposition and merge sort algortihms for a
 * generic object
 ∗ @author: Benton Phillips
 ∗ @date: November 15 , 2024
 *********∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {

            int mid = left + (right - left) / 2;


            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);

            merge(a, left, mid, right);
        }
    }
    // Merges 2 subarrays with mid being the last element of the first sub array
    public static <T extends Comparable> void merge(ArrayList<T> a,int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = 0;
        ArrayList<T> temp = new ArrayList();

        while (i <= mid && j <= right) {
            if (a.get(i).compareTo(a.get(j)) < 0) {
                temp.add(k,a.get(i));
                k++;
                i++;
            } else {
                temp.add(k, a.get(j));
                k++;
                j++;
            }
        }

        while (i <= mid) {
            temp.add(k, a.get(i));
            k++;
            i++;
        }
        while (j <= right) {
            temp.add(k, a.get(j));
            k++;
            j++;
        }

        int loc = 0;
        for (k = left; k <= right; k++) {
            a.set(k, temp.get(loc));
            loc++;
        }
    }


    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right){
            int pivot = partition(a, left, right);
            quickSort(a, left, pivot - 1);
            quickSort(a, pivot + 1, right);

        }
    }
    //Chooses a pivot value to perform quick sort
    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        int pivotIndex = left + ((right-left)/2);
        T pivot = a.get(pivotIndex);
        int i = left - 1;

        if(pivotIndex != right) {
            swap(a, pivotIndex, right);
        }

        for (int j = left; j < right; j++) {
            if(a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i+1, right);
        return i+1;
    }

    //Swaps two elements
    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        for (int i = a.size() / 2 - 1; i >= 0; i--){
            heapify(a, a.size(), i);
        }
        for (int i = a.size() - 1; i > 0; i--) {
            swap(a, 0, i);
            heapify(a, i, 0);
        }
    }

    //Upholds the maxHeap property for a subtree
    public static <T extends Comparable> void heapify (ArrayList<T> a, int size, int workingVal) {
        int largest = workingVal;
        int leftChild = 2 * workingVal + 1;
        int rightChild = 2 * workingVal + 2;

        if(leftChild < size && a.get(leftChild).compareTo(a.get(largest)) > 0) {
            largest = leftChild;
        }
        if(rightChild < size && a.get(rightChild).compareTo(a.get(largest)) > 0) {
            largest = rightChild;
        }
        if(largest != workingVal) {
            swap(a, largest, workingVal);
            heapify(a, size, largest);
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        boolean swapped;
        int comparisons  = 0;
        for(int i = 0; i < size; i++) {
            swapped = false;
            for (int j = i+1; j < size; j++) {
                comparisons ++;
                if (a.get(i).compareTo(a.get(j)) > 0) {
                    swap(a, i, j);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return comparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;

            for (int i = 1; i < a.size() - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }

            for (int i = 0; i < a.size() - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                   swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            comparisons += 2;
        }

        return comparisons;
    }

    public static void main(String [] args)  throws IOException {

        if (args.length != 3) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[2]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        //Create an ArrayList to store all mountain objects
        ArrayList<Mountain> mountains = new ArrayList<>();


        //Populate the ArrayList with mountain objects in their correct indices
        for(int i = 0; i < numLines; i++) {
            String line = inputFileNameScanner.nextLine().strip();

            if (!line.isEmpty()) {
                String[] totalCommand = line.split(",");
                if(totalCommand.length >= 5) {
                    Mountain mountain = new Mountain(
                            totalCommand[0].trim(),
                            Integer.parseInt(totalCommand[1].trim()),
                            Integer.parseInt(totalCommand[2].trim()),
                            totalCommand[3].trim(),
                            totalCommand[4].trim()
                    );

                    mountains.add(mountain);
                }
            }
        }
        inputFileNameScanner.close();
        FileWriter fileWriter = new FileWriter("analysis.txt",true);
        FileWriter fileWriter2 = new FileWriter("sorted.txt");

        ArrayList<Mountain> mainList = new ArrayList<>(mountains);
        ArrayList<Mountain> sortedList = new ArrayList<>(mountains);
        ArrayList<Mountain> shuffledList = new ArrayList<>(mountains);
        ArrayList<Mountain> reverseList = new ArrayList<>(mountains);

        Collections.sort(sortedList);
        Collections.shuffle(shuffledList);
        Collections.sort(reverseList, Collections.reverseOrder());

        switch (args[1]){
            case "quick":
                long startTime1 = System.nanoTime();
                quickSort(sortedList, 0, sortedList.size() - 1);
                long endTime1 = System.nanoTime();
                double runtime1 = (endTime1 - startTime1) / 1e9;
                System.out.println(numLines+ " Number lines " + "Sorted Quicksort runtime: " + runtime1);
                fileWriter.write(args[1] + "," + "sorted" + "," + numLines + "," +  runtime1 + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime2 = System.nanoTime();
                quickSort(shuffledList, 0, shuffledList.size() - 1);
                long endTime2 = System.nanoTime();
                double runtime2 = (endTime2 - startTime2) / 1e9;
                System.out.println(numLines+ " Number lines " + "Shuffled Quicksort runtime: " + runtime2);
                fileWriter.write(args[1] + "," + "shuffled" + "," + numLines + "," +  runtime2 + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime3 = System.nanoTime();
                quickSort(reverseList, 0, reverseList.size() - 1);
                long endTime3 = System.nanoTime();
                double runtime3 = (endTime3 - startTime3) / 1e9;
                System.out.println(numLines+ " Number lines " + "Reversed Quicksort runtime: " + runtime3);
                fileWriter.write(args[1] + "," + "reversed" + "," + numLines + "," +  runtime3 + "\n");
                fileWriter2.write(sortedList.toString());

                break;

            case "merge":
                long startTime4 = System.nanoTime();
                mergeSort(sortedList, 0, sortedList.size() - 1);
                long endTime4 = System.nanoTime();
                double runtime4 = (endTime4 - startTime4) / 1e9;
                System.out.println(numLines+ " Number lines " + "Sorted Mergesort runtime: " + runtime4);
                fileWriter.write(args[1] + "," + "sorted" + "," + numLines + "," +  runtime4 + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime5 = System.nanoTime();
                mergeSort(shuffledList, 0, shuffledList.size() - 1);
                long endTime5 = System.nanoTime();
                double runtime5 = (endTime5 - startTime5) / 1e9;
                System.out.println(numLines+ " Number lines " + "Shuffled Mergesort runtime: " + runtime5);
                fileWriter.write(args[1] + "," + "shuffled" + "," + numLines + "," +  runtime5 + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime6 = System.nanoTime();
                mergeSort(reverseList, 0, reverseList.size() - 1);
                long endTime6 = System.nanoTime();
                double runtime6 = (endTime6 - startTime6) / 1e9;
                System.out.println(numLines+ " Number lines " + "Reversed Mergesort runtime: " + runtime6);
                fileWriter.write(args[1] + "," + "reversed" + "," + numLines + "," +  runtime6 + "\n");
                fileWriter2.write(sortedList.toString());
                break;

            case "heap":
                long startTime7 = System.nanoTime();
                heapSort(sortedList, 0, sortedList.size() - 1);
                long endTime7 = System.nanoTime();
                double runtime7 = (endTime7 - startTime7) / 1e9;
                System.out.println(numLines+ " Number lines " + "Sorted Heapsort runtime: " + runtime7);
                fileWriter.write(args[1] + "," + "sorted" + "," + numLines + "," +  runtime7 + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime8 = System.nanoTime();
                heapSort(shuffledList, 0, shuffledList.size() - 1);
                long endTime8 = System.nanoTime();
                double runtime8 = (endTime8 - startTime8) / 1e9;
                System.out.println(numLines+ " Number lines " + "Shuffled Heapsort runtime: " + runtime8);
                fileWriter.write(args[1] + "," + "shuffled" + "," + numLines + "," +  runtime8 + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime9 = System.nanoTime();
                heapSort(reverseList, 0, reverseList.size() - 1);
                long endTime9 = System.nanoTime();
                double runtime9 = (endTime9 - startTime9) / 1e9;
                System.out.println(numLines+ " Number lines " + "Reversed Heapsort runtime: " + runtime9);
                fileWriter.write(args[1] + "," + "reversed" + "," + numLines + "," +  runtime9 + "\n");
                fileWriter2.write(sortedList.toString());
                break;

            case "transposition":
                int comparisons1 = transpositionSort(sortedList, sortedList.size() - 1);
                System.out.println(numLines+ " Number lines " + "Sorted Transposition comparisons: " + comparisons1);
                fileWriter.write(args[1] + "," + "sorted" + "," + numLines + "," +  comparisons1 + "\n");
                fileWriter2.write(sortedList.toString());


                int comparisons2 = transpositionSort(shuffledList,shuffledList.size() - 1);
                System.out.println(numLines+ " Number lines " + "Shuffled Transposition comparisons: " + comparisons2);
                fileWriter.write(args[1] + "," + "shuffled" + "," + numLines + "," +  comparisons2 + "\n");
                fileWriter2.write(sortedList.toString());

                int comparisons3 = transpositionSort(reverseList, reverseList.size() - 1);
                System.out.println(numLines+ " Number lines " + "Reversed Transposition comparisons: " + comparisons3);
                fileWriter.write(args[1] + "," + "reversed" + "," + numLines + "," +  comparisons3 + "\n");
                fileWriter2.write(sortedList.toString());
                break;

            case "bubble":
                long startTime13 = System.nanoTime();
                int comparisons = bubbleSort(sortedList,sortedList.size() - 1);
                long endTime13 = System.nanoTime();
                double runtime13 = (endTime13 - startTime13) / 1e9;
                System.out.println(numLines+ " Number lines " + "Sorted Bubble runtime: " + runtime13 + "Sorted Bubble Comparisons: " + comparisons);
                fileWriter.write(args[1] + "," + "sorted" + "," +numLines + "," +  runtime13 + ", " + comparisons + "\n");
                fileWriter2.write(sortedList.toString());

                long startTime14 = System.nanoTime();
                int comparisons4 = bubbleSort(shuffledList,shuffledList.size() - 1);
                long endTime14 = System.nanoTime();
                double runtime14 = (endTime14 - startTime14) / 1e9;
                System.out.println(numLines+ " Number lines " + "Shuffled Bubble runtime: " + runtime14 + "Shuffled Bubble Comparisons: " + comparisons4);
                fileWriter.write(args[1] + "," + "shuffled" + "," + numLines + "," +  runtime14 + ", " + comparisons4 +  "\n");
                fileWriter2.write(sortedList.toString());

                long startTime15 = System.nanoTime();
                int comparisons5 = bubbleSort(reverseList,reverseList.size() - 1);
                long endTime15 = System.nanoTime();
                double runtime15 = (endTime15 - startTime15) / 1e9;
                System.out.println(numLines+ " Number lines " + "Reversed Bubble runtime: " + runtime15 + "Reversed Bubble Comparisons: " + comparisons5);
                fileWriter.write(args[1] + "," + "reversed" + "," + numLines + "," +  runtime15 + "," + comparisons5 +  "\n");
                fileWriter2.write(sortedList.toString());
                break;

            default:
                System.out.println("Invalid command. Valid commands are quick, merge, heap, transposition, bubble");
        }
        fileWriter.close();
        fileWriter2.close();
    }
}

