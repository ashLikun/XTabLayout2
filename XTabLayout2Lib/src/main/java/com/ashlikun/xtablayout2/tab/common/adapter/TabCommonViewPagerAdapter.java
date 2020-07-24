package com.ashlikun.xtablayout2.tab.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.indicators.LineTabIndicator;
import com.ashlikun.xtablayout2.tab.common.tabview.ScaleTransitionTabView;

/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/15　13:52
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：使用与ViewPager的{@link TabCommonAdapter}
 */
public class TabCommonViewPagerAdapter extends TabCommonAdapter {
    ViewPager viewPager;
    PagerAdapter viewPagerAdapter;
    ITabIndicator iTabIndicator;
    protected int mSelectedColor = Color.BLACK;
    protected int mNormalColor = Color.GRAY;
    //字体缩放，1就是不缩放
    private float mMinScale = 1f;

    public TabCommonViewPagerAdapter() {
    }

    public TabCommonViewPagerAdapter(ViewPager viewPager) {
        this(viewPager, null);
    }

    public TabCommonViewPagerAdapter(ViewPager viewPager, ITabIndicator iTabIndicator) {
        this.viewPager = viewPager;
        this.iTabIndicator = iTabIndicator;
        viewPagerAdapter = viewPager.getAdapter();
    }


    @Override
    public int getCount() {
        return viewPagerAdapter.getCount();
    }

    @Override
    public ITabView createTabView(Context context, int index) {
        ScaleTransitionTabView colorTransitionTabView = new ScaleTransitionTabView(context);
        colorTransitionTabView.setMinScale(mMinScale);
        colorTransitionTabView.setNormalColor(mNormalColor);
        colorTransitionTabView.setSelectedColor(mSelectedColor);
        colorTransitionTabView.setText(viewPagerAdapter.getPageTitle(index));
        return colorTransitionTabView;
    }

    @Override
    public ITabIndicator createIndicator(Context context) {
        if (iTabIndicator == null) {
            LineTabIndicator linePagerIndicator = new LineTabIndicator(context);
            linePagerIndicator.setColors(Color.BLACK);
            iTabIndicator = linePagerIndicator;
        }
        if (iTabIndicator instanceof View) {
            if (((View) iTabIndicator).getParent() != null) {
                ((ViewGroup) ((View) iTabIndicator).getParent()).removeView((View) iTabIndicator);
            }
        }
        return iTabIndicator;
    }

    @Override
    public void onItemClick(ITabView view, int index) {
        super.onItemClick(view, index);
        viewPager.setCurrentItem(index);
    }


    public int getSelectedColor() {
        return mSelectedColor;
    }

    public TabCommonViewPagerAdapter setSelectedColor(int mSelectedColor) {
        this.mSelectedColor = mSelectedColor;
        return this;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public TabCommonViewPagerAdapter setNormalColor(int mNormalColor) {
        this.mNormalColor = mNormalColor;
        return this;
    }

    public <T extends ITabIndicator> T getTabIndicator() {
        if (iTabIndicator == null) {
            return null;
        }
        return (T) iTabIndicator;
    }

    public TabCommonViewPagerAdapter setTabIndicator(ITabIndicator iTabIndicator) {
        this.iTabIndicator = iTabIndicator;
        return this;
    }


    public TabCommonViewPagerAdapter setMode(int mMode) {
        if (iTabIndicator != null && iTabIndicator instanceof LineTabIndicator) {
            ((LineTabIndicator) iTabIndicator).setMode(mMode);
        }
        return this;
    }

    public float getMinScale() {
        return mMinScale;
    }

    public TabCommonViewPagerAdapter setMinScale(float mMinScale) {
        this.mMinScale = mMinScale;
        return this;
    }

    public TabCommonViewPagerAdapter setColors(Integer... mColors) {
        if (iTabIndicator != null && iTabIndicator instanceof LineTabIndicator) {
            ((LineTabIndicator) iTabIndicator).setColors(mColors);
        }
        return this;
    }

    public LineTabIndicator getLineTabIndicator() {
        if (iTabIndicator != null && iTabIndicator instanceof LineTabIndicator) {
            return (LineTabIndicator) iTabIndicator;
        }
        return null;
    }

    public TabCommonViewPagerAdapter setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPagerAdapter = viewPager.getAdapter();
        return this;
    }
}
