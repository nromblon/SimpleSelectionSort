package com.algorithm.parallel;

import java.util.ArrayList;

import com.reusables.General;

public class ParallelSelectionSort implements Runnable {
	private Thread thread;
	private String threadName;
	
	public static int MIN_INDEX;
	
	private boolean flag1 = false;
	private boolean flag2 = false;
	private ArrayList<RunnableSelectionSort> runnableSelectionSortList;
	private ArrayList<Integer> itemList;
	
//	private RunnableSelectionSort runnableSelectionSort1;
//	private RunnableSelectionSort runnableSelectionSort2;
	
//	public void sort(ArrayList<Integer> itemList) {
//		General.PRINT(this.getClass().getSimpleName()+" sort");
//		this.runnableSelectionSort1 = new RunnableSelectionSort("rSS 1");
//		this.runnableSelectionSort2 = new RunnableSelectionSort("rSS 2");
//		
//		this.runnableSelectionSort1.start(itemList);
//		this.runnableSelectionSort2.start(itemList);
//	}

	
	/**
	 * Thread start function.
	 */
	public void start(ArrayList<Integer> itemList, int splitCount) {
		General.PRINT(this.getClass().getSimpleName()+" start");
		this.setItemList(itemList);
		this.initializeThreads(itemList, splitCount);
		if(this.getThread() == null) {
			this.setThread(new Thread(this, this.getThreadName()));
			this.getThread().start();
		}
		
//		this.runnableSelectionSort1 = new RunnableSelectionSort("rSS 1");
//		this.runnableSelectionSort2 = new RunnableSelectionSort("rSS 2");
		
//		this.runnableSelectionSort1.start(itemList);
//		this.runnableSelectionSort2.start(itemList);
		
		
		
	}
	
	public void initializeThreads(ArrayList<Integer> itemList, int splitCount) {
		ArrayList<Integer> splitSelection = this.splitSelection(itemList, splitCount);
		System.out.println("split selection size "+splitSelection.size());
		this.runnableSelectionSortList = new ArrayList<RunnableSelectionSort>();
		for(int i = 0; i < splitSelection.size(); i++) {
			this.runnableSelectionSortList.add(new RunnableSelectionSort("rSS "+i, splitSelection.get(i), splitSelection.get(i+1)));
		}
	}
	
	public void runThreads(ArrayList<Integer> itemList) {
		for(int i = 0; i < runnableSelectionSortList.size(); i++) {
			this.runnableSelectionSortList.get(i).start(itemList);
		}
	}
	
	@Override
	public void run() {
		this.runThreads(this.getItemList());
		for(int i = 0; i < runnableSelectionSortList.size(); i++) {
			while(!runnableSelectionSortList.get(i).isDone()) {
				System.out.println("Waiting for thread "+i);
			}
		}
	}
	
	/**
	 * Returns an arraylist of indices from 0 to list count.
	 * @param itemList
	 * @param splitCount
	 * @return
	 */
	public ArrayList<Integer> splitSelection(ArrayList<Integer> itemList, int splitCount) {
		ArrayList<Integer> splitIndices = new ArrayList<Integer>();
		int size = itemList.size()/splitCount;
		
		for(int i = 0; i < size; i+=size) {
			splitIndices.add(size*i);
			System.out.println("splitIndex: "+i);
		}
		splitIndices.add(itemList.size());
		System.out.println("splitIndex: "+itemList.size());
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
}
