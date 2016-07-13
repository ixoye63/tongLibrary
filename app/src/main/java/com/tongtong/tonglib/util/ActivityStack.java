package com.tongtong.tonglib.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;

public class ActivityStack 
{
	private List<Activity> stack = new ArrayList();
	
	public void addActivityStack(Activity pActivity)
	{
		stack.add(pActivity);
	}
	
	public void clearActivityStack()
	{
		Iterator iterator = stack.iterator();
		
		while (iterator.hasNext())
		{
			((Activity)iterator.next()).finish();
		}
		
		stack.clear();
	}

	public void clearActivityStack(Activity pActivity)
	{
		Iterator iterator = stack.iterator();
		
		while (iterator.hasNext())
		{
			Activity activity = (Activity)iterator.next();
			
			if (activity.equals(pActivity))
			{
				activity.finish();
				
				// stack.remove(pActivity);
			}
		}
	}
	
	public void removeActivityStack(Activity pActivity)
	{
		this.stack.remove(pActivity);
	}

}

