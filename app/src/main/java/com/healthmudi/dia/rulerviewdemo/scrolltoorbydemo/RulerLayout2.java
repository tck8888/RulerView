package com.healthmudi.dia.rulerviewdemo.scrolltoorbydemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthmudi.dia.rulerviewdemo.R;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/16 15:53</p>
 *
 * @author tck
 * @version 3.3
 */
public class RulerLayout2 extends ConstraintLayout {

    private Context context;
    private RulerView3 rulerView;
    private TextView tvDegree;

    public RulerLayout2(Context context) {
        this(context, null);
    }

    public RulerLayout2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View.inflate(context,R.layout.ruler_layout,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rulerView = (RulerView3) findViewById(R.id.ruler_view);
        tvDegree = (TextView) findViewById(R.id.tv_degree);

        rulerView.setOnValueChangeListener(new RulerView3.OnValueChangeListener() {
            @Override
            public void onChange(float value) {
                tvDegree.setText(String.valueOf(value));
            }
        });
    }

    /**
     * 初始化配置参数
     *
     * @param value    当前值
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    public void setRulerData(float value, float minValue, float maxValue){
        rulerView.setValue(value,minValue,maxValue);
    }
}
