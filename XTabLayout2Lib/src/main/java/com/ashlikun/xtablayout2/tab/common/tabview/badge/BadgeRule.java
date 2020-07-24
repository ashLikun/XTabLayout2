package com.ashlikun.xtablayout2.tab.common.tabview.badge;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:20
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：角标的定位规则
 */

public class BadgeRule {
    private BadgeAnchor mAnchor;
    private int mOffset;

    public BadgeRule(BadgeAnchor anchor, int offset) {
        mAnchor = anchor;
        mOffset = offset;
    }

    public BadgeAnchor getAnchor() {
        return mAnchor;
    }

    public void setAnchor(BadgeAnchor anchor) {
        mAnchor = anchor;
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }
}
