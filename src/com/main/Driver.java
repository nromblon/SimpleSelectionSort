package com.main;
import java.util.ArrayList;

import com.algorithm.parallel.ParallelSelectionSort;
import com.algorithm.parallel.executor.ParallelSelectionExecutor;
import com.algorithm.parallel.lambda.ParallelSelectionLambda;
import com.algorithm.sequential.RunnableSequentialSelectionSort;
import com.reusables.CsvParser;

public class Driver {
//	private static String filename = "10000random.csv";
	private static String filename = "UnknownRandom.csv";
	
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

        itemList = CsvParser.read(filename);

		System.out.println();
        pLambda.parallelSelectionSort(itemList,2);
    }
	public static void runParallel_executor(){

        ParallelSelectionExecutor parallelSelectionExecutor = new ParallelSelectionExecutor();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        itemList = CsvParser.read(filename);
        parallelSelectionExecutor.start(itemList, 2);
    }
	public static void runParallel(){

        ParallelSelectionSort parallelSelectionSort = new ParallelSelectionSort();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        itemList = CsvParser.read(filename);
        parallelSelectionSort.start(itemList, 2);
    }

	public static void runSequential(){
//        SequentialSelectionSort seq = new SequentialSelectionSort();
        RunnableSequentialSelectionSort runSeq = new RunnableSequentialSelectionSort();
        ArrayList<Integer> input = CsvParser.read(filename);
        runSeq.start(input);

    }
}
