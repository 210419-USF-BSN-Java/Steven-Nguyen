package com.AMT.util;

import java.util.Scanner;

public class SingleScanner {
	private static Scanner sc;

	private SingleScanner() {
	}

	public static synchronized Scanner getScanner() {

		sc = new Scanner(System.in);
		return sc;

	}
}
