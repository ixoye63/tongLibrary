package com.tongtong.tonglib.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil 
{
	static SimpleDateFormat dayTimeMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static String getCurrentTimeMillis()
	{
		long time = System.currentTimeMillis(); 		 
		String strDT = dayTimeMillis.format(new Date(time));
		
		return(strDT);
	}

}
