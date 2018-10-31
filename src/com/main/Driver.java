package com.main;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import com.algorithm.parallel.ParallelSelectionSort;
import com.algorithm.sequential.RunnableSequentialSelectionSort;
import com.algorithm.sequential.SequentialSelectionSort;
import com.reusables.CsvParser;

public class Driver {
	public static void main(String[] args) {
	    runSequential();
	    runParallel();
	}

	public static void runParallel(){

        ParallelSelectionSort parallelSelectionSort = new ParallelSelectionSort();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        String filename = "10000random.csv";
        // String filename = "UnknownRandom.csv";
        itemList = CsvParser.read(filename);


        Instant start = Instant.now();
        parallelSelectionSort.start(itemList, 2);
        //TODO: Add wait until list is sorted.
        Instant end = Instant.now();
        long duration = Duration.between(start,end).toMillis();
//        System.out.println("PARALLEL : List of size "+itemList.size()+" took "+duration+"ms to be sorted.");
    }

	public static void runSequential(){
        String filename = "10000random.csv";
        SequentialSelectionSort seq = new SequentialSelectionSort();
        RunnableSequentialSelectionSort runSeq = new RunnableSequentialSelectionSort();
        ArrayList<Integer> input = CsvParser.read(filename);
        Instant start = Instant.now();
//        seq.sort(input);
        runSeq.start(input);
        Instant end = Instant.now();

//        System.out.println("Sorted array");
//        seq.printArray(input);
        long duration = Duration.between(start,end).toMillis();
//        System.out.println("SEQUENTIAL : List of size "+input.size()+" took "+duration+"ms to be sorted.");
    }
}
