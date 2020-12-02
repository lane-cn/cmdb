package cn.developbranch.sms.util;

import java.security.MessageDigest;

public abstract class StringUtils {

	public static String md5sum(String s) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		char[] chars = s.toCharArray();
		byte[] bytes = new byte[chars.length];
		
		for (int i = 0; i < chars.length; i++) {
			bytes[i] = (byte) chars[i];
		}
		
		byte[] bs = md5.digest(bytes);
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < bs.length; i++) {
			int val = ((int) bs[i]) & 0xff;
			if (val < 16) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(val));
		}
		
		return hex.toString();
	}
}
