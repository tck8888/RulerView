package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

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
