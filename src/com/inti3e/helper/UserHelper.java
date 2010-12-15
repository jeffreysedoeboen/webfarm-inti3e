package com.inti3e.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class UserHelper.
 */
public final class UserHelper {
	
	/**
	 * String2md5.
	 *
	 * @param password the password
	 * @return the string
	 */
	public static final String string2md5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(password.getBytes());
			byte[] result = md.digest();
			
			StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<result.length; i++) {
	            hexString.append(Integer.toHexString(0xFF & result[i]));
	        }
	        
	        return hexString.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
