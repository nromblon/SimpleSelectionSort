package com.main;
import java.util.ArrayList;

import com.algorithm.parallel.ParallelSelectionSort;

public class Driver {
	public static void main(String[] args) {
		ParallelSelectionSort parallelSelectionSort = new ParallelSelectionSort();
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		itemList.add(5);
		itemList.add(3);
		itemList.add(1);
		itemList.add(2);
		itemList.add(4);
		
		parallelSelectionSort.sort(itemList);
	}
}
