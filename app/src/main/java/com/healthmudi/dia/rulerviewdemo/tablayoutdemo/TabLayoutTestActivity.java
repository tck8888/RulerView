package com.healthmudi.dia.rulerviewdemo.tablayoutdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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


    private RadioGroup rgTabLayout;
    private RadioButton rbWorks;
    private RadioButton rbLike;
    private ViewPager viewPage;

    private List<ViewPageEntity> mCentreViewPageEntities = new ArrayList<>();

    private String text1 = "作品 123456";
    private String text2 = "喜欢 123456";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout_test_activity);

        rgTabLayout = (RadioGroup) findViewById(R.id.rg_tab_layout);
        rbWorks = (RadioButton) findViewById(R.id.rb_works);
        rbLike = (RadioButton) findViewById(R.id.rb_like);
        viewPage = (ViewPager) findViewById(R.id.view_page);
        rbWorks.setText(text1);
        rbLike.setText(text2);
        rgTabLayout.check(R.id.rb_works);
        rbWorks.setText(getColorStr(text1, 2, text2.length(), "#FF6152"));
        rbLike.setText(getColorStr(text2, 2, text2.length(), "#D9D9D9"));
        mCentreViewPageEntities.add(new ViewPageEntity("", TestFragment.newInstance()));
        mCentreViewPageEntities.add(new ViewPageEntity("", TestFragment.newInstance()));

        viewPage.setAdapter(new TestViewPageAdapter(getSupportFragmentManager(), mCentreViewPageEntities));


        rgTabLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_like) {
                    viewPage.setCurrentItem(1, true);
                    rbWorks.setText(getColorStr(text1, 2, text1.length(), "#D9D9D9"));
                    rbLike.setText(getColorStr(text2, 2, text1.length(), "#FF6152"));
                } else {
                    viewPage.setCurrentItem(0, true);
                    rbWorks.setText(getColorStr(text1, 2, text2.length(), "#FF6152"));
                    rbLike.setText(getColorStr(text2, 2, text2.length(), "#D9D9D9"));
                }
            }
        });

        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    rgTabLayout.check(R.id.rb_like);
                } else {
                    rgTabLayout.check(R.id.rb_works);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public SpannableString getColorStr(String str, int start, int end, String targetColor) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(targetColor));
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(17f / 16f);
        spannableString.setSpan(sizeSpan01, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
