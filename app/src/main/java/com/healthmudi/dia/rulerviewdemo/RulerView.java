package com.healthmudi.dia.rulerviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>description:https://blog.csdn.net/qq_33453910/article/details/85266903</p>
 * <p>created on: 2019/4/15 19:44</p>
 *
 * @author tck
 * @version 3.3
 */
public class RulerView extends View {

    private int screenWidth;
    private Paint bgPaint;
    private Paint indicatorPaint;

    private int baseWidth = 750;

    private int indicatorHeight = 0;
    private int bgHeight = 0;
    private int bgRadius = 0;
    private int indicatorWith;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        indicatorWith = screenWidth * 24 / baseWidth;
        indicatorHeight = screenWidth * 128 / baseWidth;
        bgHeight = screenWidth * 108 / baseWidth;
        bgRadius = bgHeight / 2;

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(screenWidth * 2 / baseWidth);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(Color.parseColor("#fff4f4f4"));

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStrokeWidth(screenWidth * 1 / baseWidth);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setTextAlign(Paint.Align.CENTER);
        indicatorPaint.setColor(Color.parseColor("#ffff6253"));


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSpecSize, indicatorHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(
                0,
                indicatorWith / 2,
                getWidth(),
                indicatorHeight - indicatorWith / 2,
                bgRadius,
                bgRadius,
                bgPaint);
        canvas.drawRoundRect(
                getWidth() / 2 - indicatorWith / 2,
                0, getWidth() / 2 + indicatorWith / 2,
                indicatorHeight,
                indicatorWith / 2,
                indicatorWith / 2,
                indicatorPaint);
    }
}
