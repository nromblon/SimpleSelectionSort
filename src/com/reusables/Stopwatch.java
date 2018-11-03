package com.reusables;

import java.time.Duration;
import java.time.Instant;

public abstract class Stopwatch {
    static Instant start;
    static Instant end;

    public static void start(){
        start = Instant.now();
    }

    public static long end() throws Exception{
        if(start == null)
            throw new Exception("Stopwatch has not been started yet!");
        end = Instant.now();
        return Duration.between(start,end).toMillis();
    }

    public static long endAndPrint() throws Exception{
        if(start == null)
            throw new Exception("Stopwatch has not been started yet!");
        end = Instant.now();
        long duration = Duration.between(start,end).toMillis();
        System.out.println("Stopped watch after "+duration+"ms.");
		System.out.println();
        return duration;
    }

    public static void reset(){
        start = null;
        end = null;
    }
}
