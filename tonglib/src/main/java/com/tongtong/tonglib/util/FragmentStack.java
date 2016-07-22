package com.tongtong.tonglib.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class FragmentStack
{
	private List<FragmentGroup> stack = new ArrayList();

	public void add(FragmentGroup pFragmentGroup)
	{
		Fragment fragment = pFragmentGroup.getFragment();
		
		stack.add(pFragmentGroup);
		
		FragmentTransaction ft = pFragmentGroup.getFragmentActivity().getSupportFragmentManager().beginTransaction();
		ft.replace(pFragmentGroup.getContentViewResId(), fragment, fragment.getTag());
		ft.addToBackStack(null);
		ft.commit();
	}


	public void init()
	{
		stack.clear();
	}
	
	
	public void init(FragmentGroup pFragmentGroup)
	{
		stack.clear();		

		Fragment fragment = pFragmentGroup.getFragment();
		stack.add(pFragmentGroup);
		
		FragmentTransaction ft = pFragmentGroup.getFragmentActivity().getSupportFragmentManager().beginTransaction();
		ft.replace(pFragmentGroup.getContentViewResId(), fragment, fragment.getTag());
		ft.addToBackStack(null);
		ft.commit();
	}
	
	public void removeLast()
	{
		int lastLevel = stack.size();
		
		if (lastLevel >= 1)
			stack.remove(lastLevel);
		
		if (lastLevel >= 1)
		{
			FragmentGroup fragmentGroup = stack.get(lastLevel);
			Fragment fragment = fragmentGroup.getFragment();
			
			FragmentTransaction ft = fragmentGroup.getFragmentActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(fragmentGroup.getContentViewResId(), fragment, fragment.getTag());
			ft.commit();
		}
	}

	
}
