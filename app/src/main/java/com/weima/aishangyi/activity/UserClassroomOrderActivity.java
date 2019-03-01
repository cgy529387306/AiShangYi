package com.weima.aishangyi.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;

import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.fragment.ClassroomOrderFragment;
import com.weima.aishangyi.fragment.TalentCircleFragment;
import com.weima.aishangyi.tabstrip.PagerSlidingTabStrip;

/**
 * 课程/课室订单
 */
public class UserClassroomOrderActivity extends BaseActivity {
    private PagerSlidingTabStrip tabstrip;
    private ViewPager viewpager;
    private int status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_trip_viewpager);
        setCustomTitle("课程/课室订单");
        initUI();
    }


    private void initUI() {
        status = getIntent().getIntExtra("status",0);
        tabstrip = (PagerSlidingTabStrip) findViewById(R.id.tabstrip);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabstrip.setViewPager(viewpager);
        viewpager.setCurrentItem(status); //初始化显示0
        tabstrip.setTextColor(getResources().getColor(R.color.text_color));//未选中字体的颜色
        tabstrip.setSelectedTextColor(getResources().getColor(R.color.base_orange));//选中选项中字体的颜色
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);//获取屏幕宽度
        int width = dm.widthPixels;//宽度
        tabstrip.setTextSize(width / 28);//字体的大小
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        private final String[] titles = {"全部", "待确认", "待授课","待评价"};


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
            if (position == 1) {
                return ClassroomOrderFragment.newInstance(0);
            } else if (position == 2) {
                return ClassroomOrderFragment.newInstance(1);
            } else if (position == 3) {
                return ClassroomOrderFragment.newInstance(2);
            }else{
                return ClassroomOrderFragment.newInstance(-1);
            }
        }

    }

}
