package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.XTabLayout;
import com.ashlikun.xtablayout2.XTabLayoutMediator;
import com.ashlikun.xtablayout2.XTabUtils;
import com.ashlikun.xtablayout2.magicindicatordemo.R;
import com.ashlikun.xtablayout2.magicindicatordemo.xt.titles.ColorFlipPagerTitleView;
import com.ashlikun.xtablayout2.magicindicatordemo.xt.titles.ScaleTransitionPagerTitleView;
import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.TabCommon;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonViewPagerAdapter;
import com.ashlikun.xtablayout2.tab.common.indicators.BezierTabIndicator;
import com.ashlikun.xtablayout2.tab.common.indicators.LineTabIndicator;
import com.ashlikun.xtablayout2.tab.common.indicators.TriangularTabIndicator;
import com.ashlikun.xtablayout2.tab.common.indicators.WrapTabIndicator;
import com.ashlikun.xtablayout2.tab.common.tabview.ClipTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.SimpleTabView;

import java.util.Arrays;
import java.util.List;

public class ScrollableTabExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_indicator_example_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1();
        initMagicIndicator2();
        initMagicIndicator3();
        initMagicIndicator4();
        initMagicIndicator5();
        initMagicIndicator6();
        initMagicIndicator7();
        initMagicIndicator8();
        initMagicIndicator9();
    }

    private void initMagicIndicator1() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setSkimOver(true);
        int padding = XTabUtils.getScreenWidth(this) / 2;
        commonNavigator.setRightPadding(padding);
        commonNavigator.setLeftPadding(padding);
        commonNavigator.setAdapter(new TabCommonAdapter() {

            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                ClipTabView clipPagerTitleView = new ClipTabView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                return clipPagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                return null;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator2);
        magicIndicator.setBackgroundColor(Color.parseColor("#00c853"));
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new SimpleTabView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setTextSize(12);
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                indicator.setMode(LineTabIndicator.MODE_EXACTLY);
                indicator.setYOffset(XTabUtils.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator3() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator3);
        magicIndicator.setBackgroundColor(Color.WHITE);
        TabCommon tabCommon = XTabLayoutMediator.setupViewPager(magicIndicator, mViewPager, new TabCommonViewPagerAdapter());
        TabCommonViewPagerAdapter adapter = tabCommon.getAdapter();
        ((LineTabIndicator) adapter.getTabIndicator()).setMode(LineTabIndicator.MODE_WRAP_CONTENT);
    }

    private void initMagicIndicator4() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator4);
        magicIndicator.setBackgroundColor(Color.parseColor("#455a64"));

        TabCommon tabCommon = XTabLayoutMediator.setupViewPager(magicIndicator, mViewPager, new TabCommonViewPagerAdapter());
        TabCommonViewPagerAdapter adapter = tabCommon.getAdapter();
        adapter.setNormalColor(Color.parseColor("#88ffffff"))
                .setSelectedColor(Color.WHITE)
                .setColors(Color.parseColor("#40c4ff"));
    }

    private void initMagicIndicator5() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator5);
        magicIndicator.setBackgroundColor(Color.WHITE);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setScrollPivotX(0.8f);
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
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator6() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator6);
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
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                BezierTabIndicator indicator = new BezierTabIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator7() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator7);
        magicIndicator.setBackgroundColor(Color.parseColor("#fafafa"));
        TabCommon commonNavigator7 = new TabCommon(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#00c853"));
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                indicator.setMode(LineTabIndicator.MODE_EXACTLY)
                        .setLineHeight(XTabUtils.dip2px(context, 6))
                        .setLineWidth(XTabUtils.dip2px(context, 10))
                        .setRoundRadius(XTabUtils.dip2px(context, 3))
                        .setStartInterpolator(new AccelerateInterpolator())
                        .setEndInterpolator(new DecelerateInterpolator(2.0f))
                        .setColors(Color.parseColor("#00c853"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator7);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator8() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator8);
        magicIndicator.setBackgroundColor(Color.WHITE);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new SimpleTabView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                WrapTabIndicator indicator = new WrapTabIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator9() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator9);
        magicIndicator.setBackgroundColor(Color.WHITE);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setScrollPivotX(0.15f);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                SimpleTabView simplePagerTitleView = new SimpleTabView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                return simplePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                TriangularTabIndicator indicator = new TriangularTabIndicator(context);
                indicator.setLineColor(Color.parseColor("#e94220"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }
}
