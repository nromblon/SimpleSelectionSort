package com.reusables;

import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Stopwatch {
    static Queue<Instant> startQueue;
//    static Instant start;
    static Instant end;

    public static void start(){
        Instant start = Instant.now();
        if(startQueue == null)
            startQueue = new ConcurrentLinkedQueue<>();
        startQueue.add(start);
    }

    public static long end() throws Exception{
        if(startQueue.isEmpty())
            throw new Exception("Stopwatch has not been started yet!");
        end = Instant.now();
        Instant start = startQueue.poll();
        return Duration.between(start,end).toMillis();
    }

    public static long endAndPrint() throws Exception{
        if(startQueue.isEmpty())
            throw new Exception("Stopwatch has not been started yet!");
        end = Instant.now();
        Instant start = startQueue.poll();
        long duration = Duration.between(start,end).toMillis();
        System.out.println("Stopped watch after "+duration+"ms.");
		System.out.println();
        return duration;
    }

    public static void reset(){
        startQueue.clear();
        end = null;
    }
}
