package com.algorithm.parallel.forkjoin;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ParallelSelectionForkJoinTask extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	private int start;
	private int end;
	private int size;
	
	private ArrayList<Integer> itemList;
	private int localMinimum;
	
	public ParallelSelectionForkJoinTask(ArrayList<Integer> list, int start, int end) {
		this.itemList = list;
		this.size = this.itemList.size() - start;
	}
	
	

	  @Override
	    protected void compute() {

	        //if work is above threshold, break tasks up into smaller tasks
//	        if(this.start != this.end) {
		  	if(this.size > this.itemList.size()/2) {
//	            System.out.println("Splitting workLoad : " + this.workLoad);
		  		System.out.println("splitting work");
	            List<ParallelSelectionForkJoinTask> subtasks =
	                new ArrayList<ParallelSelectionForkJoinTask>();

	            subtasks.addAll(createSubtasks());

	            for(RecursiveAction subtask : subtasks){
	                subtask.fork();
	            }

	        } else {
//	            System.out.println("Doing workLoad myself: " + this.workLoad);
	        	System.out.println("doing work");
	        	this.setLocalMinimum(this.findLocalMinimum(this.itemList, this.start, this.end));
	        }
	    }

	    private List<ParallelSelectionForkJoinTask> createSubtasks() {
	        List<ParallelSelectionForkJoinTask> subtasks =
	            new ArrayList<ParallelSelectionForkJoinTask>();
	        
	        int mid = this.size/2;
	        
	        ParallelSelectionForkJoinTask subtask1 = new ParallelSelectionForkJoinTask(this.itemList, this.start, mid);
	        ParallelSelectionForkJoinTask subtask2 = new ParallelSelectionForkJoinTask(this.itemList, mid, this.end);

	        subtasks.add(subtask1);
	        subtasks.add(subtask2);

	        return subtasks;
	    }

	
//	private BigInteger calculate(int start, int n) {
//	    return IntStream.rangeClosed(start, n)
//	      .mapToObj(BigInteger::valueOf)
//	      .reduce(BigInteger.ONE, BigInteger::multiply);
//	}
	
	
		
	public int findLocalMinimum(ArrayList<Integer> list, int start, int end) {
		int localMin = start;

		for(int i = start+1; i < end; i++) {
			if(list.get(i) < list.get(localMin)) {
				localMin = i;
			}
		}
		return localMin;
	}

	public ArrayList<Integer> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Integer> itemList) {
		this.itemList = itemList;
	}



	public int getLocalMinimum() {
		return localMinimum;
	}



	public void setLocalMinimum(int localMinimum) {
		this.localMinimum = localMinimum;
	}
}
