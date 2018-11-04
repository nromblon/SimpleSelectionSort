package com.algorithm.parallel.executor;

import java.util.ArrayList;

public class MultithreadMonitor {

    private ArrayList<RunnableSelectionExecutor> executors;
    private boolean[] flags;
    private int executorSize;
    private int callerCount;
    public MultithreadMonitor(ArrayList<RunnableSelectionExecutor> executors){
        this.executors = executors;
        flags = new boolean[executors.size()];
        this.executorSize = executors.size();
        // Associate executors w/ this monitor
        for(int i=0; i<executors.size(); i++){
            flags[i] = false;
            this.executors.get(i).setMonitor(this);
            this.executors.get(i).setMonitorIndex(i);
        }
    }
    public void reset() {
    	this.callerCount = 0;
    	for(int i=0; i<executors.size(); i++){
            flags[i] = false;
    	}
    }
    synchronized public void setDone(RunnableSelectionExecutor caller, boolean val){
        boolean hasBeenAllTrue = true;
        @SuppressWarnings("unused")
		int callerIndex = -1;
//        flags[caller.getMonitorIndex()] = val;
        this.callerCount += 1;
        System.out.println("caller count is "+this.callerCount + " index is "+caller.getMonitorIndex());
        
        if(this.callerCount == this.executorSize) {
        	System.out.println("Entered RELEASE");
        	this.release();
        }

        /*
        for(int i = 0; i < flags.length; i++){
//            if(executors.get(i) == caller) {
//                callerIndex = i;
//                flags[i] = val;
//            }
            if(!flags[i]) {
            	firstFalse = i;
                hasBeenAllTrue = false;
            }
        }
//        System.out.println("setDone() called by: executor #"+callerIndex);
	
        if(hasBeenAllTrue)
            release();
        else {
//        	System.out.println("False on "+firstFalse);
        }
        */
    }

    synchronized public void release(){
        System.out.println("release");
        this.notify();
    }

}
