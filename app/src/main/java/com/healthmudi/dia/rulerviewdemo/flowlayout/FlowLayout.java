package com.healthmudi.dia.rulerviewdemo.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>description:特殊样式的流失布局，最后一view单独为一行</p>
 * <p>created on: 2019/5/17 8:18</p>
 *
 * @author tck
 * @version 3.5
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";
    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        //记录每一行的宽度，width不断取最大宽度
        int lineWidth = 0;
        //记录每一行的宽度，不断累加每行最大高度获取height
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            //测量每一个子View的宽和高
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            //当前子View实际占据的宽高
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            int childWidth = childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            //如果加入当前childView，超出最大宽度，则得到目前最大宽度给width，类加height 然后开启新行
            if ((lineWidth + childWidth > sizeWidth) || (i == childCount - 1)) {
                // 取最大的宽度
                width = Math.max(lineWidth, childWidth);
                //重新开启新行，重新计算
                lineWidth = childWidth;
                //叠加当前高度
                height += childHeight;
                //记录下一行高度
                lineHeight = childHeight;
            } else {
                // 累加值lineWidth,lineHeight取最大高度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        int lineWidth = 0;
        int lineHeight = 0;
        int width = getWidth();
        int childCount = getChildCount();
        List<View> lineViews = new ArrayList<>();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;
            int childHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if ((lineWidth + childWidth > width) || (i == childCount - 1)) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }
            //如果不需要换行，则累加
            lineWidth += childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
            lineViews.add(childView);
        }

        // 记录最后一行
        mAllViews.add(lineViews);
        mLineHeight.add(lineHeight);
        int left = 0;
        int top = 0;

        // 得到总行数
        int size = mAllViews.size();
        for (int i = 0; i < size; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);
            for (View view : lineViews) {
                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + view.getMeasuredWidth();
                int bc = tc + view.getMeasuredHeight();
                view.layout(lc, tc, rc, bc);
                left += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;

        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

}
