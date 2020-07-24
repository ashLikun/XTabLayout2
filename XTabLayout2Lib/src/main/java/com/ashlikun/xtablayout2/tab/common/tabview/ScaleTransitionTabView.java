package com.ashlikun.xtablayout2.tab.common.tabview;

import android.content.Context;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 14:33
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：带颜色渐变和缩放的指示器标题
 */

public class ScaleTransitionTabView extends ColorTransitionTabView {
    private float mMinScale = 0.75f;

    public ScaleTransitionTabView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }
}
