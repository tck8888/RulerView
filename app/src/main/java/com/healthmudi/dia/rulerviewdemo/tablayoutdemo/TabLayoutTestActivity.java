package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.healthmudi.dia.rulerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 13:24</p>
 *
 * @author tck
 * @version 3.3
 */
public class TabLayoutTestActivity extends AppCompatActivity {

    private MyTabLayoutWidget tabLayout;


    private List<ViewPageEntity> mCentreViewPageEntities = new ArrayList<>();
    private List<TabWidgetImpl> tabs = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_test_activity);


        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout = (MyTabLayoutWidget) findViewById(R.id.tab_layout);

        mCentreViewPageEntities.add(new ViewPageEntity("", TestFragment.newInstance()));
        mCentreViewPageEntities.add(new ViewPageEntity("", TestFragment2.newInstance()));
        mCentreViewPageEntities.add(new ViewPageEntity("", TestFragment3.newInstance()));

        tabs.add(new TabWidgetImpl("作品", "123"));
        tabs.add(new TabWidgetImpl("喜欢", "123"));
        tabs.add(new TabWidgetImpl("哈哈哈", "123"));

        tabLayout.setData(tabs, mCentreViewPageEntities, getSupportFragmentManager(),4);
    }

}
