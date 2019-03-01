package com.weima.aishangyi.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;

import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.fragment.TalentCircleFragment;
import com.weima.aishangyi.tabstrip.PagerSlidingTabStrip;
import com.weima.aishangyi.utils.NavigationHelper;


/**
 * 才艺圈
 */
public class TalentCircleActivity extends BaseActivity {
    private PagerSlidingTabStrip tabstrip;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_trip_viewpager);
        setCustomTitle("才艺圈");
        setImageRightButton(R.drawable.bg_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.startActivity(TalentCircleActivity.this, TalentPostActivity.class, null, false);
            }
        });
        initUI();
    }

    private void initUI() {
        tabstrip = (PagerSlidingTabStrip) findViewById(R.id.tabstrip);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabstrip.setViewPager(viewpager);
        viewpager.setCurrentItem(0); //初始化显示0
        tabstrip.setTextColor(getResources().getColor(R.color.text_color));//未选中字体的颜色
        tabstrip.setSelectedTextColor(getResources().getColor(R.color.base_orange));//选中选项中字体的颜色
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);//获取屏幕宽度
        int width = dm.widthPixels;//宽度
        tabstrip.setTextSize(width / 28);//字体的大小
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        private final String[] titles = {"广场", "关注", "我的"};


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return TalentCircleFragment.newInstance(position + 1);
        }

    }

}
