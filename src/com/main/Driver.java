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
//	private static String filename = "UnknownRandom.csv";
	private static final String generated_name_prefix = "unsorted_generated_";
	private static final int _10K = 10000;
	private static final int _100K = 100000;
	private static final int _1M = 1000000;

	private static final String filename = generated_name_prefix+_10K+".csv";
	
	public static void main(String[] args) {
//        runParallel_executor();
        runSequential();
	}
	public static void runParallel_forkjoin(ArrayList<Integer> list){
		ParallelSelectionForkJoin parallelSelectionForkJoin = new ParallelSelectionForkJoin();
        ArrayList<Integer> itemList = list;
        parallelSelectionForkJoin.start(itemList, 2);
	}
	public static void runParallel_lambda(ArrayList<Integer> list){
        ParallelSelectionLambda pLambda = new ParallelSelectionLambda();
        ArrayList<Integer> itemList = list;

		System.out.println();
        pLambda.parallelSelectionSort(itemList,2);
    }
	
	public static void runParallel_executor(ArrayList<Integer> list){

        ParallelSelectionExecutor parallelSelectionExecutor = new ParallelSelectionExecutor();
        ArrayList<Integer> itemList = list;
        parallelSelectionExecutor.start(itemList, 2);
    }
	public static void runParallel_executor(){

        ParallelSelectionExecutor parallelSelectionExecutor = new ParallelSelectionExecutor();
        ArrayList<Integer> itemList = CsvParser.read(filename);
        parallelSelectionExecutor.start(itemList, 2);
    }

	public static void runParallel(ArrayList<Integer> list){

        ParallelSelectionSort parallelSelectionSort = new ParallelSelectionSort();
        ArrayList<Integer> itemList = list;
        parallelSelectionSort.start(itemList, 2);
    }
	public static void runSequential(){
//      SequentialSelectionSort seq = new SequentialSelectionSort();
      RunnableSequentialSelectionSort runSeq = new RunnableSequentialSelectionSort();
      ArrayList<Integer> input = CsvParser.read(filename);
      runSeq.start(input);

  }
	public static void runSequential(ArrayList<Integer> list){
//        SequentialSelectionSort seq = new SequentialSelectionSort();
        RunnableSequentialSelectionSort runSeq = new RunnableSequentialSelectionSort();
        ArrayList<Integer> input = list;
        runSeq.start(input);

    }

    public static ArrayList<Integer> generateRandom(int amount){
        System.out.println("Generating input...");
        Random rand = new Random();
        rand.setSeed(Instant.now().getEpochSecond());
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i<amount; i++){
            numbers.add(Math.abs(rand.nextInt(100000000)));
        }
        System.out.println("Input generation complete.");
        System.out.println("Writing into a file...");
        CsvWriter.write(numbers, generated_name_prefix+amount);
        System.out.println("Write complete.\n==========================");
        return numbers;
    }
}
