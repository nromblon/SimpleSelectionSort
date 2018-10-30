package com.algorithm.parallel;

import java.util.ArrayList;
import com.reusables.General;

public class RunnableSelectionSort implements Runnable {
	private Thread thread;
	private String threadName;
	private ArrayList<Integer> itemList;

	private int localMin;
	private int startIndex;
	private int endIndex;
	
	private boolean isDone;
	
	public RunnableSelectionSort(String name, int startIndex, int endIndex) {
		this.setThreadName(name);
		this.setStartIndex(startIndex);
		this.setEndIndex(endIndex);
		this.setDone(false);
	}
	
	/**
	 * Thread start function.
	 */
	public void start(ArrayList<Integer> list) {
		this.setDone(false);
		General.PRINT(this.getClass().getSimpleName()+" start");
		this.setItemList(list);
		
		if(this.getThread() == null) {
			this.setThread(new Thread(this, this.getThreadName()));
			this.getThread().start();
		}
	}
	
	@Override
	public void run() {
		General.PRINT(this.getClass().getSimpleName()+" run");
		this.setLocalMin(this.findLocalMinimum(this.getItemList(), startIndex, endIndex));
		this.setDone(true);
		System.out.println("Done "+threadName);
		this.getThread().interrupt();
	}
	
	/**
	 * Returns the index of the local minimum in the passed list.
	 * @param list
	 * @param start
	 * @param end
	 * @return
	 */
	public int findLocalMinimum(ArrayList<Integer> list, int start, int end) {
		int localMin = start;
		
		for(int i = start+1; i < end; i++) {
			if(list.get(i) < list.get(localMin)) {
				localMin = i;
			}
		}
		return localMin;
	}
	
	public void reset(int startIndex, int endIndex) {
		this.setStartIndex(startIndex);
		this.setEndIndex(endIndex);
	}
	
	/**
	 * Thread name getter.
	 * @param name
	 */
	public void setThreadName(String name) {
		this.threadName = name;
	}
	/**
	 * Thread name setter.
	 * @return
	 */
	public String getThreadName() {
		return this.threadName;
	}
	
	/***
	 * Thread getter.
	 * @return
	 */
	public Thread getThread() {
		return this.thread;
	}
	
	/**
	 * Thread setter.
	 * @param thread
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	/**
	 * Item list getter.
	 * @return
	 */
	public ArrayList<Integer> getItemList() {
		if(this.itemList == null) {
			this.itemList = new ArrayList<Integer>();
		}
		return this.itemList;
	}
	
	public void setItemList(ArrayList<Integer> list) {
		this.itemList = list;
	}
	/**
	 * Local minimum getter.
	 * @return
	 */
	public int getLocalMin() {
		return this.localMin;
	}
	
	/**
	 * Local minimum setter.
	 * @param localMin
	 */
	public void setLocalMin(int localMin) {
		this.localMin = localMin;
	}
	
	/**
	 * Start index getter.
	 * @return
	 */
	public int getStartIndex() {
		return this.startIndex;
	}
	
	/**
	 * Start index setter.
	 * @param startIndex
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	/**
	 * End index getter.
	 * @return
	 */
	public int getEndIndex() {
		return this.endIndex;
	}
	
	/**
	 * End index setter.
	 * @param endIndex
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
}
