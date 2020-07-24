package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashlikun.xtablayout2.XTabLayout;
import com.ashlikun.xtablayout2.XTabLayoutMediator;
import com.ashlikun.xtablayout2.tab.common.TabCommon;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;
import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.tabview.ClipTabView;

import com.ashlikun.xtablayout2.magicindicatordemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DynamicTabExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private List<String> mDataList = new ArrayList<String>(Arrays.asList(CHANNELS));
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;
    private XTabLayout mMagicIndicator;
    private TabCommon mCommonNavigator;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_tab_example_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mExamplePagerAdapter);

        mMagicIndicator = (XTabLayout) findViewById(R.id.magic_indicator1);
        mMagicIndicator.setBackgroundColor(Color.parseColor("#d43d3d"));
        mCommonNavigator = new TabCommon(this);
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(new TabCommonAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
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
        mMagicIndicator.setTab(mCommonNavigator);
        XTabLayoutMediator.bind(mMagicIndicator, mViewPager);

        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void randomPage(View view) {
        mDataList.clear();
        int total = new Random().nextInt(CHANNELS.length);
        for (int i = 0; i <= total; i++) {
            mDataList.add(CHANNELS[i]);
        }

        mCommonNavigator.notifyDataSetChanged();    // must call firstly
        mExamplePagerAdapter.notifyDataSetChanged();

        mToast.setText("" + mDataList.size() + " page");
        mToast.show();
    }
}
