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


	private boolean flag1 = false;
	private boolean flag2 = false;
	private ArrayList<RunnableSelectionExecutor> runnableSelectionSortList;
	private ArrayList<Integer> itemList;
	private ThreadPoolExecutor executor;
	
	/**
	 * Thread start function.
	 */
	public void start(ArrayList<Integer> itemList, int splitCount) {
		this.setItemList(itemList);
		this.setSplitCount(splitCount);
		if(this.getThread() == null) {
			this.setThreadName("ParallelSelectionSort");
			this.setThread(new Thread(this, this.getThreadName()));
		}
		if(this.getExecutor() == null) {
			this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(splitCount);
		}
		this.getThread().start();
	}
	
	
	public void initializeThreads(ArrayList<Integer> itemList, int startIndex, int splitCount) {
		ArrayList<Integer> splitSelection = this.splitSelection(itemList, startIndex, splitCount);
		 // System.out.println("split selection size "+splitSelection.size());
		this.runnableSelectionSortList = new ArrayList<RunnableSelectionExecutor>();
		for(int i = 0; i < splitCount; i++) {
			this.runnableSelectionSortList.add(new RunnableSelectionExecutor("rSS "+i, splitSelection.get(i), splitSelection.get(i+1)));
		}
	}
	public void reinitializeThreads(ArrayList<Integer> itemList, int startIndex, int splitCount) {
		
		if(this.runnableSelectionSortList == null || this.runnableSelectionSortList.size() == 0) {
			this.initializeThreads(itemList, startIndex, splitCount);
		}
		else {
			ArrayList<Integer> splitSelection = this.splitSelection(itemList, startIndex, splitCount);
			 // System.out.println("split selection size "+splitSelection.size());
			for(int i = 0; i < splitCount; i++) {
				this.runnableSelectionSortList.get(i).reset(splitSelection.get(i), splitSelection.get(i+1));
//				(new RunnableSelectionExecutor("rSS "+i, splitSelection.get(i), splitSelection.get(i+1)));
			}
		}
	}
	public void runThreads(ArrayList<Integer> itemList) {
		for(int i = 0; i < runnableSelectionSortList.size(); i++) {
			this.getExecutor().execute(this.runnableSelectionSortList.get(i).start(itemList));
//			this.runnableSelectionSortList.get(i).start(itemList);
			// Log started thread here, or at thread 'run' function directly
		}
	}
	
	@Override
	public void run() {
		System.out.println();
		System.out.println("PAR_EXEC: Process START");
		Stopwatch.start();
		
		int currentMin = 0;

		int size = itemList.size();
		boolean isDone;
		General.PRINT_TIME();
		for(int h = 0; h < size; h++) {
			this.reinitializeThreads(itemList, h, this.getSplitCount());
			this.runThreads(this.getItemList());
			for(int i = 0; i < runnableSelectionSortList.size(); i++) {
				// System.out.println("indices "+runnableSelectionSortList.get(i).getStartIndex()+" "+runnableSelectionSortList.get(i).getEndIndex());
				isDone = runnableSelectionSortList.get(i).isDone();
				do {
					isDone = runnableSelectionSortList.get(i).isDone();
					if(isDone)
						break;
					
				}while(!isDone);
				// Thread 'i' confirmed as done
				
				
				if(i == 0) {
					currentMin = runnableSelectionSortList.get(i).getLocalMin();
				}
				else {
					currentMin = getMinimum(currentMin, runnableSelectionSortList.get(i).getLocalMin());
				}				
			}
			// Swap the selected local min here
			swap(this.getItemList(), h, currentMin);
		}
		System.out.println("PAR_EXEC: Process DONE");
		try {
			Stopwatch.endAndPrint();
		} catch (Exception e){
			e.printStackTrace();
		}
		General.PRINT_TIME();
		this.executor.shutdown();
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


	public ThreadPoolExecutor getExecutor() {
		return executor;
	}


	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}
}
