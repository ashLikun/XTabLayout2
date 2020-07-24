package com.ashlikun.xtablayout2.tab.common;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:13
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：抽象的指示器标题，适用于 {@link com.ashlikun.xtablayout2.tab.common.TabCommon}
 */

public interface ITabView {
    /**
     * 被选中
     */
    void onSelected(int index, int totalCount);

    /**
     * 未被选中
     */
    void onDeselected(int index, int totalCount);

    /**
     * 离开
     *
     * @param leavePercent 离开的百分比, 0.0f - 1.0f
     * @param leftToRight  从左至右离开
     */
    void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

    /**
     * 进入
     *
     * @param enterPercent 进入的百分比, 0.0f - 1.0f
     * @param leftToRight  从左至右离开
     */
    void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);
}
