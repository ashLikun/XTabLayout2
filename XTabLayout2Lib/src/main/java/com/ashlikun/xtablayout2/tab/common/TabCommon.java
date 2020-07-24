package com.ashlikun.xtablayout2.tab.common;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ashlikun.xtablayout2.R;
import com.ashlikun.xtablayout2.ScrollState;
import com.ashlikun.xtablayout2.TabHelper;
import com.ashlikun.xtablayout2.tab.ITab;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/15 13:11
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：通用的ViewPager指示器，包含PagerTitle和PagerIndicator
 */

public class TabCommon extends FrameLayout implements ITab, TabHelper.OnNavigatorScrollListener {
    private HorizontalScrollView mScrollView;
    private FrameLayout mContentView;
    private LinearLayout mTitleContainer;
    private LinearLayout mIndicatorContainer;
    private ITabIndicator mIndicator;

    private TabCommonAdapter mAdapter;
    private TabHelper mTabHelper;

    /**
     * 提供给外部的参数配置
     */
    /****************************************************/
    private boolean mEnablePivotScroll; // 启动中心点滚动
    private float mScrollPivotX = 0.5f; // 滚动中心点 0.0f - 1.0f
    private boolean mSmoothScroll = true;   // 是否平滑滚动，适用于 !mAdjustMode && !mFollowTouch
    private boolean mFollowTouch = true;    // 是否手指跟随滚动
    private int mRightPadding;
    private int mLeftPadding;
    private boolean mIndicatorOnTop;    // 指示器是否在title上层，默认为下层
    private boolean mSkimOver;  // 跨多页切换时，中间页是否显示 "掠过" 效果
    private boolean mReselectWhenLayout = true; // PositionData准备好时，是否重新选中当前页，为true可保证在极端情况下指示器状态正确
    //是否当宽度不够撑满的时候均分tab
    private boolean isFixedTab = false;
    /****************************************************/

    // 保存每个title的位置信息，为扩展indicator提供保障
    private List<PositionData> mPositionDataList = new ArrayList<PositionData>();

