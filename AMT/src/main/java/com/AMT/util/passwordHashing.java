package com.AMT.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordHashing {
//https://www.youtube.com/watch?v=qSTZVlo2lr0
//Guide to hashing
	public static String doHashing(String pw) {
		try {
			MessageDigest messageDigest =MessageDigest.getInstance("MD5");
			
			messageDigest.update(pw.getBytes());
			
			byte[] resultByteArray = messageDigest.digest();
			
			StringBuilder sb = new StringBuilder();
			for(byte b: resultByteArray) {
				sb.append(String.format("%02x", b));
				
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			SingleLogger.getLogger().info(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
}
