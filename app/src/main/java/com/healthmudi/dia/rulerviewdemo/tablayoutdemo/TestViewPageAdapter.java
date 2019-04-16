package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 13:29</p>
 *
 * @author tck
 * @version 3.3
 */
public class TestViewPageAdapter extends FragmentPagerAdapter {

    private List<ViewPageEntity> mCentreViewPageEntities;

    public TestViewPageAdapter(FragmentManager fm, List<ViewPageEntity> entities) {
        super(fm);
        mCentreViewPageEntities = entities;
    }

    @Override
    public int getCount() {
        return mCentreViewPageEntities == null ? 0 : mCentreViewPageEntities.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCentreViewPageEntities.get(position).name;
    }

    @Override
    public Fragment getItem(int position) {
        return mCentreViewPageEntities.get(position).fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
