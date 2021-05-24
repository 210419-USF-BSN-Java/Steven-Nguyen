package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {

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
			e.printStackTrace();
		}
		return "";
	}
}