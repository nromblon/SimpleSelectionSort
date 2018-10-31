package com.reusables;

import java.util.ArrayList;

public class General {
	
	private static final boolean isPrinting = true;
	private static final boolean isPrintingError = true;
	
	/**
	 * Static printing function.
	 * @param message
	 */
	public static void PRINT(String message) {
		if(isPrinting) {
			System.out.println(message);
		}
	}
	public static void PRINT(ArrayList<Integer> message, int limit) {
		if(isPrintingError) {
			for(int i = 0; i < limit; i++) {
				System.out.println(message.get(i)+" ");
			}
		}
	}
	public static void PRINT_ERROR(String message) {
		if(isPrintingError) {
			System.out.println(message);
		}
	}
}
