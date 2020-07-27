package com.ashlikun.xtablayout2.tab.common.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;

import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.TabCommon;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:17
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：{@link TabCommon}适配器，通过它可轻松切换不同的指示器样式
 */

public abstract class TabCommonAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    private OnTabItemClickListener onTabItemClickListener = null;
    private OnCreateTabViewListener onCreateTabViewListener = null;

    public abstract int getCount();

    public abstract ITabView createTabView(Context context, int index);

    public abstract ITabIndicator createIndicator(Context context);

    public float getTitleWeight(Context context, int index) {
        return 1;
    }

    public final void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public final void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public final void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public final void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public ITabView bindTabView(Context context, final int index) {
        final ITabView view = onCreateTabViewListener == null ? createTabView(context, index) : onCreateTabViewListener.createTabView(context, index);
        if (view instanceof View) {
            ((View) view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(view, index);
                }
            });
        }
        return view;
    }

    public void onItemClick(ITabView view, int index) {
        if (onTabItemClickListener != null) {
            onTabItemClickListener.onItemClick(view, index);
        }
    }

    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        this.onTabItemClickListener = onTabItemClickListener;
    }

    public void setOnCreateTabViewListener(OnCreateTabViewListener onCreateTabViewListener) {
        this.onCreateTabViewListener = onCreateTabViewListener;
    }

    public OnCreateTabViewListener getOnCreateTabViewListener() {
        return onCreateTabViewListener;
    }

    public interface OnTabItemClickListener {
        public void onItemClick(ITabView view, int index);
    }

    public interface OnCreateTabViewListener {
        public ITabView createTabView(Context context, int index);
    }
}
