package com.main;


import com.reusables.CsvParser;

import java.time.Duration;
import java.time.Instant;

public class SelectionSort {
    void sort(int arr[]) {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    // Prints the array
    void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }

    // Driver code to test above
    public static void main(String args[])
    {
        SelectionSort ob = new SelectionSort();
        int arr[] = {};
        CsvParser parser = new CsvParser();
        Instant start = Instant.now();
        ob.sort(arr);
        Instant end = Instant.now();
        System.out.println("Sorted array");
        ob.printArray(arr);
        long duration = Duration.between(start,end).toMillis();
        System.out.println("List of size "+arr.length+" took "+duration+"ms to be sorted.");
    }
}
/* This code is contributed by Rajat Mishra*/
