package com.ashlikun.xtablayout2.tab.common.tabview;

import android.content.Context;

import com.ashlikun.xtablayout2.XTabUtils;


/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:19
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍： 两种颜色过渡的指示器标题
 */

public class ColorTransitionTabView extends SimpleTabView {

    public ColorTransitionTabView(Context context) {
        super(context);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = XTabUtils.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = XTabUtils.eval(enterPercent, mNormalColor, mSelectedColor);
        setTextColor(color);
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }
}
