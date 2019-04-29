package com.healthmudi.dia.rulerviewdemo.scrolltoorbydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.healthmudi.dia.rulerviewdemo.R;

/**
 * <p>description:</p>
 * <p>created on: 2019/4/29 14:59</p>
 *
 * @author tck
 * @version 3.3
 */
public class RulerView3 extends View {

    private static final String TAG = "RulerView3";


    //刻度线的宽度
    private float calibrationWidth = 1.0f; //dp

    //短的刻度线的高度
    private float calibrationShort;

    //长的刻度线的高度
    private float calibrationLong;

    //当前View的宽度
    private int width;

    //宽度的中间值
    private int middle;

    //刻度尺最小值
    private float minValue = 1;

    //最大值
    private float maxValue = 100;

    //刻度尺当前值
    private float value = 40;

    //每一格代表的值
    private float per = 1;

    //两条长的刻度线之间的 per 数量
    private int perCount = 10;

    //当前刻度与最小值的距离 (minValue-value)/per*gapWidth
    private float offset;

    //当前刻度与最新值的最大距离 (minValue-maxValue)/per*gapWidth
    private float maxOffset;

    //两个刻度之间的距离
    private float gapWidth = 10.0f; //dp

    //总的刻度数量
    private int totalCalibration;

    private float lastX;

    //被认为是快速滑动的最小速度
    private float minFlingVelocity;

    private Scroller scroller;

    private float dx;


    //速度追踪器
    private VelocityTracker velocityTracker;

    private OnValueChangeListener onValueChangeListener;


    private int longLineDegreeColor;
    private int shortLineDegreeColor;
    private int screenWidth;

    /**
     * 回调接口
     */
    public interface OnValueChangeListener {
        void onChange(float value);
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public RulerView3(Context context) {
        super(context, null);
    }

    public RulerView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        initAttrs(context, attrs);
        init(context);
        calculateAttr();


    }

    private Paint degreeLinePaint;
    private Paint indicatorPaint;
    private Paint textPaint;
    //private Paint bgPaint;

    private float indicatorHeight;
    private float indicatorRadius;
    // private float bgRadius;


    private void init(Context context) {
        minFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        scroller = new Scroller(context);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(screenWidth * 24f / 750);
        textPaint.setColor(Color.parseColor("#ff9F9F9F"));
        textPaint.setFakeBoldText(true);

        degreeLinePaint = new Paint();
        degreeLinePaint.setAntiAlias(true);
        degreeLinePaint.setStrokeWidth(screenWidth * 4f / 750);
        degreeLinePaint.setStyle(Paint.Style.FILL);
        shortLineDegreeColor = Color.parseColor("#ffE8E8E8");
        longLineDegreeColor = Color.parseColor("#ff9F9F9F");

        indicatorRadius = screenWidth * 12f / 750;
        indicatorHeight = screenWidth * 128f / 750;

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStrokeWidth(indicatorRadius * 2);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setColor(Color.parseColor("#ffff6253"));

//        bgRadius = screenWidth * 54f / 750;
//        bgPaint = new Paint();
//        bgPaint.setAntiAlias(true);
//        bgPaint.setStrokeWidth(screenWidth * 3f / 750);
//        bgPaint.setStyle(Paint.Style.STROKE);
//        bgPaint.setColor(Color.parseColor("#fff4f4f4"));

    }

    private void calculateAttr() {
        verifyValues(minValue, value, maxValue);
        offset = (value - minValue) * 10.0f / per * gapWidth;
        maxOffset = (maxValue - minValue) * 10.0f / per * gapWidth;
        totalCalibration = (int) ((maxValue - minValue) * 10.0f / per + 1);
    }

    /**
     * 修正minValue，value，maxValue 的有效性
     *
     * @param minValue
     * @param value
     * @param maxValue
     */
    private void verifyValues(float minValue, float value, float maxValue) {
        if (minValue > maxValue) {
            this.minValue = maxValue;
        }

        if (value < minValue) {
            this.value = minValue;
        }

        if (value > maxValue) {
            this.value = maxValue;
        }
    }

