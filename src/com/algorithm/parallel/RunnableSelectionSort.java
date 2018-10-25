package com.algorithm.parallel;

import java.util.ArrayList;
import java.util.List;

import com.reusables.General;

public class RunnableSelectionSort implements Runnable {
	private Thread thread;
	private String threadName;
	private ArrayList<Integer> itemList;
	
	private int localMin;
	private int startIndex;
	private int endIndex;
	
	public RunnableSelectionSort(String name) {
		this.setThreadName(name);
	}
	
	/**
	 * Thread start function.
	 */
	public void start(ArrayList<Integer> list) {
		General.PRINT(this.getClass().getSimpleName()+" start");
		
		if(this.getThread() == null) {
			this.setThread(new Thread(this, this.getThreadName()));
			this.getThread().start();
		}
	}
	
	@Override
	public void run() {
		General.PRINT(this.getClass().getSimpleName()+" run");
		this.splitSelection(this.getItemList(), startIndex, endIndex);
	}
	
	/**
	 * Returns the index of the local minimum in the passed list.
	 * @param list
	 * @param start
	 * @param end
	 * @return
	 */
	public int splitSelection(ArrayList<Integer> list, int start, int end) {
		int localMin = start;
		
		for(int i = start+1; i < end; i++) {
			if(list.get(i) < list.get(localMin)) {
				localMin = i;
			}
		}
		return localMin;
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
}
