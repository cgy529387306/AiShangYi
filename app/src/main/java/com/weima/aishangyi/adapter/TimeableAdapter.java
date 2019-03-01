package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class TimeableAdapter extends BaseAdapter{
    private Activity activity;
    private List<String> selectTime = new ArrayList<>();
    public TimeableAdapter(Activity act) {
        this.activity = act;
    }

    public List<String> getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(List<String> selectTime) {
        if (Helper.isNotEmpty(selectTime)){
            this.selectTime = selectTime;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return 32;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (true) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_timeable, null);
            holder.txv_time = (TextView) convertView.findViewById(R.id.txv_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (position){
            case 0:
                holder.txv_time.setText("");
                break;
            case 1:
                holder.txv_time.setText("周一");
                break;
            case 2:
                holder.txv_time.setText("周二");
                break;
            case 3:
                holder.txv_time.setText("周三");
                break;
            case 4:
                holder.txv_time.setText("周四");
                break;
            case 5:
                holder.txv_time.setText("周五");
                break;
            case 6:
                holder.txv_time.setText("周六");
                break;
            case 7:
                holder.txv_time.setText("周日");
                break;
            case 8:
                holder.txv_time.setText("上午");
                break;
            case 16:
                holder.txv_time.setText("下午");
                break;
            case 24:
                holder.txv_time.setText("晚上");
                break;
            default:
                holder.txv_time.setText("");
                break;
        }
        final ViewHolder finalHolder = holder;
        if (position != 0 && position != 1 && position != 2 && position != 3 && position != 4 && position != 5 && position != 6
                && position != 7 && position != 8 && position != 16 && position != 24) {
            if (Helper.isNotEmpty(selectTime)){
                int xp = 8 + (position - 1) / 8;
                if (selectTime.contains(String.valueOf(position - xp))){
                    finalHolder.txv_time.setBackgroundResource(R.drawable.bg_timeable_smile);
                    finalHolder.txv_time.setSelected(true);
                }else{
                    finalHolder.txv_time.setBackgroundResource(R.drawable.bg_white);
                    finalHolder.txv_time.setSelected(false);
                }
            }
        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (position != 0 && position != 1 && position != 2 && position != 3 && position != 4 && position != 5 && position != 6
//                        && position != 7 && position != 8 && position != 16 && position != 24) {
//                    int xp = 8 + (position - 1) / 8;
//                    if (finalHolder.txv_time.isSelected()) {
//                        finalHolder.txv_time.setBackgroundResource(R.drawable.bg_white);
//                        finalHolder.txv_time.setSelected(false);
//                        if (selectTime.contains(String.valueOf(position - xp))) {
//                            selectTime.remove(String.valueOf(position - xp));
//                        }
//
//                    } else {
//                        finalHolder.txv_time.setBackgroundResource(R.drawable.bg_timeable_smile);
//                        finalHolder.txv_time.setSelected(true);
//                        if (!selectTime.contains(String.valueOf(position - xp))) {
//                            selectTime.add(String.valueOf(position - xp));
//                        }
//                    }
//                }
//            }
//        });
        return convertView;
    }

    static class ViewHolder{
        TextView txv_time;
    }

}
