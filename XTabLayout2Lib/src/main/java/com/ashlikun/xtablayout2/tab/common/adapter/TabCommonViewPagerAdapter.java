package com.ashlikun.xtablayout2.tab.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.XTabUtils;
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
    protected int mTextSize;
    //字体缩放，1就是不缩放
    private float mMinScale = 1f;

    public TabCommonViewPagerAdapter(Context context) {
        init(context);
    }

    public TabCommonViewPagerAdapter(ViewPager viewPager) {
        this(viewPager, null);
    }

    public TabCommonViewPagerAdapter(ViewPager viewPager, ITabIndicator iTabIndicator) {
        this.viewPager = viewPager;
        this.iTabIndicator = iTabIndicator;
        viewPagerAdapter = viewPager.getAdapter();
        mTextSize = XTabUtils.dip2px(viewPager.getContext(), 14);

        init(viewPager.getContext());
    }

    private void init(Context context) {
        if (this.iTabIndicator == null) {
            this.iTabIndicator = new LineTabIndicator(context);
        }
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
        colorTransitionTabView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        return colorTransitionTabView;
    }

    @Override
    public ITabIndicator createIndicator(Context context) {

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

    public int getTextSize() {
        return mTextSize;
    }

    public TabCommonViewPagerAdapter setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        return this;
    }

    public float getMinScale() {
        return mMinScale;
    }

    public TabCommonViewPagerAdapter setMinScale(float mMinScale) {
        this.mMinScale = mMinScale;
        return this;
    }

    public TabCommonViewPagerAdapter setTabIndicator(ITabIndicator iTabIndicator) {
        this.iTabIndicator = iTabIndicator;
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
