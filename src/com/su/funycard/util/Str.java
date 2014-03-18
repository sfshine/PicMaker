package com.su.funycard.util;

import java.security.MessageDigest;

/**
 * 字符串工具类
 * 
 * @author sfshine
 * 
 */
public class Str {

	/**
	 * @category 为空返回真
	 */
	public static boolean isNull(String str) {// 字符串判断是否为空
		boolean can = "".equals(str) || str == null || str.length() == 0
				|| "null".equals(str);
		if (can)
			return true;// 为空返回真
		else
			return false;
	}

	public final static String md5(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