    /**
     * 读取布局文件中的自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        calibrationLong = screenWidth * 36f / 750;
        calibrationShort = screenWidth * 18f / 750;
        per *= 10.0f;
        gapWidth = (screenWidth * 108f / 750) / 10;
    }


    /**
     * 初始化配置参数
     *
     * @param value    当前值
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    public void setValue(float value, float minValue, float maxValue) {
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        //浮点数在计算容易丢失精度，放大10倍
        // this.per = per * 10.0f;
        // this.perCount = perCount;
        calculateAttr();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        middle = width / 2;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + middle);
        //当在布局文件设置高度为wrap_content时，默认为80dp(如果不处理效果和math_parent效果一样)，宽度就不处理了
        if (mode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) indicatorHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCalibration(canvas);
    }


    /**
     * 在画的时候首先找到第一根刻度画的x坐标，接着加上gapWidth接着画下一根，当x超出当前View的宽度则停止
     *
     * @param canvas
     */
    private void drawCalibration(Canvas canvas) {

//        canvas.drawRoundRect(
//                0,
//                indicatorRadius,
//                getWidth(),
//                indicatorHeight - indicatorRadius,
//                bgRadius,
//                bgRadius,
//                bgPaint
//        );
        //当前画的刻度的位置
        float currentCalibration;
        float height = indicatorHeight - screenWidth * 30f / 750;
        String value;

        //计算出左边第一个刻度，直接跳过前面不需要画的可读
        int distance = (int) (middle - offset);
        int left = 0;
        if (distance < 0) {
            left = (int) (-distance / gapWidth);
        }
        currentCalibration = middle - offset + left * gapWidth;
        Log.d(TAG, "drawCalibration: " + currentCalibration);
        while (currentCalibration < width * 10 && left < totalCalibration) {

            //边缘的一根刻度不画
            if (currentCalibration == 0) {
                left++;
                currentCalibration = middle - offset + left * gapWidth;
                continue;
            }
            if (left % perCount == 0) {
                //长的刻度宽度是短的两倍

                value = String.valueOf(minValue + left * per / 10.0f);
                if (value.endsWith(".0")) {
                    value = value.substring(0, value.length() - 2);
                }
                float textY = height - calibrationLong - screenWidth * 10f / 750;
                canvas.drawText(
                        value,
                        currentCalibration - textPaint.measureText(value) / 2,
                        textY,
                        textPaint);

                degreeLinePaint.setColor(longLineDegreeColor);
                canvas.drawLine(
                        currentCalibration,
                        height - calibrationLong,
                        currentCalibration,
                        height,
                        degreeLinePaint);
            } else if (left % 5 == 0) {
                degreeLinePaint.setColor(shortLineDegreeColor);
                canvas.drawLine(
                        currentCalibration,
                        height - calibrationShort,
                        currentCalibration,
                        height,
                        degreeLinePaint);
            }
            left++;
            currentCalibration = middle - offset + left * gapWidth;
        }

        canvas.drawRoundRect(
                getWidth() / 2f - indicatorRadius,
                0,
                getWidth() / 2f + indicatorRadius,
                indicatorHeight,
                indicatorRadius,
                indicatorRadius,
                indicatorPaint
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scroller.forceFinished(true);
                lastX = x;
                dx = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                // Log.d(TAG, "onTouchEvent: " + x);
                dx = lastX - x;
                validateValue();
                break;
            case MotionEvent.ACTION_UP:
                smoothMoveToCalibration();
                calculateVelocity();
                return false;
            default:
                return false;

        }
        lastX = x;
        return true;
    }

    /**
     * 滑动结束后，若是指针在2条刻度之间时，需要让指针指向最近的可读
     */
    private void smoothMoveToCalibration() {
        offset += dx;
        if (offset < 0) {
            offset = 0;
        } else if (offset > maxOffset) {
            offset = maxOffset;
        }
        lastX = 0;
        dx = 0;
        value = minValue + Math.round(Math.abs(offset) / gapWidth) * per / 10.0f;
        offset = (value - minValue) * 10.0f / per * gapWidth;
        if (onValueChangeListener != null) {
            onValueChangeListener.onChange(value);
        }
        postInvalidate();
    }


    /**
     * 计算水平速度 像素/秒
     */
    private void calculateVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        float xVelocity = velocityTracker.getXVelocity(); //计算水平方向的速度（单位秒）
        Log.d(TAG, "xVelocity: " + xVelocity);

        //大于这个值才会被认为是fling
        if (Math.abs(xVelocity) > minFlingVelocity) {
            scroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            invalidate();
        }
    }


    /**
     * 根据滑动距离，重新计算offset，注意它的有效范围
     */
    private void validateValue() {
        offset += dx;
        if (offset < 0) {
            offset = 0;
            dx = 0;
            scroller.forceFinished(true);
        } else if (offset > maxOffset) {
            offset = maxOffset;
            dx = 0;
            scroller.forceFinished(true);
        }
        value = minValue + Math.round(Math.abs(offset) / gapWidth) * per / 10.0f;
        if (onValueChangeListener != null) {
            onValueChangeListener.onChange(value);
        }
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        //返回true表示滑动还没有结束
        if (scroller.computeScrollOffset()) {
            if (scroller.getCurrX() == scroller.getFinalX()) {
                smoothMoveToCalibration();
            } else {
                int x = scroller.getCurrX();
                dx = lastX - x;
                validateValue();
                lastX = x;
            }
        }
    }

    public float getValue() {
        return value;
    }

}
