package com.ashlikun.xtablayout2.tab;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:28
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：抽象的ViewPager导航器
 */

public interface ITab {

    ///////////////////////// ViewPager的3个回调
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    /////////////////////////
    int getCurrentItem();

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    void onAttachToMagicIndicator();

    /**
     * 当IPagerNavigator从MagicIndicator上移除时调用
     */
    void onDetachFromMagicIndicator();

    /**
     * ViewPager内容改变时需要先调用此方法，自定义的IPagerNavigator应当遵守此约定
     */
    void notifyDataSetChanged();
}
