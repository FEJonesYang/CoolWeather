package com.example.kuou.module.search.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.example.kuou.R;

/**
 * @Author: JonesYang
 * @Date Created in 2021-06-08-3:01 PM
 * @Description: 搜索界面，自定义的热门城市流式布局
 */
public class HotCityFlowLayout extends ViewGroup {


    private static final int DEFAULT_HORIZONTAL_SPACING = 15;
    private static final int DEFAULT_VERTICAL_SPACING = 0;

    private int mVerticalSpacing;
    private int mHorizontalSpacing;

    public HotCityFlowLayout(Context context) {
        super(context);
    }

    public HotCityFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HotCityFlowLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.HotCityFlowLayout_horizontal_spacing, DEFAULT_HORIZONTAL_SPACING);
            mVerticalSpacing = a.getDimensionPixelSize(
                    R.styleable.HotCityFlowLayout_vertical_spacing, DEFAULT_VERTICAL_SPACING);
        } finally {
            a.recycle();
        }
    }

    public HotCityFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHorizontalSpacing(int pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(int pixelSize) {
        mVerticalSpacing = pixelSize;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        int myWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams layoutParams = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height));
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);

            // 把当前child view放到上一个child view的右边，如果放不下，则换行
            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }

}
