package com.weima.aishangyi.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;

import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.fragment.CollectFragment;
import com.weima.aishangyi.fragment.QuestionFragment;
import com.weima.aishangyi.tabstrip.PagerSlidingTabStrip;

/**
 * 我的收藏
 */
public class UserCollectActivity extends BaseActivity {
    private PagerSlidingTabStrip tabstrip;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_trip_viewpager);
        setCustomTitle("我的收藏");
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
        private final String[] titles = {"老师", "课程", "资讯", "活动课堂"};


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
            if (position == 0){
                return CollectFragment.newInstance(3);
            }else if (position == 1){
                return CollectFragment.newInstance(2);
            }else if (position == 2){
                return CollectFragment.newInstance(1);
            }else {
                return CollectFragment.newInstance(4);
            }
        }

    }

}
