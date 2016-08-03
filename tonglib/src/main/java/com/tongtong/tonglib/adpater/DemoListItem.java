package com.tongtong.tonglib.adpater;

import android.content.Context;
import android.support.v4.app.FragmentActivity;


public class DemoListItem {
	public String mTitle;
	public String mDescription;
	public Class<? extends FragmentActivity> mActivity;

	public DemoListItem(Context context, int titleId, int descriptionId, Class<? extends FragmentActivity> activity) {
		mTitle = context.getResources().getString(titleId);
		mDescription = context.getResources().getString(descriptionId);
		mActivity = activity;
	}
	
	public DemoListItem(String title, String description, Class<? extends FragmentActivity> activity) {
		mTitle = title;
		mDescription = description;
		mActivity= activity;
	}
}
