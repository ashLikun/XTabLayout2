package com.ashlikun.xtablayout2.tab.common;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:15
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：可测量内容区域的指示器标题
 */

public interface IMeasurablePagerTitleView extends ITabView {
    int getContentLeft();

    int getContentTop();

    int getContentRight();

    int getContentBottom();
}
