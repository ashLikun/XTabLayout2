package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.XTabLayout;
import com.ashlikun.xtablayout2.XTabLayoutMediator;
import com.ashlikun.xtablayout2.tab.circle.TabCircle;
import com.ashlikun.xtablayout2.magicindicatordemo.xt.navigator.ScaleCircleNavigator;

import com.ashlikun.xtablayout2.magicindicatordemo.R;

import java.util.Arrays;
import java.util.List;

public class CustomNavigatorExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_navigator_example_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        initMagicIndicator1();
        initMagicIndicator2();
        initMagicIndicator3();
    }

    private void initMagicIndicator1() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator1);
        TabCircle circleNavigator = new TabCircle(this);
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(new TabCircle.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(circleNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator2() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator2);
        TabCircle circleNavigator = new TabCircle(this);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(CHANNELS.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(new TabCircle.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(circleNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }

    private void initMagicIndicator3() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator3);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(CHANNELS.length);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setTab(scaleCircleNavigator);
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }
}
