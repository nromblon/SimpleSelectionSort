package com.algorithm.parallel.executor;

import java.util.ArrayList;

public class MultithreadMonitor {

    private ArrayList<RunnableSelectionExecutor> executors;
    private int executorSize;
    private int callerCount;
    

    private ParallelSelectionExecutor mainExecutor;

    public MultithreadMonitor(ArrayList<RunnableSelectionExecutor> executors, ParallelSelectionExecutor executor){
        this.executors = executors;
        this.executorSize = executors.size();
        // Associate executors w/ this monitor
        for(int i=0; i<executors.size(); i++){
            this.executors.get(i).setMonitor(this);
            this.executors.get(i).setMonitorIndex(i);
        }
        this.mainExecutor = executor;
    }

    public void reset() {
    	this.callerCount = 0;
    }

    synchronized public void setDone(boolean val){
//        boolean hasBeenAllTrue = true;
//		int callerIndex = -1;
//        flags[caller.getMonitorIndex()] = val;
        this.callerCount += 1;
//        System.out.println("caller count is "+this.callerCount + " index is "+caller.getMonitorIndex());
        
        if(this.callerCount == this.executorSize) {
//        	System.out.println("Entered RELEASE");
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
//        System.out.println("release");
        this.mainExecutor.setDone(true);
//        synchronized (this) {
//            this.notify();
//		}
    }

}
