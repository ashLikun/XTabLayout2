package com.ashlikun.xtablayout2.magicindicatordemo.example;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.ashlikun.xtablayout2.tab.common.tabview.ClipTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.ColorTransitionTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.SimpleTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.badge.BadgeAnchor;
import com.ashlikun.xtablayout2.tab.common.tabview.badge.BadgeTabView;
import com.ashlikun.xtablayout2.tab.common.tabview.badge.BadgeRule;
import com.ashlikun.xtablayout2.magicindicatordemo.R;
import com.ashlikun.xtablayout2.magicindicatordemo.xt.titles.ScaleTransitionPagerTitleView;

import java.util.Arrays;
import java.util.List;

public class BadgeTabExampleActivity extends AppCompatActivity {
    private static final String[] CHANNELS = new String[]{"KITKAT", "NOUGAT", "DONUT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_tab_example_layout);

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
                final BadgeTabView badgePagerTitleView = new BadgeTabView(context);

                SimpleTabView simplePagerTitleView = new ColorTransitionTabView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                // setup badge
                if (index != 2) {
                    TextView badgeTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.simple_count_badge_layout, null);
                    badgeTextView.setText("" + (index + 1));
                    badgePagerTitleView.setBadgeView(badgeTextView);
                } else {
                    ImageView badgeImageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.simple_red_dot_badge_layout, null);
                    badgePagerTitleView.setBadgeView(badgeImageView);
                }

                // set badge position
                if (index == 0) {
                    badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_LEFT, -XTabUtils.dip2px(context, 6)));
                    badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
                } else if (index == 1) {
                    badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -XTabUtils.dip2px(context, 6)));
                    badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
                } else if (index == 2) {
                    badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CENTER_X, -XTabUtils.dip2px(context, 3)));
                    badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_BOTTOM, XTabUtils.dip2px(context, 2)));
                }

                // don't cancel badge when tab selected
                badgePagerTitleView.setAutoCancelBadge(false);

                return badgePagerTitleView;
            }

            @Override
            public void onItemClick(ITabView view, int index) {
                super.onItemClick(view, index);
                mViewPager.setCurrentItem(index);
                ((BadgeTabView) view).setBadgeView(null); // cancel badge when click tab
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator indicator = new LineTabIndicator(context);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
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
                BadgeTabView badgePagerTitleView = new BadgeTabView(context);

                SimpleTabView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#616161"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f57c00"));
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                // setup badge
                if (index == 1) {
                    ImageView badgeImageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.simple_red_dot_badge_layout, null);
                    badgePagerTitleView.setBadgeView(badgeImageView);
                    badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CENTER_X, -XTabUtils.dip2px(context, 3)));
                    badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_BOTTOM, XTabUtils.dip2px(context, 2)));
                }

                // cancel badge when click tab, default true
                badgePagerTitleView.setAutoCancelBadge(true);

                return badgePagerTitleView;
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
        XTabLayout magicIndicator = (XTabLayout) findViewById(R.id.magic_indicator3);
        magicIndicator.setBackgroundResource(R.drawable.round_indicator_bg);
        TabCommon commonNavigator = new TabCommon(this);
        commonNavigator.setAdapter(new TabCommonAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public ITabView createTabView(Context context, final int index) {
                BadgeTabView badgePagerTitleView = new BadgeTabView(context);

                ClipTabView clipPagerTitleView = new ClipTabView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#e94220"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                badgePagerTitleView.setInnerPagerTitleView(clipPagerTitleView);

                return badgePagerTitleView;
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
                BadgeTabView badgePagerTitleView = new BadgeTabView(context);

                SimpleTabView simplePagerTitleView = new ColorTransitionTabView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setText(mDataList.get(index));
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public ITabIndicator createIndicator(Context context) {
                LineTabIndicator linePagerIndicator = new LineTabIndicator(context);
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
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
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return XTabUtils.dip2px(BadgeTabExampleActivity.this, 15);
            }
        });
        XTabLayoutMediator.bind(magicIndicator, mViewPager);
    }
}
