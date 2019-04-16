package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.healthmudi.dia.rulerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 20:03</p>
 *
 * @author tck
 * @version 3.3
 */
public class MyTabLayoutWidget extends LinearLayout {

    private Context context;
    private RecyclerView recyclerView;
    private ViewPager viewPage;
    private MyTabIndicatorWidget adapter;
    private TestViewPageAdapter testViewPageAdapter;

    private List<TabWidgetImpl> tabWidgets = new ArrayList<>();
    private List<ViewPageEntity> tabFragments = new ArrayList<>();
    private int currentPos = 0;

    public MyTabLayoutWidget(Context context) {
        this(context, null);
    }

    public MyTabLayoutWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayoutWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View.inflate(context, R.layout.my_tab_layout, this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        viewPage = (ViewPager) findViewById(R.id.view_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MyTabIndicatorWidget(context, tabWidgets);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyTabIndicatorWidget.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                currentPos = pos;
                updateTabs();
                viewPage.setCurrentItem(currentPos, true);
            }
        });
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPos = i;
                updateTabs();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    /**
     * 设置数据
     * @param tabs
     * @param fragments
     * @param fm
     */
    public void setData(@NonNull List<TabWidgetImpl> tabs,
                        @NonNull List<ViewPageEntity> fragments,
                        @NonNull FragmentManager fm) {

        setData(tabs, fragments, fm, 0);

    }

    /**
     * 设置数据
     * @param tabs
     * @param fragments
     * @param fm
     * @param defaultSelectedPos 默认选中 的item
     */
    public void setData(@NonNull List<TabWidgetImpl> tabs,
                        @NonNull List<ViewPageEntity> fragments,
                        @NonNull FragmentManager fm, int defaultSelectedPos) {

        if (tabs.size() != fragments.size()) {
            return;
        }
        tabWidgets.clear();
        tabWidgets.addAll(tabs);

        if (defaultSelectedPos > tabs.size() - 1) {
            currentPos = tabs.size() - 1;
        } else {
            currentPos = defaultSelectedPos;
        }



        tabFragments.clear();
        tabFragments.addAll(fragments);

        if (testViewPageAdapter == null) {
            testViewPageAdapter = new TestViewPageAdapter(fm, tabFragments);
            viewPage.setAdapter(testViewPageAdapter);
        } else {
            testViewPageAdapter.notifyDataSetChanged();
        }

        viewPage.setCurrentItem(currentPos);

        updateTabs();
    }


    private void updateTabs() {
        if (tabWidgets == null || tabWidgets.size() == 0 || adapter == null) {
            return;
        }
        if (currentPos > tabWidgets.size() - 1) {
            return;
        }

        for (int i = 0; i < tabWidgets.size(); i++) {
            TabWidgetImpl tabWidget = tabWidgets.get(i);
            if (currentPos == i) {
                tabWidget.isSelected = true;
            } else {
                tabWidget.isSelected = false;
            }
        }

        adapter.notifyDataSetChanged();
    }


}
