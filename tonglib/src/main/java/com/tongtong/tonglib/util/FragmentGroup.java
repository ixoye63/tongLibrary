package com.tongtong.tonglib.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FragmentGroup 
{
	private FragmentActivity mFragmentActivity;
	private Fragment mFragment;
	private int mContentViewResId;
	
	public FragmentGroup(FragmentActivity pFragmentActivity, Fragment pFragment, int pContentViewResId)
	{
		mFragmentActivity = pFragmentActivity;
		mFragment = pFragment;
		mContentViewResId = pContentViewResId;
	}
		
	public FragmentActivity getFragmentActivity()
	{
		return mFragmentActivity;
	}

	public Fragment getFragment()
	{
		return mFragment;
	}
	
	public int getContentViewResId()
	{
		return mContentViewResId;
	}
}
