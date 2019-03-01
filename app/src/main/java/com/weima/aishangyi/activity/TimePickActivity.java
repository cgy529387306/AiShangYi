package com.weima.aishangyi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.mb.android.utils.Helper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.SimpleFragmentPagerAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.entity.MemberBean;
import com.weima.aishangyi.entity.TimeBean;
import com.weima.aishangyi.fragment.TimeFragment;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class TimePickActivity extends BaseActivity implements View.OnClickListener{
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Dialog dateDialog;
    private String dateStr;
    private List<TimeBean> timeList;
    private List<Fragment> fragmentList = new ArrayList<>();
    public List<TimeBean> getTimeList(){
        return timeList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("可预约时间表");
        setRightButton("更多时间", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        setContentView(R.layout.activity_pick_time);
        initUI();
    }

    private void initUI() {
        for (int i=0; i<7; i++){
            Fragment fragment = TimeFragment.newInstance(i);
            fragmentList.add(fragment);
        }
        timeList = (List<TimeBean>) getIntent().getSerializableExtra("lesson_time");
        dateStr = Helper.date2String(new Date(),"yyyy-MM-dd");
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this,fragmentList);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i,new Date()));
        }
        viewPager.setCurrentItem(ProjectHelper.getWeekNum(new Date()));
        findView(R.id.btn_comfirm).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_comfirm){
            TimeFragment timeFragment = (TimeFragment) fragmentList.get(viewPager.getCurrentItem());
            TimeBean timeBean = timeFragment.getTimeSelect();
            if (timeBean != null){
                String formatStr = "yyyy-MM-dd HH:mm";
                String date = pagerAdapter.getDate(viewPager.getCurrentItem());
                Date startTime = Helper.string2Date(date +" " + timeBean.getEnd_time(),formatStr);
                Date now = Helper.string2Date(Helper.date2String(new Date(),formatStr),formatStr);
                if (now.compareTo(startTime) > 0){
                    ToastHelper.showToast("该时间段已过时，请另选可预约时间段哦～");
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("timeBean",timeBean);
                    intent.putExtra("dateStr",pagerAdapter.getDate(viewPager.getCurrentItem()));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        }
    }

    private void showDateDialog() {
        try {
            Date date = null;
            if (Helper.isNotEmpty(dateStr)) {
                date = Helper.string2Date(dateStr, "yyyy-MM-dd");
            } else {
                date = new Date();
            }
            List<Integer> dateList = DateUtil.getDateForString(Helper.date2String(date,"yyyy-MM-dd"));
            DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
            builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
                @Override
                public void onDateSelected(int[] dates) {
                    if (Helper.isNotEmpty(MemberBean.getInstance()) && Helper.isNotEmpty(MemberBean.getInstance().getDate_line())){
                        String dateStr1 = dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                                + (dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                        dateStr = dateStr1;
                        Date date1 = Helper.string2Date(dateStr1,"yyyy-MM-dd");
                        Date dateLine = Helper.string2Date(MemberBean.getInstance().getDate_line(),"yyyy-MM-dd");
                        if (date1.compareTo(dateLine)>0){
                            ToastHelper.showToast("超过会员有效期"+MemberBean.getInstance().getDate_line());
                        }else {
                            tabLayout.removeAllTabs();
                            viewPager.setAdapter(pagerAdapter);
                            tabLayout.setupWithViewPager(viewPager);
                            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                                TabLayout.Tab tab = tabLayout.getTabAt(i);
                                tab.setCustomView(pagerAdapter.getTabView(i,date1));
                            }
                            viewPager.setCurrentItem(ProjectHelper.getWeekNum(date1));
                            dateDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onCancel() {
                    dateDialog.dismiss();
                }
            });
            builder.setSelectYear(dateList.get(0) - 1);
            builder.setSelectMonth(dateList.get(1) - 1);
            builder.setSelectDay(dateList.get(2) - 1);
            dateDialog = builder.create();
            dateDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
