package com.tongtong.tonglib.adpater;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tongtong.tonglib.ui.TabView;
import com.tongtong.tonglib.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HJKO on 2015-10-02.
 */
public class TabFragementAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private final List<TabItem> mTabItemList = new ArrayList<>();

    public TabFragementAdapter(final Context context, FragmentManager manager, TabLayout tabLayout, ViewPager viewPager) {
        super(manager);
        mContext = context;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabItemList.get(position).tabFragment;
    }

    @Override
    public int getCount() {
        return mTabItemList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return mTabItemList.get(position).;
        return null;
    }

    public void addTab(Fragment fragment, final int drawableResId, final int titleResId, final int titleSelectorResId) {
        TabItem tabItem = new TabItem(fragment, drawableResId, titleResId, titleSelectorResId);
        mTabItemList.add(tabItem);
    }

    public void createTabs() {
        TabItem tabItem;
        int count = mTabItemList.size();

        mTabLayout.setupWithViewPager(mViewPager);

        //
        for(int i=0; i<count; i++) {
            tabItem = mTabItemList.get(i);
            mTabLayout.getTabAt(i).setCustomView(createTabView(tabItem.drawableResId, tabItem.titleResId, tabItem.titleSelectorResId));
        }

        mTabLayout.getTabAt(0).getCustomView().setSelected(true);

    }

    private View createTabView( final int drawableResId, final int titleResId, final int titleSelectorResId) {

        TabView tabItem = new TabView(mContext);
        tabItem.setTitleColor(titleSelectorResId);
        tabItem.setTitleFont(Util.getDefaultFont(), 11F);
        tabItem.setIconImage(drawableResId);
        tabItem.setTitle(titleResId);

        return tabItem;
    }

}

class TabItem {
    Fragment tabFragment;
    int drawableResId;
    int titleResId;
    int titleSelectorResId;

    public TabItem(Fragment fragment, int drawableResId, int titleResId, int titleSelectorResId) {
        this.tabFragment = fragment;
        this.drawableResId = drawableResId;
        this.titleResId = titleResId;
        this.titleSelectorResId = titleSelectorResId;
    }
}
