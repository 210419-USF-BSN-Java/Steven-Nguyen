package com.AMT.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class managerMenu {
	
public static void fileMenu() {
	try {
		BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\Steven\\Desktop\\manager.txt"));
		String s;
		while((s = br.readLine()) != null) {
			System.out.println(s);
		}
		br.close();
	}catch(Exception e) {
		e.printStackTrace();
	}
}
}
