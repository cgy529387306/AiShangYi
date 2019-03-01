package com.weima.aishangyi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.utils.ProjectHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/22.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    private Date date;
    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context,List<Fragment> list) {
        super(fm);
        this.context = context;
        this.fragmentList = list;
    }

    public String getDate(int position){
        if (Helper.isNotEmpty(date) && Helper.isNotEmpty(dateList)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            return year+"-"+dateList.get(position);
        }else{
            return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


    public View getTabView(int position,Date date){
        this.date = date;
        dateList = ProjectHelper.getAllweekDays(date);
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_time, null);
        TextView txv_week= (TextView) view.findViewById(R.id.txv_week);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, +position);
        txv_week.setText(ProjectHelper.getWeekStr(position));
        TextView txv_day= (TextView) view.findViewById(R.id.txv_day);
        txv_day.setText(dateList.get(position));
        return view;
    }
}
