package com.tongtong.tonglib.ui;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tongtong.tonglib.R;

import java.util.ArrayList;
import java.util.List;


import static com.tongtong.tonglib.util.Util.getDefaultFont;

/**
 * Created by KOHeeJin on 2016-05-03.
 */
public class TabLayouts {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Context mContext;
    FragmentManager mFragmentManager;

    private final List<TabLayoutItem> mTabLayoutList = new ArrayList<>();

    public TabLayouts(ViewPager viewPager, TabLayout tabLayout, Context context, FragmentManager fm) {
        mViewPager = viewPager;
        mTabLayout = tabLayout;
        mContext = context;
        mFragmentManager = fm;
    }

    public void addTab(Fragment fragment,  String title, final int drawableResId, final int titleResId) {

        TabLayoutItem tabLayoutItem = new TabLayoutItem(fragment, title, drawableResId, titleResId);
        mTabLayoutList.add(tabLayoutItem);
    }

    public void displayTab() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(mFragmentManager);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        int size = mTabLayoutList.size();
        for (int i =0; i<size; i++) {
            mTabLayout.getTabAt(i).setCustomView(createTabView(mContext, mTabLayoutList.get(i)._drawableResId, mTabLayoutList.get(i)._titleResId));
        }

        mTabLayout.getTabAt(0).getCustomView().setSelected(true);

    }

    private View createTabView(final Context context, final int drawableResId, final int titleResId) {

        TabView tabItem = new TabView(context);
        tabItem.setTitleColor(R.drawable.renew_tabitem_titlelabel_selector);
        tabItem.setTitleFont(getDefaultFont(), 11F);
        tabItem.setIconImage(drawableResId);
        tabItem.setTitle(titleResId);

        return tabItem;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabLayoutList.get(position)._fragment;
        }

        @Override
        public int getCount() {
            return mTabLayoutList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            // return mFragmentTitleList.get(position);
            return null;
        }
    }

    private class TabLayoutItem {
        Fragment _fragment;
        String _title;
        int _drawableResId;
        int _titleResId;

        public TabLayoutItem(Fragment fragment,  String title, final int drawableResId, final int titleResId) {
            _fragment = fragment;
            _title = title;
            _drawableResId = drawableResId;
            _titleResId = titleResId;
        }
    }

}
