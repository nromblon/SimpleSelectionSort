package com.algorithm.sequential;

import java.util.ArrayList;

import com.reusables.General;
import com.reusables.Stopwatch;

/* This code is adapted from Rajat Mishra's*/

public class RunnableSequentialSelectionSort implements Runnable {

	private Thread thread;
	private String threadName;
	private ArrayList<Integer> list;

    public void start(ArrayList<Integer> list) {
//    	General.PRINT(this.getClass().getSimpleName()+" start");
		this.setList(list);

        if(this.getThread() == null) {
    		this.setThreadName("SequentialSelectionSort");
    		this.setThread(new Thread(this, this.getThreadName()));
    		this.getThread().start();
        }
    }

    // Prints the array
    public void printArray(ArrayList<Integer> arr) {
        int n = arr.size();
        for (int i=0; i<n; ++i)
            System.out.print(arr.get(i)+" ");
        System.out.println();
    }

    
	@Override
	public void run() {
//		System.out.println();
//		System.out.println("SEQ: Process START");
//		General.PRINT_TIME();
		Stopwatch.start("Sequential");
//		General.PRINT_TIME();
        int n = list.size();
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("usedMemoryBefore: "+usedMemoryBefore);
        // One by one move boundary of unsorted sublistay
        for (int i = 0; i < n-1; i++) {
            // Find the minimum element in unsorted listay
            int min_idx = i;
            for (int j = i+1; j < n; j++) {
                if (list.get(j) < list.get(min_idx)) {
                    min_idx = j;
                }
            }
            // Swap the found minimum element with the first
            // element
            int temp = list.get(min_idx);
            list.set(min_idx,list.get(i));
            list.set(i,temp);
           
        }
        
//        System.out.println("SEQ: Process DONE");
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("usedMemoryAfter: "+usedMemoryAfter);
        System.out.println("Memory increased:" + (long)((usedMemoryAfter-usedMemoryBefore)));
        General.printUsage();
//		General.printUsage();
        try {
            Stopwatch.endAndPrint();
        } catch (Exception e){
            e.printStackTrace();
        }
//		General.PRINT_TIME();
	}

	public ArrayList<Integer> getList() {
		return list;
	}

	public void setList(ArrayList<Integer> list) {
		this.list = list;
	}
	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
