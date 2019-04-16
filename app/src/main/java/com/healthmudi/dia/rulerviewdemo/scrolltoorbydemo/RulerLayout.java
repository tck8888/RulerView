package com.healthmudi.dia.rulerviewdemo.scrolltoorbydemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 15:53</p>
 *
 * @author tck
 * @version 3.3
 */
public class RulerLayout extends FrameLayout {

    private Context context;
    private int screenWidth;
    private int radius;
    private RulerView rulerView;

    public RulerLayout(Context context) {
        this(context, null);
    }

    public RulerLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        radius = screenWidth * 54 / 750;


        View view = new View(context);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(screenWidth * 3 / 750, Color.parseColor("#fff4f4f4"));
        view.setBackground(gradientDrawable);
        LayoutParams layoutParams = new LayoutParams(-1, radius * 2);
        layoutParams.topMargin = screenWidth * 12 / 750;
        addView(view, layoutParams);

        addRulerView();
    }

    private void addRulerView() {
        rulerView = new RulerView(context);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.leftMargin = radius;
        layoutParams.rightMargin = radius;
        addView(rulerView, layoutParams);
    }

    /**
     * 获取刻度
     *
     * @return
     */
    public int getScale() {
        if (rulerView != null) {
            return rulerView.getCurrentNum();
        }
        return 0;
    }
}
