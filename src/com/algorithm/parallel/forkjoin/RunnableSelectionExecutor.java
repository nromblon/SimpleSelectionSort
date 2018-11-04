package com.algorithm.parallel.forkjoin;


import java.util.ArrayList;

public class RunnableSelectionExecutor implements Runnable {
	private Thread thread;
	private String threadName;
	private ArrayList<Integer> itemList;

	private int localMin;
	private int startIndex;
	private int endIndex;

	private int threadIndex;
//	private boolean isDone;

	private MultithreadMonitor monitor;
	private int monitorIndex;
	
	public RunnableSelectionExecutor(String name, int start, int end, int threadIndex) {
		this.setThreadName(name);
		this.setThreadIndex(threadIndex);
		this.setThread(new Thread(this, this.getThreadName()));
		this.setStartIndex(start);
		this.setEndIndex(end);
		this.startIndex = start;
		this.endIndex = end;
//		System.err.println(start + " "+ end+" "+this.startIndex);
	}
	
	/**
	 * Thread start function.
	 */
	public Runnable start(ArrayList<Integer> list) {
		// General.PRINT(this.getClass().getSimpleName()+" start");
		this.setItemList(list);
		
//		if(this.getThread() == null) {
//			this.setThread(new Thread(this, this.getThreadName()));
//			this.getThread().start();
//		}
		return this.getThread();
	}
	
	@Override
	public void run() {
//		System.out.println("Loc Min "+this.getStartIndex()+ "endIndex "+this.getEndIndex());
		this.setLocalMin(this.findLocalMinimum(this.getItemList(), this.getStartIndex(), this.getEndIndex()));
//		this.monitor.setDone(true);
		this.monitor.setDone(true, this.getLocalMin(), this.getItemList().get(this.getLocalMin()));
		this.reset();
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

//		System.out.println("starend is "+start+" "+end);
		for(int i = start+1; i < end; i++) {
			if(list.get(i) < list.get(localMin)) {
				localMin = i;
			}
		}
//		System.out.println("found loc min is "+localMin);
		return localMin;
	}
	
	public void reset(int startIndex, int endIndex) {
		this.setStartIndex(startIndex);
		this.setEndIndex(endIndex);
	}
	
	public void reset() {
		this.setStartIndex(this.getStartIndex()+1);
		this.setEndIndex(this.getEndIndex()+1);
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
	public void setStartIndex(int index) {
		if(index < this.getItemList().size()) {
			this.startIndex = index;
		}
		else {
			this.startIndex = this.getItemList().size()-1;
		}
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
		if(endIndex < this.getItemList().size()) {
			this.endIndex = endIndex;
		}
		else {
			this.endIndex = this.getItemList().size()-1;
		}
	}

	
	public void setMonitor(MultithreadMonitor monitor){
		this.monitor = monitor;
	}

	public int getMonitorIndex() {
		return monitorIndex;
	}

	public void setMonitorIndex(int monitorIndex) {
		this.monitorIndex = monitorIndex;
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}
}
