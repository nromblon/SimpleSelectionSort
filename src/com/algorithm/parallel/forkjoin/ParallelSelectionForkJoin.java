package com.algorithm.parallel.forkjoin;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import com.reusables.CsvWriter;
import com.reusables.Stopwatch;

public class ParallelSelectionForkJoin implements Runnable {
	private Thread thread;
	private String threadName;
	
	private int splitCount;

//	private MultithreadMonitor monitor;

	private boolean flag1 = false;
	private boolean flag2 = false;
//	private ArrayList<RunnableSelectionExecutor> runnableSelectionSortList;
	private ArrayList<Integer> itemList;
//	private ThreadPoolExecutor executor;
	
	private volatile int callCount;
	
	private volatile boolean isDone;
	private ForkJoinPool forkJoinPool;
	
	private volatile int currentMin;
	
	public ParallelSelectionForkJoin() {
		if(this.getThread() == null) {
			this.setThreadName("ParallelSelectionSort");
			this.setThread(new Thread(this, this.getThreadName()));
		}
	}
	/**
	 * Thread start function.
	 */
	public void start(ArrayList<Integer> itemList, int splitCount) {
		this.setItemList(itemList);
		this.setSplitCount(splitCount);
		
		this.getThread().start();
	}
	
	@Override
	public void run() {
	
		System.out.println();
//		System.out.println("PAR_EXEC: Process START");
//		this.initializeThreads(itemList, 0, this.getSplitCount());
		Stopwatch.start("Parallel fork join");
		
		this.currentMin = 0;
		int size = itemList.size();
		ParallelSelectionForkJoinTask forkJoinTask;
		
		for(int h = 0; h < size; h++) {
			
			
			this.setDone(false);
			this.callCount = 0;

			forkJoinTask = new ParallelSelectionForkJoinTask(this, false, this.getItemList(), h, size);
			this.forkJoinPool = new ForkJoinPool(splitCount);
			forkJoinPool.invoke(forkJoinTask);
			
			
			// Block thread until subthreads are done
			while(!this.isDone) {
				// Do nothing
			}
//			System.out.println("done h "+h);
//			currentMin = this.monitor.getCurrentMinIndex();
			// Swap the selected local min here
			swap(this.getItemList(), h, currentMin);
		}
//		System.out.println("PAR_EXEC: Process DONE");
		try {
			Stopwatch.endAndPrint();
		} catch (Exception e){
			e.printStackTrace();
		}
		
//		this.executor.shutdown();
		CsvWriter.write(this.getItemList());
	}
	
	synchronized public void callDone(int minIndex, int minValue) {
		this.callCount += 1;
		
		if(minValue < this.itemList.get(this.currentMin));
		this.currentMin = minIndex;
		if(this.callCount == this.splitCount) {
			this.setDone(true);
		}
	}
	
	public void swap(ArrayList<Integer> list, int index1, int index2) {
		int temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
	/**
	 * Get the minimum index between the two parameters.
	 * @param index1
	 * @param index2
	 * @return
	 */
	public int getMinimum(int index1, int index2) {
		if(this.getItemList().get(index1) < this.getItemList().get(index2)) {
			return index1;
		}
		return index2;
	}

	/**
	 * Returns an arraylist of indices from 0 to list count.
	 * @param itemList
	 * @param startIndex
	 * @param splitCount
	 * @return
	 */
	public ArrayList<Integer> splitSelection(ArrayList<Integer> itemList, int startIndex, int splitCount) {
		ArrayList<Integer> splitIndices = new ArrayList<Integer>();
		int size = (int)Math.floor(((double)itemList.size()-(double)startIndex)/(double)splitCount);
		// System.out.println("SIZE: "+size);
		// System.out.println("START: "+startIndex);
		int index = startIndex;
		for(int i = 0; i < splitCount; i++) {
			splitIndices.add(index);
			// System.out.println("splitIndex: "+index);
			index += size;
		}
		splitIndices.add(itemList.size());
		// System.out.println("splitIndex: "+itemList.size());
		return splitIndices;
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

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public ArrayList<Integer> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Integer> itemList) {
		this.itemList = itemList;
	}
	public int getSplitCount() {
		return splitCount;
	}

	public void setSplitCount(int splitCount) {
		this.splitCount = splitCount;
	}


//	public ScheduledThreadPoolExecutor getExecutor() {
//		return executor;
//	}
//	public void setExecutor(ScheduledThreadPoolExecutor executor) {
//		this.executor = executor;
//	}
	
//	public ThreadPoolExecutor getExecutor() {
//		return executor;
//	}
//	public void setExecutor(ThreadPoolExecutor executor) {
//		this.executor = executor;
//	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
//        System.out.println("release1");
		this.isDone = isDone;
	}
}