    private DataSetObserver mObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            mTabHelper.setTotalCount(mAdapter.getCount());    // 如果使用helper，应始终保证helper中的totalCount为最新
            init();
        }

        @Override
        public void onInvalidated() {
            // 没什么用，暂不做处理
        }
    };

    public TabCommon(Context context) {
        super(context);
        mTabHelper = new TabHelper();
        mTabHelper.setNavigatorScrollListener(this);
    }

    @Override
    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }


    public <T extends TabCommonAdapter> T getAdapter() {
        return (T) mAdapter;
    }

    public void setAdapter(TabCommonAdapter adapter) {
        if (mAdapter == adapter) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mObserver);
            mTabHelper.setTotalCount(mAdapter.getCount());
            if (mTitleContainer != null) {  // adapter改变时，应该重新init，但是第一次设置adapter不用，onAttachToMagicIndicator中有init
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mTabHelper.setTotalCount(0);
            init();
        }
    }

    private void init() {
        removeAllViews();

        View root = LayoutInflater.from(getContext()).inflate(R.layout.pager_navigator_layout, this);

        mScrollView = (HorizontalScrollView) root.findViewById(R.id.scroll_view);   // mAdjustMode为true时，mScrollView为null

        mContentView = (FrameLayout) root.findViewById(R.id.content_view);

        mTitleContainer = (LinearLayout) root.findViewById(R.id.title_container);
        mTitleContainer.setPadding(mLeftPadding, 0, mRightPadding, 0);

        mIndicatorContainer = (LinearLayout) root.findViewById(R.id.indicator_container);
        if (mIndicatorOnTop) {
            mIndicatorContainer.getParent().bringChildToFront(mIndicatorContainer);
        }

        initTitlesAndIndicator();
    }

    /**
     * 初始化title和indicator
     */
    private void initTitlesAndIndicator() {
        for (int i = 0, j = mTabHelper.getTotalCount(); i < j; i++) {
            ITabView v = mAdapter.bindTabView(getContext(), i);
            if (v instanceof View) {
                View view = (View) v;
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                mTitleContainer.addView(view, lp);
            }
        }
        if (mAdapter != null) {
            mIndicator = mAdapter.createIndicator(getContext());
            if (mIndicator instanceof View) {
                LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mIndicatorContainer.addView((View) mIndicator, lp);
            }
        }
    }

    private int getValidWidth() {
        if (getMeasuredWidth() == 0) {
            return 0;
        }
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTitleContainer.getMeasuredWidth() <= getValidWidth()) {
            //宽度不够盛满
            if (mTitleContainer.getChildCount() != 1) {
                //不是一条的时候内容居中
                ((LayoutParams) mContentView.getLayoutParams()).gravity = Gravity.CENTER;
            } else {
                //一条的时候 内容左边
                ((LayoutParams) mContentView.getLayoutParams()).gravity = Gravity.LEFT;
            }
            if (isFixedTab) {
                mTitleContainer.getLayoutParams().width = getValidWidth();
                for (int i = 0; i < mTitleContainer.getChildCount(); i++) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTitleContainer.getChildAt(i).getLayoutParams();
                    lp.width = 0;
                    lp.weight = 1;
                }
            } else {
                mTitleContainer.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                for (int i = 0; i < mTitleContainer.getChildCount(); i++) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTitleContainer.getChildAt(i).getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    lp.weight = 0;
                }
            }
        } else {
            ((LayoutParams) mContentView.getLayoutParams()).gravity = Gravity.LEFT;
            mTitleContainer.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            for (int i = 0; i < mTitleContainer.getChildCount(); i++) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTitleContainer.getChildAt(i).getLayoutParams();
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.weight = 0;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter != null) {
            preparePositionData();
            if (mIndicator != null) {
                mIndicator.onPositionDataProvide(mPositionDataList);
            }
            if (mReselectWhenLayout && mTabHelper.getScrollState() == ScrollState.SCROLL_STATE_IDLE) {
                onPageSelected(mTabHelper.getCurrentIndex());
                onPageScrolled(mTabHelper.getCurrentIndex(), 0.0f, 0);
            }
        }

    }

    /**
     * 获取title的位置信息，为打造不同的指示器、各种效果提供可能
     */
    private void preparePositionData() {
        mPositionDataList.clear();
        for (int i = 0, j = mTabHelper.getTotalCount(); i < j; i++) {
            PositionData data = new PositionData();
            View v = mTitleContainer.getChildAt(i);
            if (v != null) {
                data.mLeft = v.getLeft();
                data.mTop = v.getTop();
                data.mRight = v.getRight();
                data.mBottom = v.getBottom();
                if (v instanceof IMeasurablePagerTitleView) {
                    IMeasurablePagerTitleView view = (IMeasurablePagerTitleView) v;
                    data.mContentLeft = view.getContentLeft();
                    data.mContentTop = view.getContentTop();
                    data.mContentRight = view.getContentRight();
                    data.mContentBottom = view.getContentBottom();
                } else {
                    data.mContentLeft = data.mLeft;
                    data.mContentTop = data.mTop;
                    data.mContentRight = data.mRight;
                    data.mContentBottom = data.mBottom;
                }
            }
            mPositionDataList.add(data);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mAdapter != null) {

            mTabHelper.onPageScrolled(position, positionOffset, positionOffsetPixels);
            if (mIndicator != null) {
                mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // 手指跟随滚动
            if (mScrollView != null && mPositionDataList.size() > 0 && position >= 0 && position < mPositionDataList.size()) {
                if (mFollowTouch) {
                    int currentPosition = Math.min(mPositionDataList.size() - 1, position);
                    int nextPosition = Math.min(mPositionDataList.size() - 1, position + 1);
                    PositionData current = mPositionDataList.get(currentPosition);
                    PositionData next = mPositionDataList.get(nextPosition);
                    float scrollTo = current.horizontalCenter() - mScrollView.getWidth() * mScrollPivotX;
                    float nextScrollTo = next.horizontalCenter() - mScrollView.getWidth() * mScrollPivotX;
                    mScrollView.scrollTo((int) (scrollTo + (nextScrollTo - scrollTo) * positionOffset), 0);
                } else if (!mEnablePivotScroll) {
                    // TODO 实现待选中项完全显示出来
                }
            }
        }
    }

    public float getScrollPivotX() {
        return mScrollPivotX;
    }

    public void setScrollPivotX(float scrollPivotX) {
        mScrollPivotX = scrollPivotX;
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter != null) {
            mTabHelper.onPageSelected(position);
            if (mIndicator != null) {
                mIndicator.onPageSelected(position);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mAdapter != null) {
            mTabHelper.onPageScrollStateChanged(state);
            if (mIndicator != null) {
                mIndicator.onPageScrollStateChanged(state);
            }
        }
    }

    @Override
    public void onAttachToMagicIndicator() {
        init(); // 将初始化延迟到这里
    }

    @Override
    public void onDetachFromMagicIndicator() {
    }

    public ITabIndicator getPagerIndicator() {
        return mIndicator;
    }

    public boolean isEnablePivotScroll() {
        return mEnablePivotScroll;
    }

    public void setEnablePivotScroll(boolean is) {
        mEnablePivotScroll = is;
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (mTitleContainer == null) {
            return;
        }
        View v = mTitleContainer.getChildAt(index);
        if (v instanceof ITabView) {
            ((ITabView) v).onEnter(index, totalCount, enterPercent, leftToRight);
        }
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (mTitleContainer == null) {
            return;
        }
        View v = mTitleContainer.getChildAt(index);
        if (v instanceof ITabView) {
            ((ITabView) v).onLeave(index, totalCount, leavePercent, leftToRight);
        }
    }

    public boolean isSmoothScroll() {
        return mSmoothScroll;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        mSmoothScroll = smoothScroll;
    }

    public boolean isFollowTouch() {
        return mFollowTouch;
    }

    public void setFollowTouch(boolean followTouch) {
        mFollowTouch = followTouch;
    }

    public boolean isSkimOver() {
        return mSkimOver;
    }

    public void setSkimOver(boolean skimOver) {
        mSkimOver = skimOver;
        mTabHelper.setSkimOver(skimOver);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        if (mTitleContainer == null) {
            return;
        }
        View v = mTitleContainer.getChildAt(index);
        if (v instanceof ITabView) {
            ((ITabView) v).onSelected(index, totalCount);
        }
        if (!mFollowTouch && mScrollView != null && mPositionDataList.size() > 0) {
            int currentIndex = Math.min(mPositionDataList.size() - 1, index);
            PositionData current = mPositionDataList.get(currentIndex);
            if (mEnablePivotScroll) {
                float scrollTo = current.horizontalCenter() - mScrollView.getWidth() * mScrollPivotX;
                if (mSmoothScroll) {
                    mScrollView.smoothScrollTo((int) (scrollTo), 0);
                } else {
                    mScrollView.scrollTo((int) (scrollTo), 0);
                }
            } else {
                // 如果当前项被部分遮挡，则滚动显示完全
                if (mScrollView.getScrollX() > current.mLeft) {
                    if (mSmoothScroll) {
                        mScrollView.smoothScrollTo(current.mLeft, 0);
                    } else {
                        mScrollView.scrollTo(current.mLeft, 0);
                    }
                } else if (mScrollView.getScrollX() + getWidth() < current.mRight) {
                    if (mSmoothScroll) {
                        mScrollView.smoothScrollTo(current.mRight - getWidth(), 0);
                    } else {
                        mScrollView.scrollTo(current.mRight - getWidth(), 0);
                    }
                }
            }
        }
    }

    @Override
    public int getCurrentItem() {
        if (mAdapter != null) {
            return mTabHelper.getCurrentIndex();
        }
        return 0;
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        if (mTitleContainer == null) {
            return;
        }
        View v = mTitleContainer.getChildAt(index);
        if (v instanceof ITabView) {
            ((ITabView) v).onDeselected(index, totalCount);
        }
    }

    public ITabView getPagerTitleView(int index) {
        if (mTitleContainer == null) {
            return null;
        }
        return (ITabView) mTitleContainer.getChildAt(index);
    }

    public LinearLayout getTitleContainer() {
        return mTitleContainer;
    }

    public int getRightPadding() {
        return mRightPadding;
    }

    public void setRightPadding(int rightPadding) {
        mRightPadding = rightPadding;
    }

    public int getLeftPadding() {
        return mLeftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        mLeftPadding = leftPadding;
    }

    public boolean isIndicatorOnTop() {
        return mIndicatorOnTop;
    }

    public void setIndicatorOnTop(boolean indicatorOnTop) {
        mIndicatorOnTop = indicatorOnTop;
    }

    public boolean isReselectWhenLayout() {
        return mReselectWhenLayout;
    }

    public void setReselectWhenLayout(boolean reselectWhenLayout) {
        mReselectWhenLayout = reselectWhenLayout;
    }

    public FrameLayout getContentView() {
        return mContentView;
    }

    public FrameLayout getScrollView() {
        return mScrollView;
    }

    public FrameLayout.LayoutParams getScrollViewLayoutParams() {
        return (LayoutParams) mScrollView.getLayoutParams();
    }

    public FrameLayout.LayoutParams getContentViewLayoutParams() {
        return (LayoutParams) mContentView.getLayoutParams();
    }

    public boolean isFixedTab() {
        return isFixedTab;
    }

    public TabCommon setFixedTab(boolean fixedTab) {
        isFixedTab = fixedTab;
        return this;
    }
}
