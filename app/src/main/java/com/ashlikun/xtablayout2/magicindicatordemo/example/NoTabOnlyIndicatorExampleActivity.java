package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.XTabLayout;
import com.ashlikun.xtablayout2.XTabLayoutMediator;
import com.ashlikun.xtablayout2.XTabUtils;
import com.ashlikun.xtablayout2.tab.common.TabCommon;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;
import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.indicators.LineTabIndicator;
import com.ashlikun.xtablayout2.tab.common.indicators.TriangularTabIndicator;
import com.ashlikun.xtablayout2.tab.common.tabview.DummyTabView;
import com.ashlikun.xtablayout2.magicindicatordemo.R;

import java.util.Arrays;
import java.util.List;

public class NoTabOnlyIndicatorExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "NOUGAT", "DONUT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_tab_only_indicator_example_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1();
        initMagicIndicator2();
    }

    private void initMagicIndicator1() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundColor(Color.LTGRAY);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                return new DummyTabView(context);
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                float lineHeight = context.getResources().getDimension(R.dimen.small_navigator_height);
                indicator.setLineHeight((int) lineHeight);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator2);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                return new DummyTabView(context);
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                TriangularTabIndicator indicator = new TriangularTabIndicator(context);
                indicator.setReverse(true);
                float smallNavigatorHeight = context.getResources().getDimension(R.dimen.small_navigator_height);
                indicator.setLineHeight(XTabUtils.dip2px(context, 2));
                indicator.setTriangleHeight((int) smallNavigatorHeight);
                indicator.setLineColor(Color.parseColor("#e94220"));
                return indicator;
            }
        });
        magicIndicator.setTab(commonNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }
}
