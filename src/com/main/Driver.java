package com.main;
import java.util.ArrayList;

import com.algorithm.parallel.ParallelSelectionSort;
import com.algorithm.parallel.executor.ParallelSelectionExecutor;
import com.algorithm.parallel.lambda.ParallelSelectionLambda;
import com.algorithm.sequential.RunnableSequentialSelectionSort;
import com.reusables.CsvParser;

public class Driver {
	public static void main(String[] args) {
//        runParallel_lambda();
//        System.out.println("\n\nStarting sequential...");
//        runSequential();
//        System.out.println("\n\nStarting parallel standard...");
//        runParallel();
        runParallel_executor();
	}

	public static void runParallel_lambda(){
        ParallelSelectionLambda pLambda = new ParallelSelectionLambda();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        String filename = "10000random.csv";
        // String filename = "UnknownRandom.csv";
        itemList = CsvParser.read(filename);

		System.out.println();
        pLambda.parallelSelectionSort(itemList,2);
    }
	public static void runParallel_executor(){

        ParallelSelectionExecutor parallelSelectionExecutor = new ParallelSelectionExecutor();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        String filename = "10000random.csv";
        // String filename = "UnknownRandom.csv";
        itemList = CsvParser.read(filename);

        parallelSelectionExecutor.start(itemList, 2);
    }
	public static void runParallel(){

        ParallelSelectionSort parallelSelectionSort = new ParallelSelectionSort();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        String filename = "10000random.csv";
        // String filename = "UnknownRandom.csv";
        itemList = CsvParser.read(filename);


//        Instant start = Instant.now();
        parallelSelectionSort.start(itemList, 2);
        //TODO: Add wait until list is sorted.
//        Instant end = Instant.now();
//        long duration = Duration.between(start,end).toNanos();
//        System.out.println("PARALLEL : List of size "+itemList.size()+" took "+duration+"ns to be sorted.");
    }

	public static void runSequential(){
        String filename = "10000random.csv";
//        SequentialSelectionSort seq = new SequentialSelectionSort();
        RunnableSequentialSelectionSort runSeq = new RunnableSequentialSelectionSort();
        ArrayList<Integer> input = CsvParser.read(filename);
//        Instant start = Instant.now();
//        seq.sort(input);
        
        runSeq.start(input);

        
//        Instant end = Instant.now();

//        System.out.println("Sorted array");
//        seq.printArray(input);
//        long duration = Duration.between(start,end).toNanos();
//        System.out.println("SEQUENTIAL : List of size "+input.size()+" took "+duration+"ns to be sorted.");
    }
}
