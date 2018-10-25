package com.reusables;

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
	
	public static void PRINT_ERROR(String message) {
		if(isPrintingError) {
			System.out.println(message);
		}
	}
}
