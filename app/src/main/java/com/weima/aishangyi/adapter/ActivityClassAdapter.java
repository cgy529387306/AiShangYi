package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ActivityClassDetailActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ActivityBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityClassAdapter extends BaseAdapter {
    private Activity activity;
    private List<ActivityBean> dataList = new ArrayList<>();

    public ActivityClassAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<ActivityBean> list){
        if (Helper.isNotEmpty(list)){
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        dataList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public ActivityBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (Helper.isNull(convertView)) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_activityclass, null);
            holder.imv_icon = (ImageView) convertView.findViewById(R.id.imv_icon);
            holder.txv_title = (TextView) convertView.findViewById(R.id.txv_title);
            holder.txv_beginTime = (TextView) convertView.findViewById(R.id.txv_beginTime);
            holder.txv_endTime = (TextView) convertView.findViewById(R.id.txv_endTime);
            holder.txv_end_status = (TextView) convertView.findViewById(R.id.txv_end_status);
            holder.txv_time_status = (TextView) convertView.findViewById(R.id.txv_time_status);
            holder.txv_cost = (TextView) convertView.findViewById(R.id.txv_cost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ActivityBean entity = dataList.get(position);
        if (Helper.isNotEmpty(entity.getImages())){
            Picasso.with(activity).load(entity.getImages().get(0)).placeholder(R.drawable.img_default).into(holder.imv_icon);
        }
        holder.txv_title.setText(ProjectHelper.getCommonText(entity.getTitle()));
        holder.txv_beginTime.setText(Helper.long2DateString(entity.getStart_time() * 1000, "yyyy-MM-dd HH:mm"));
        holder.txv_endTime.setText(Helper.long2DateString(entity.getClose_time() * 1000, "yyyy-MM-dd HH:mm"));
        holder.txv_end_status.setText(ProjectHelper.getCommonText(entity.getEnd_status()));
        holder.txv_time_status.setText(ProjectHelper.getCommonText(entity.getTime_status()));
        holder.txv_cost.setText("¥"+entity.getPrice());
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProjectConstants.BundleExtra.KEY_ACTIVITY,entity);
                NavigationHelper.startActivity(activity, ActivityClassDetailActivity.class, bundle, false);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView imv_icon;
        TextView txv_title;
        TextView txv_beginTime;
        TextView txv_endTime;
        TextView txv_end_status;
        TextView txv_time_status;
        TextView txv_cost;
    }
}
