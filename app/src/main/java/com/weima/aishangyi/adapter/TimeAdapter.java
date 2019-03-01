package com.weima.aishangyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.entity.MemberBean;
import com.weima.aishangyi.entity.TimeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class TimeAdapter extends BaseAdapter {
    private Context activity;
    private List<TimeBean> dataList = new ArrayList<>();
    private int tempIndex = 0;

    public TimeAdapter(Context context) {
        this.activity = context;
    }

    public void addMore(List<TimeBean> list){
        if (Helper.isNotEmpty(list)){
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public int getSelectIndex(){
        return  tempIndex;
    }

    public void clear(){
        dataList.clear();
        notifyDataSetChanged();
    }

    private void setTempIndex(int index){
        this.tempIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public TimeBean getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_time, null);
            holder.imv_select = (ImageView) convertView.findViewById(R.id.imv_select);
            holder.txv_beginTime = (TextView) convertView.findViewById(R.id.txv_beginTime);
            holder.txv_endTime = (TextView) convertView.findViewById(R.id.txv_endTime);
            holder.txv_status = (TextView) convertView.findViewById(R.id.txv_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TimeBean entity = dataList.get(position);
        holder.txv_beginTime.setText(entity.getStart_time());
        holder.txv_endTime.setText(entity.getEnd_time());
        holder.txv_status.setText(entity.getNumber()>0?"可预约":"不可预约");
        if (entity.getNumber()>0){
            holder.txv_status.setText("可预约");
            holder.imv_select.setImageResource(tempIndex == position ? R.drawable.check_selected : R.drawable.check_normal);
        }else{
            holder.txv_status.setText("不可预约");
            holder.imv_select.setImageResource(R.drawable.check_warn);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entity.getNumber()>0){
                    setTempIndex(position);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView imv_select;
        TextView txv_beginTime;
        TextView txv_endTime;
        TextView txv_status;
    }
}
