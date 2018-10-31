package com.algorithm.sequential;

import java.util.ArrayList;

import com.reusables.General;

/* This code is adapted from Rajat Mishra's*/

public class SequentialSelectionSort {
    public void sort(ArrayList<Integer> list) {
    	System.out.println("SEQ: Process START");
		General.PRINT_TIME();
		
        int n = list.size();

        // One by one move boundary of unsorted sublistay
        for (int i = 0; i < n-1; i++) {
            // Find the minimum element in unsorted listay
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (list.get(j) < list.get(min_idx))
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = list.get(min_idx);
            list.set(min_idx,list.get(i));
            list.set(i,temp);
        }
        
        System.out.println("SEQ: Process DONE");
		General.PRINT_TIME();
    }

    // Prints the array
    public void printArray(ArrayList<Integer> arr) {
        int n = arr.size();
        for (int i=0; i<n; ++i)
            System.out.print(arr.get(i)+" ");
        System.out.println();
    }
}
