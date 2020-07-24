package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.FragmentContainerHelper;
import com.ashlikun.xtablayout2.XTabLayout;
import com.ashlikun.xtablayout2.XTabLayoutMediator;
import com.ashlikun.xtablayout2.XTabUtils;
import com.ashlikun.xtablayout2.magicindicatordemo.R;
import com.ashlikun.xtablayout2.magicindicatordemo.xt.titles.ScaleTransitionPagerTitleView;
import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.TabCommon;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;
import com.ashlikun.xtablayout2.tab.common.indicators.LineTabIndicator;
import com.ashlikun.xtablayout2.tab.common.tabview.ClipTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.ColorTransitionTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.SimpleTabView;

import java.util.Arrays;
import java.util.List;

;

public class FixedTabExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"KITKAT", "NOUGAT", "DONUT"};
    //    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private com.ashlikun.xtablayout2.magicindicatordemo.example.ExamplePagerAdapter mExamplePagerAdapter = new com.ashlikun.xtablayout2.magicindicatordemo.example.ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_tab_example_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1();
        initMagicIndicator2();
        initMagicIndicator3();
        initMagicIndicator4();
    }

    private void initMagicIndicator1() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator1);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new ColorTransitionTabView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(XTabUtils.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator2);
        magicIndicator.setBackgroundColor(Color.WHITE);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#616161"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f57c00"));
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
                indicator.setYOffset(XTabUtils.dip2px(context, 39));
                indicator.setLineHeight(XTabUtils.dip2px(context, 1));
                indicator.setColors(Color.parseColor("#f57c00"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                if (index == 0) {
                    return 2.0f;
                } else if (index == 1) {
                    return 1.2f;
                } else {
                    return 1.0f;
                }
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator3() {
        XTabLayout magicIndicator = findViewById(R.id.magic_indicator3);
        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                ClipTabView clipPagerTitleView = new ClipTabView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#e94220"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                return clipPagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                float navigatorHeight = context.getResources().getDimension(R.dimen.common_navigator_height);
                float borderWidth = XTabUtils.dip2px(context, 1);
                float lineHeight = navigatorHeight - 2 * borderWidth;
                indicator.setLineHeight((int) lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#bc2a2a"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        commonNavigator.setFixedTab(false);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator4() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator4);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new ColorTransitionTabView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setText(mDataList.get(index));
                return simplePagerTitleView;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator linePagerIndicator = new LineTabIndicator(context);
                linePagerIndicator.setMode(LineTabIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        magicIndicator.setTab(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return XTabUtils.dip2px(FixedTabExampleActivity.this, 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }
}
