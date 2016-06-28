package com.tongtong.tonglib.util;

public class StringUtil 
{
	public static String rtrim(String str) 
	{
		int len = str.length();
		while ((0 < len) && (str.charAt(len - 1) <= ' ')) {
			len--;
		}
		return str.substring(0, len);
	}

	/**
	 * str이 null 이거나 "", "    " 일경우 return true
	 */
	public static boolean isNull(String str) 
	{
		return (str == null || (str.trim().length()) == 0);
	}

	public static boolean isNotNull(String str) 
	{
		/**
		 * isNull이 true이면 false false이면 true
		 */
		if (isNull(str)) {
			return false;
		} else {
			return true;
		}
	}

}
