package com.algorithm.parallel.executor;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.reusables.CsvWriter;
import com.reusables.General;
import com.reusables.Stopwatch;

public class ParallelSelectionExecutor implements Runnable {
	private Thread thread;
	private String threadName;
	
	private int splitCount;

	private MultithreadMonitor monitor;

	private boolean flag1 = false;
	private boolean flag2 = false;
	private ArrayList<RunnableSelectionExecutor> runnableSelectionSortList;
	private ArrayList<Integer> itemList;
	private ThreadPoolExecutor executor;
	
	private volatile boolean isDone;
	
	public ParallelSelectionExecutor() {
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
		
		if(this.getExecutor() == null) {
			this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(splitCount);
		}
		this.getThread().start();
	}
	
	
	public void initializeThreads(ArrayList<Integer> itemList, int startIndex, int splitCount) {
		ArrayList<Integer> splitSelection = this.splitSelection(itemList, startIndex, splitCount);

		this.runnableSelectionSortList = new ArrayList<RunnableSelectionExecutor>();
		for(int i = 0; i < splitCount; i++) {
//			System.out.println(splitSelection.get(i)+" "+ splitSelection.get(i+1));
			this.runnableSelectionSortList.add(new RunnableSelectionExecutor("rSS "+i, splitSelection.get(i), splitSelection.get(i+1), i));
		}
		// initialize monitor
		this.monitor = new MultithreadMonitor(runnableSelectionSortList, this);
	}
	
	public void reinitializeThreads(ArrayList<Integer> itemList, int startIndex, int splitCount) {
		
		if(this.runnableSelectionSortList == null || this.runnableSelectionSortList.size() == 0) {
			this.initializeThreads(itemList, startIndex, splitCount);
		}
		
		else {
			this.monitor.reset();
			// Find the indeces
			double rawSizePerPartition = (double)(itemList.size() - startIndex) / (double) splitCount;
			boolean isExact = rawSizePerPartition % 1 == 0 ? true : false;
			int sizePerPartition = (int)Math.floor(rawSizePerPartition);
			int curIndex = startIndex;
			int nextIndex = curIndex;
			for(int t = 0; t < runnableSelectionSortList.size(); t++){
				nextIndex += sizePerPartition;
				if(!isExact && t == splitCount -1)
					nextIndex +=1;
//				System.out.println("init thread: "+t+" start:"+curIndex+" end:"+nextIndex);
				this.runnableSelectionSortList.get(t).setStartIndex(curIndex);
				this.runnableSelectionSortList.get(t).setEndIndex(nextIndex);
				curIndex = nextIndex;
			}
		}

	}
	
	public void runThreads(ArrayList<Integer> itemList) {
		for(int i = 0; i < runnableSelectionSortList.size(); i++) {
			this.getExecutor().execute(this.runnableSelectionSortList.get(i).start(itemList));
		}
	}
	
	@Override
	public void run() {

		this.initializeThreads(itemList, 0, this.getSplitCount());
		
		// SPEED Record
		Stopwatch.start("Parallel executor");
		
		// MEMORY Usage (1)
//		Runtime runtime = Runtime.getRuntime();
//        System.gc();
//        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("usedMemoryBefore: "+usedMemoryBefore);
      
		int currentMin = 0;
		int size = itemList.size();;
		for(int h = 0; h < size; h++) {
			this.setDone(false);
			
			// initialize threads
			this.reinitializeThreads(itemList, h, this.getSplitCount());

			// run threads
			this.runThreads(this.getItemList());

//			// debugging only. Comment out on final
//			for(int i =0;i<this.runnableSelectionSortList.size();i++){
//				int st = runnableSelectionSortList.get(i).getStartIndex();
//				int ed = runnableSelectionSortList.get(i).getEndIndex();
//				System.out.println("For element #"+h+":: Thread "+i+" has start index:"+st+" and end index:"+ed);
//			}

			// Block thread until subthreads are done
			while(!this.isDone) {
				// Do nothing
			}
			currentMin = this.monitor.getCurrentMinIndex();
			// Swap the selected local min here
			swap(this.getItemList(), h, currentMin);
			
			
		}

		// MEMORY Usage (2)
//		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("usedMemoryAfter: "+usedMemoryAfter);
//        System.out.println("Memory increased:" + (long)((usedMemoryAfter-usedMemoryBefore)));

        // CPU Usage
//		General.printUsage();
		
		// SPEED Record
		try {
			Stopwatch.endAndPrint();
		} catch (Exception e){
			e.printStackTrace();
		}

		this.executor.shutdown();
		
		// Print CSV
		CsvWriter.write(this.getItemList());
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
			 System.out.println("splitIndex: "+index);
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
	
	public ThreadPoolExecutor getExecutor() {
		return executor;
	}
	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
//        System.out.println("release1");
		this.isDone = isDone;
	}
}
