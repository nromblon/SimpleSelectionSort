package com.algorithm.parallel.forkjoin;

import java.util.ArrayList;

public class MultithreadMonitor {

    private ArrayList<RunnableSelectionExecutor> executors;
    private int executorSize;
    private volatile int callerCount;
    private volatile int currentMinIndex;
    private volatile int currentMinValue;

    private ParallelSelectionForkJoin mainExecutor;

    public MultithreadMonitor(ArrayList<RunnableSelectionExecutor> executors, ParallelSelectionForkJoin executor){
        this.executors = executors;
        this.executorSize = executors.size();
        // Associate executors w/ this monitor
        for(int i=0; i<executors.size(); i++){
            this.executors.get(i).setMonitor(this);
            this.executors.get(i).setMonitorIndex(i);
        }
        this.mainExecutor = executor;
        this.reset();
    }

    public void reset() {
    	this.callerCount = 0;
    	this.setCurrentMinIndex(-1);
    }
    synchronized public void setDone(boolean val, int minIndex, int minValue){
        this.callerCount += 1;
        
        if(this.getCurrentMinIndex() == -1) {
        	this.ChangeMinValue(minIndex, minValue);
        }
        else {
        	if(minValue < this.getCurrentMinValue()) {
            	this.ChangeMinValue(minIndex, minValue);
        	}
        }
        
        if(this.callerCount == this.executorSize) {
        	this.release();
        }
    }
    public void ChangeMinValue(int minIndex, int minValue) {
    	this.setCurrentMinIndex(minIndex);
    	this.setCurrentMinValue(minValue);
    }
    
    synchronized public void setDone(boolean val){
        this.callerCount += 1;
        if(this.callerCount == this.executorSize) {
        	this.release();
        }
    }

    synchronized public void release(){
        this.mainExecutor.setDone(true);
    }

	public int getCurrentMinValue() {
		return currentMinValue;
	}

	public void setCurrentMinValue(int currentMinValue) {
		this.currentMinValue = currentMinValue;
	}

	public int getCurrentMinIndex() {
		return currentMinIndex;
	}

	public void setCurrentMinIndex(int currentMinIndex) {
		this.currentMinIndex = currentMinIndex;
	}

}
