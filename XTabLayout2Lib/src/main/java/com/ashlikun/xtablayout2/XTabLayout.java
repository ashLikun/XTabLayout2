package com.ashlikun.xtablayout2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ashlikun.xtablayout2.tab.ITab;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 11:06
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：整个框架的入口，核心
 */

public class XTabLayout extends FrameLayout {
    private ITab mNavigator;

    public XTabLayout(Context context) {
        super(context);
    }

    public XTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNavigator != null) {
            mNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (mNavigator != null) {
            mNavigator.onPageSelected(position);
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (mNavigator != null) {
            mNavigator.onPageScrollStateChanged(state);
        }
    }

    public void setCurrentItem(int position) {
        onPageSelected(position);
    }

    public ITab getNavigator() {
        return mNavigator;
    }

    public void setTab(ITab navigator) {
        if (mNavigator == navigator) {
            return;
        }
        if (mNavigator != null) {
            mNavigator.onDetachFromMagicIndicator();
        }
        mNavigator = navigator;
        removeAllViews();
        if (mNavigator instanceof View) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView((View) mNavigator, lp);
            mNavigator.onAttachToMagicIndicator();
        }
    }


    public int getCurrentItem() {
        if (mNavigator != null) {
            return mNavigator.getCurrentItem();
        }
        return -1;
    }
}
