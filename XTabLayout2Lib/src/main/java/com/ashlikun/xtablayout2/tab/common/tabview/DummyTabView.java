package com.ashlikun.xtablayout2.tab.common.tabview;

import android.content.Context;
import android.view.View;

import com.ashlikun.xtablayout2.tab.common.ITabView;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:19
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：空指示器标题，用于只需要指示器而不需要title的需求
 */

public class DummyTabView extends View implements ITabView {

    public DummyTabView(Context context) {
        super(context);
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }
}
