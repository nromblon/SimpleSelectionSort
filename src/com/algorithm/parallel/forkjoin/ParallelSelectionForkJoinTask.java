package com.algorithm.parallel.forkjoin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ParallelSelectionForkJoinTask extends RecursiveTask<BigInteger>{
	
	private static final long serialVersionUID = 1L;
	private int start = 1;
	private int n;
	private static final int THRESHOLD = 20;
	
	public ParallelSelectionForkJoinTask(int newStart, int newN) {
		this.start = newStart;
		this.n = newN;
	}
	public ParallelSelectionForkJoinTask(int newN) {
		this.n = newN;
	}
	@Override
	protected BigInteger compute() {
		if((n-start) >= THRESHOLD) {
			return ForkJoinTask.invokeAll(createSubtasks())
					.stream()
					.map(ForkJoinTask::join)
					.reduce(BigInteger.ONE, BigInteger::multiply);
		}
		else {
			return calculate(start, n);
		}
	}
	
	private Collection<ParallelSelectionForkJoinTask> createSubtasks() {
		List<ParallelSelectionForkJoinTask> dividedTasks = new ArrayList<>();
		int mid = (start+n)/2;
		dividedTasks.add(new ParallelSelectionForkJoinTask(start, mid));
		dividedTasks.add(new ParallelSelectionForkJoinTask(mid+1, n));
		return dividedTasks;
		
	}
	
	private BigInteger calculate(int start, int n) {
	    return IntStream.rangeClosed(start, n)
	      .mapToObj(BigInteger::valueOf)
	      .reduce(BigInteger.ONE, BigInteger::multiply);
	}
}
