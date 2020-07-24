package com.ashlikun.xtablayout2;


import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.tab.common.TabCommon;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonViewPagerAdapter;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 11:08
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：XTabLayout与ViewPager的中间层
 * 简化和ViewPager绑定
 */


public class XTabLayoutMediator {
    public static void bind(final XTabLayout magicIndicator, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    public static TabCommon setupViewPager(final XTabLayout magicIndicator, ViewPager viewPager, TabCommonAdapter adapter) {
        TabCommon tabCommon = new TabCommon(magicIndicator.getContext());
        if (adapter instanceof TabCommonViewPagerAdapter) {
            ((TabCommonViewPagerAdapter) adapter).setViewPager(viewPager);
        }
        tabCommon.setAdapter(adapter);
        magicIndicator.setTab(tabCommon);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
        return tabCommon;
    }
}
