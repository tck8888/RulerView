package com.healthmudi.dia.rulerviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;


/**
 * <p>description:https://blog.csdn.net/qq_33453910/article/details/85266903</p>
 * <p>created on: 2019/4/15 19:44</p>
 *
 * @author tck
 * @version 3.3
 */
public class RulerView extends View {
    String TAG = "RulerView";
    private Paint indicatorPaint;
    private Paint degreeLinePaint;
    private Paint degreeTextPaint;
    private Paint textPaint;

    private int screenWidth;
    private int baseWidth = 750;

    private float indicatorHeight;
    private float rightBorder;
    private float leftBorder;
    private float lineDegreeSpace;
    private int lineCount = 16;
    private int shortDegreeLine;
    private int longDegreeLine;
    private int rulerHeight;
    private int currentNum;
    private float mXDown;
    private float mLastMoveX;
    private float mCurrentMoveX;

    /**
     * 监控手势速度类
     */
    private VelocityTracker mVelocityTracker;
    //惯性最大最小速度
    protected int mMaximumVelocity, mMinimumVelocity;


    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        indicatorHeight = screenWidth * 128f / baseWidth;
        rulerHeight = screenWidth * (128 + 27 + 42) / baseWidth;

        shortDegreeLine = screenWidth * 18 / baseWidth;
        longDegreeLine = screenWidth * 36 / baseWidth;
        lineDegreeSpace = screenWidth * 54f / baseWidth;
        leftBorder = screenWidth * 20f / baseWidth;
        rightBorder = lineDegreeSpace * lineCount * 2 + leftBorder ;


        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStrokeWidth(screenWidth * 1f / baseWidth);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setColor(Color.parseColor("#ffff6253"));

        degreeLinePaint = new Paint();
        degreeLinePaint.setAntiAlias(true);
        degreeLinePaint.setStrokeWidth(screenWidth * 4f / baseWidth);
        degreeLinePaint.setStyle(Paint.Style.FILL);
        shortLineDegreeColor = Color.parseColor("#ffE8E8E8");
        longLineDegreeColor = Color.parseColor("#ff9F9F9F");
        degreeLinePaint.setColor(shortLineDegreeColor);

        degreeTextPaint = new Paint();
        degreeTextPaint.setAntiAlias(true);
        degreeTextPaint.setTextSize(screenWidth * 24f / 750);
        degreeTextPaint.setColor(Color.parseColor("#ff9F9F9F"));
        degreeTextPaint.setFakeBoldText(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(screenWidth * 36f / 750);
        textPaint.setColor(Color.parseColor("#ffFF6253"));
        textPaint.setFakeBoldText(true);


        //添加速度追踪器
        mVelocityTracker = VelocityTracker.obtain();
        //获取最大速度
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        //获取最小速度
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
        //创建滑动实例
        mScroller = new Scroller(context);
        Log.d(TAG, "lineDegreeSpace=" + lineDegreeSpace);
    }

    private int longLineDegreeColor;
    private int shortLineDegreeColor;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSpecSize, rulerHeight);

        greenPointX = getMeasuredWidth() / 2;
    }

    private int greenPointX;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = leftBorder;
        float y = indicatorHeight - screenWidth * 30f / baseWidth;

        float indicatorRadius = screenWidth * 12f / baseWidth;


        for (int i = 0; i <= lineCount * 2; i++) {
            //画长刻度
            if (i % 2 == 0) {
                degreeLinePaint.setColor(longLineDegreeColor);
                canvas.drawLine(x, y - longDegreeLine, x, y, degreeLinePaint);
                //画刻度值
                String number = String.valueOf(40 + (i / 2) * 10);
                float textWidth = degreeTextPaint.measureText(number);
                canvas.drawText(
                        number,
                        x - textWidth / 2,
                        y - longDegreeLine - screenWidth * 10f / 750,
                        degreeTextPaint
                );
            } else {
                degreeLinePaint.setColor(shortLineDegreeColor);
                canvas.drawLine(x, y - shortDegreeLine, x, y, degreeLinePaint);
            }
            x += lineDegreeSpace;
        }

        canvas.drawRoundRect(
                getWidth() / 2f - indicatorRadius + getScrollX(),
                0,
                getWidth() / 2f + indicatorRadius + getScrollX(),
                indicatorHeight,
                indicatorRadius,
                indicatorRadius,
                indicatorPaint);
        //画当前刻度值
        Log.d(TAG, "onDraw: " + (greenPointX + getScrollX() - leftBorder) / lineDegreeSpace);
        int ceil = Math.round((greenPointX + getScrollX() - leftBorder) / lineDegreeSpace);

        currentNum = 40 + (ceil * 5);
        float textWidth = textPaint.measureText(currentNum + "cm");
        canvas.drawText(
                currentNum + "cm",
                greenPointX - textWidth / 2 + getScrollX(),
                screenWidth * (128 + 42) / 750,
                textPaint);
    }

    /**
     * 用于滑动实例
     *
     * @param context
     */
    private Scroller mScroller;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录初始触摸屏幕下的坐标
                mXDown = ev.getRawX();
                mLastMoveX = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentMoveX = ev.getRawX();
                //本次的滑动距离
                int scrolledX = (int) (mLastMoveX - mCurrentMoveX);
                //左右边界中 自由滑动
                scrollBy(scrolledX, 0);
                mLastMoveX = mCurrentMoveX;
                break;
            case MotionEvent.ACTION_UP:
                //处理松手后的Fling 获取当前事件的速率，1毫秒运动了多少个像素的速率，1000表示一秒
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                //获取横向速率
                int velocityX = (int) mVelocityTracker.getXVelocity();
                //滑动速度大于最小速度 就滑动
                if (Math.abs(velocityX) > mMinimumVelocity) {
                    fling(-velocityX);
                }
                //刻度之间检测
                moveRecently();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void moveRecently() {
        float distance = (greenPointX + getScrollX() - leftBorder) % lineDegreeSpace;
        //指针的位置在小刻度中间位置往后（右）
        if (distance >= lineDegreeSpace / 2f) {
            scrollBy((int) (lineDegreeSpace - distance), 0);
        } else {
            scrollBy((int) (-distance), 0);
        }
    }

    private void fling(int vX) {
        mScroller.fling(getScrollX(), 0, vX, 0, (int) (-rightBorder), (int) rightBorder, 0, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //这是最后mScroller的最后一次滑动 进行刻度边界检测
            if (!mScroller.computeScrollOffset()) {
                moveRecently();
            }

        }

    }

    @Override
    public void scrollTo(int x, int y) {
        //左边界检测
        if (x < leftBorder - getWidth() / 2) {
            x = (int) (-getWidth() / 2 + leftBorder);
        }
        //有边界检测
        if (x + getWidth() / 2 > rightBorder) {
            x = (int) (rightBorder - getWidth() / 2f );
        }
        if (x != getScrollX()) {

            super.scrollTo(x, y);
        }

    }


    public int getCurrentNum() {
        return currentNum;
    }
}
