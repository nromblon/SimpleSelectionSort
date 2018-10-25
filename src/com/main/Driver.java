package com.main;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.algorithm.parallel.ParallelSelectionSort;
import com.algorithm.sequential.SequentialSelectionSort;
import com.reusables.CsvParser;

public class Driver {
	public static void main(String[] args) {
	    runSequential();
	}

	public static void runParallel(){

        ParallelSelectionSort parallelSelectionSort = new ParallelSelectionSort();
        ArrayList<Integer> itemList = new ArrayList<Integer>();
        itemList.add(5);
        itemList.add(3);
        itemList.add(1);
        itemList.add(2);
        itemList.add(4);

        parallelSelectionSort.sort(itemList);
    }

	public static void runSequential(){
        String filename = "10000random.csv";
        SequentialSelectionSort ob = new SequentialSelectionSort();
        List<Integer> input = CsvParser.read(filename);
        Instant start = Instant.now();
        ob.sort(input);
        Instant end = Instant.now();
        System.out.println("Sorted array");
        ob.printArray(input);
        long duration = Duration.between(start,end).toMillis();
        System.out.println("List of size "+input.size()+" took "+duration+"ms to be sorted.");
    }
}
