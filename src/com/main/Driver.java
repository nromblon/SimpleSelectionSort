package com.main;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

import com.algorithm.parallel.ParallelSelectionSort;
import com.algorithm.parallel.executor.ParallelSelectionExecutor;
import com.algorithm.parallel.forkjoin.ParallelSelectionForkJoin;
import com.algorithm.parallel.lambda.ParallelSelectionLambda;
import com.algorithm.sequential.RunnableSequentialSelectionSort;
import com.reusables.CsvParser;
import com.reusables.CsvWriter;

public class Driver {
//	private static String filename = "10000random.csv";
	private static String filename = "UnknownRandom.csv";
	private static final String generated_name_prefix = "unsorted_generated_";

	public static void main(String[] args) {

//		runParallel_forkjoin();
//        runParallel_executor();
//        runParallel_lambda();
//        System.out.println("\n\nStarting sequential...");
        runSequential();
//        System.out.println("\n\nStarting parallel standard...");
//        runParallel();
	}
	public static void runParallel_forkjoin(){
		ParallelSelectionForkJoin parallelSelectionForkJoin = new ParallelSelectionForkJoin();
        ArrayList<Integer> itemList = new ArrayList<Integer>();

        itemList = CsvParser.read(filename);
        parallelSelectionForkJoin.start(itemList, 2);
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

    public static ArrayList<Integer> generateRandom(int amount){
        Random rand = new Random();
        rand.setSeed(Instant.now().getEpochSecond());
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i<amount; i++){
            numbers.add(rand.nextInt());
        }
        CsvWriter.write(numbers, generated_name_prefix+amount+".csv");
        return numbers;
    }
}
