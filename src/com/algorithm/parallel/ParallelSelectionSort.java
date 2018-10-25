package com.algorithm.parallel;

import java.util.ArrayList;

import com.reusables.General;

public class ParallelSelectionSort {
	private RunnableSelectionSort runnableSelectionSort1;
	
	public void sort(ArrayList<Integer> itemList) {
		General.PRINT(this.getClass().getSimpleName()+" sort");
		this.runnableSelectionSort1 = new RunnableSelectionSort("rSS 1");
		this.runnableSelectionSort1.start(itemList);
	}
}
