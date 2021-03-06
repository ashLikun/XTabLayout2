package com.ashlikun.xtablayout2.tab.common.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.ashlikun.xtablayout2.FragmentContainerHelper;
import com.ashlikun.xtablayout2.XTabUtils;
import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.PositionData;

import java.util.Arrays;
import java.util.List;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:21
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：直线viewpager指示器，带颜色渐变
 */

public class LineTabIndicator extends View implements ITabIndicator {
    public static final int MODE_MATCH_EDGE = 0;   // 直线宽度 == title宽度 - 2 * mXOffset
    public static final int MODE_WRAP_CONTENT = 1;    // 直线宽度 == title内容宽度 - 2 * mXOffset
    public static final int MODE_EXACTLY = 2;  // 直线宽度 == mLineWidth

    private int mMode;  // 默认为MODE_MATCH_EDGE模式

    // 控制动画
    private Interpolator mStartInterpolator = new LinearInterpolator();
    private Interpolator mEndInterpolator = new LinearInterpolator();

    private float mYOffset;   // 相对于底部的偏移量，如果你想让直线位于title上方，设置它即可
    private int mLineHeight;
    private float mXOffset;
    private int mLineWidth;
    private float mRoundRadius;

    private Paint mPaint;
    private List<PositionData> mPositionDataList;
    private List<Integer> mColors;

    private RectF mLineRect = new RectF();

    public LineTabIndicator(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mLineHeight = XTabUtils.dip2px(context, 2);
        mLineWidth = XTabUtils.dip2px(context, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(mLineRect, mRoundRadius, mRoundRadius, mPaint);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPositionDataList == null || mPositionDataList.isEmpty()) {
            return;
        }

        // 计算颜色
        if (mColors != null && mColors.size() > 0) {
            int currentColor = mColors.get(Math.abs(position) % mColors.size());
            int nextColor = mColors.get(Math.abs(position + 1) % mColors.size());
            int color = XTabUtils.eval(positionOffset, currentColor, nextColor);
            mPaint.setColor(color);
        }

        // 计算锚点位置
        PositionData current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position);
        PositionData next = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1);

        float leftX;
        float nextLeftX;
        float rightX;
        float nextRightX;
        if (mMode == MODE_MATCH_EDGE) {
            leftX = current.mLeft + mXOffset;
            nextLeftX = next.mLeft + mXOffset;
            rightX = current.mRight - mXOffset;
            nextRightX = next.mRight - mXOffset;
        } else if (mMode == MODE_WRAP_CONTENT) {
            leftX = current.mContentLeft + mXOffset;
            nextLeftX = next.mContentLeft + mXOffset;
            rightX = current.mContentRight - mXOffset;
            nextRightX = next.mContentRight - mXOffset;
        } else {    // MODE_EXACTLY
            leftX = current.mLeft + (current.width() - mLineWidth) / 2;
            nextLeftX = next.mLeft + (next.width() - mLineWidth) / 2;
            rightX = current.mLeft + (current.width() + mLineWidth) / 2;
            nextRightX = next.mLeft + (next.width() + mLineWidth) / 2;
        }

        mLineRect.left = leftX + (nextLeftX - leftX) * mStartInterpolator.getInterpolation(positionOffset);
        mLineRect.right = rightX + (nextRightX - rightX) * mEndInterpolator.getInterpolation(positionOffset);
        mLineRect.top = getHeight() - mLineHeight - mYOffset;
        mLineRect.bottom = getHeight() - mYOffset;

        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPositionDataProvide(List<PositionData> dataList) {
        mPositionDataList = dataList;
    }

    public float getYOffset() {
        return mYOffset;
    }

    public LineTabIndicator setYOffset(float yOffset) {
        mYOffset = yOffset;
        return this;
    }

    public float getXOffset() {
        return mXOffset;
    }

    public LineTabIndicator setXOffset(float xOffset) {
        mXOffset = xOffset;
        return this;
    }

    public float getLineHeight() {
        return mLineHeight;
    }

    public LineTabIndicator setLineHeight(int lineHeight) {
        mLineHeight = lineHeight;
        return this;
    }

    public float getLineWidth() {
        return mLineWidth;
    }

    public LineTabIndicator setLineWidth(int lineWidth) {
        mLineWidth = lineWidth;
        return this;
    }

    public float getRoundRadius() {
        return mRoundRadius;
    }

    public LineTabIndicator setRoundRadius(float roundRadius) {
        mRoundRadius = roundRadius;
        return this;
    }

    public int getMode() {
        return mMode;
    }

    public LineTabIndicator setMode(int mode) {
        if (mode == MODE_EXACTLY || mode == MODE_MATCH_EDGE || mode == MODE_WRAP_CONTENT) {
            mMode = mode;
        } else {
            throw new IllegalArgumentException("mode " + mode + " not supported.");
        }
        return this;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public List<Integer> getColors() {
        return mColors;
    }

    public LineTabIndicator setColors(Integer... colors) {
        mColors = Arrays.asList(colors);
        return this;
    }

    public Interpolator getStartInterpolator() {
        return mStartInterpolator;
    }

    public LineTabIndicator setStartInterpolator(Interpolator startInterpolator) {
        mStartInterpolator = startInterpolator;
        if (mStartInterpolator == null) {
            mStartInterpolator = new LinearInterpolator();
        }
        return this;
    }

    public Interpolator getEndInterpolator() {
        return mEndInterpolator;
    }

    public LineTabIndicator setEndInterpolator(Interpolator endInterpolator) {
        mEndInterpolator = endInterpolator;
        if (mEndInterpolator == null) {
            mEndInterpolator = new LinearInterpolator();
        }
        return this;
    }
}
