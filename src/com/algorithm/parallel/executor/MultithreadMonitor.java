package com.algorithm.parallel.executor;

import java.util.ArrayList;

public class MultithreadMonitor {

    private ArrayList<RunnableSelectionExecutor> executors;
    private boolean[] flags;

    public MultithreadMonitor(ArrayList<RunnableSelectionExecutor> executors){
        this.executors = executors;
        flags = new boolean[executors.size()];

        // Associate executors w/ this monitor
        for(int i=0; i<executors.size(); i++){
            flags[i] = false;
            this.executors.get(i).setMonitor(this);
        }
    }

    synchronized public void setDone(RunnableSelectionExecutor caller, boolean val){
        boolean hasBeenAllTrue = true;
        @SuppressWarnings("unused")
		int callerIndex = -1;
        for(int i = 0; i < flags.length; i++){
            if(executors.get(i) == caller) {
                callerIndex = i;
                flags[i] = val;
            }
            if(!flags[i])
                hasBeenAllTrue = false;
        }
//        System.out.println("setDone() called by: executor #"+callerIndex);
        if(hasBeenAllTrue)
            release();
    }

    synchronized public void release(){
//        System.out.println("release");
        this.notify();
    }

}
