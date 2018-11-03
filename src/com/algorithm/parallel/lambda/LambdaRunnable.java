package com.algorithm.parallel.lambda;

import java.util.ArrayList;

public class LambdaRunnable implements Runnable {

    private ArrayList<Integer> list;
    private int startIndex, endIndex, threadNo;
    private ParallelSelectionLambda parent;

    public LambdaRunnable(ArrayList<Integer> list, int startIndex, int endIndex, int threadNo,ParallelSelectionLambda parent){
        this.list = list;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.threadNo = threadNo;
        this.parent = parent;
    }

    @Override
    public void run() {
        parent.splitSelection(list,startIndex,endIndex,threadNo);
    }
}
