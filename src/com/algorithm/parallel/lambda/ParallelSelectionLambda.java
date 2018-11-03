package com.algorithm.parallel.lambda;

import com.reusables.Stopwatch;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParallelSelectionLambda {
    int localMins[];
    Object syncIter;
    boolean subDone;

    ReadWriteLock RWlock;
    Lock writeLock;
    Lock readLock;

    public ParallelSelectionLambda(){
        RWlock = new ReentrantReadWriteLock();
        writeLock = RWlock.writeLock();
        readLock = RWlock.readLock();
        syncIter = new Object();
    }

    public void parallelSelectionSort(ArrayList<Integer> list, int numPartitions){
        int size = list.size();
        int minIndex;
        localMins = new int[numPartitions];
        Thread[] threads = new Thread[numPartitions];

        // Stopwatch
        Stopwatch.start("Parallel lambda");
        for(int i = 0; i < size; i++){
            // Initialize
            minIndex = i;
            resetLocalMins();

            // Find the indeces
            double rawSizePerPartition = (double)(size - i) / (double)numPartitions;
            boolean isExact = rawSizePerPartition % 1 == 0 ? true : false;
            int sizePerPartition = (int)Math.floor(rawSizePerPartition);
            int curIndex = i;
            int nextIndex = curIndex;
            // Start the threads
            subDone = false;
            for(int t = 0; t < numPartitions; t++){
                nextIndex += sizePerPartition;
                if(!isExact && t == numPartitions - 1)
                    nextIndex += 1;

                int threadIndex = t, a = curIndex, b = nextIndex;
                LambdaRunnable r = new LambdaRunnable(list,a,b,threadIndex,this);
//                Runnable r = () -> splitSelection(list,a,b,threadIndex);
                threads[t] = new Thread(r);
                threads[t].start();
                curIndex = nextIndex;
            }

            // Wait until all threads are finished
            synchronized (syncIter){
                try{
                    syncIter.wait();
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
//            System.out.println("Done waiting");

            // Compare localMins
            for(int m = 0; m < localMins.length; m++){
                if(list.get(localMins[m]) < list.get(minIndex))
                    minIndex = localMins[m];
            }

            swap(list,i,minIndex);
        }
        try {
            Stopwatch.endAndPrint();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void splitSelection(ArrayList<Integer> list, int startIndex, int endIndex, int localMinIndex){
        int local = startIndex;
//        System.out.println("Starting thread: "+localMinIndex+" start: "+startIndex+" end: "+endIndex);
        for(int j = startIndex + 1; j < endIndex; j++){
            if(list.get(j) < list.get(local))
                local = j;
        }
        setLocalMin(localMinIndex, local);
    }

    private void resetLocalMins(){
        for(int i = 0; i < localMins.length; i++){
            localMins[i] = -1;
        }
    }

    private void setLocalMin(int index, int val){
//        writeLock.lock();
//        System.out.println("set local min write lock entered");
        localMins[index] = val;
//        writeLock.unlock();
//        System.out.println("set local min write lock released");

//        readLock.lock();
//        System.out.println("set local min read lock entered");
        //Check if all Local mins have been set
        for(int i = 0; i < localMins.length; i++){
            if(localMins[i] == -1){
//                readLock.unlock();
//                System.out.println("set local min read lock released");
                return;
            }
        }
//        readLock.unlock();
        synchronized (syncIter) {
//            System.out.println("set local min read lock released :: All local minimum set");
            syncIter.notify();
        }
    }

    private void swap(ArrayList<Integer> list, int indexA, int indexB){
        int temp = list.get(indexA);
        list.set(indexA,list.get(indexB));
        list.set(indexB,temp);
    }

}
