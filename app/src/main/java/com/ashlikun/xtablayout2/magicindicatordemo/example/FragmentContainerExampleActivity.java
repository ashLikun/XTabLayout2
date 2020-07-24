package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashlikun.xtablayout2.FragmentContainerHelper;
import com.ashlikun.xtablayout2.XTabLayout;
import com.ashlikun.xtablayout2.XTabUtils;
import com.ashlikun.xtablayout2.tab.common.TabCommon;
import com.ashlikun.xtablayout2.tab.common.adapter.TabCommonAdapter;
import com.ashlikun.xtablayout2.tab.common.ITabIndicator;
import com.ashlikun.xtablayout2.tab.common.ITabView;
import com.ashlikun.xtablayout2.tab.common.indicators.LineTabIndicator;
import com.ashlikun.xtablayout2.tab.common.tabview.ClipTabView;

import com.ashlikun.xtablayout2.magicindicatordemo.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentContainerExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"KITKAT", "NOUGAT", "DONUT"};
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container_example_layout);

        initFragments();
        initMagicIndicator1();

        mFragmentContainerHelper.handlePageSelected(1, false);
        switchPages(1);
    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = mFragments.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = mFragments.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = mFragments.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initFragments() {
        for (int i = 0; i < CHANNELS.length; i++) {
            TestFragment testFragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TestFragment.EXTRA_TEXT, CHANNELS[i]);
            testFragment.setArguments(bundle);
            mFragments.add(testFragment);
        }
    }

    private void initMagicIndicator1() {
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return CHANNELS.length;
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                ClipTabView clipPagerTitleView = new ClipTabView(context);
                clipPagerTitleView.setText(CHANNELS[index]);
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
                indicator.setLineHeight(lineHeight);
                indicator.setRoundRadius(lineHeight / 2);
                indicator.setYOffset(borderWidth);
                indicator.setColors(Color.parseColor("#bc2a2a"));
                return indicator;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mFragmentContainerHelper.handlePageSelected(index);
                switchPages(index);
            }
        });
        magicIndicator.setTab(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
    }
}
