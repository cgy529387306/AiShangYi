package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
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
import com.weima.aishangyi.activity.ActvityOrderDetailAcitity;
import com.weima.aishangyi.activity.ClassroomOrderDetailAcitity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ActivityBean;
import com.weima.aishangyi.entity.ActivityOrderBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityClassOrderAdapter extends BaseAdapter {
    private Activity activity;
    private List<ActivityOrderBean> dataList = new ArrayList<>();

    public ActivityClassOrderAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<ActivityOrderBean> list){
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
    public ActivityOrderBean getItem(int position) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_activityclass_order, null);
            holder.imv_icon = (ImageView) convertView.findViewById(R.id.imv_icon);
            holder.txv_title = (TextView) convertView.findViewById(R.id.txv_title);
            holder.txv_status = (TextView) convertView.findViewById(R.id.txv_status);
            holder.txv_time = (TextView) convertView.findViewById(R.id.txv_time);
            holder.txv_content = (TextView) convertView.findViewById(R.id.txv_content);
            holder.txv_count = (TextView) convertView.findViewById(R.id.txv_count);
            holder.txv_cost = (TextView) convertView.findViewById(R.id.txv_cost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ActivityOrderBean entity = dataList.get(position);
        holder.txv_status.setText(ProjectHelper.getOrderStatus(entity.getStatus()));
        holder.txv_count.setText(entity.getNumber()+"人次");
        holder.txv_cost.setText("¥"+entity.getAmount());
        if (Helper.isNotEmpty(entity.getActive_order()) && Helper.isNotEmpty(entity.getActive_order().getActive())){
            ActivityOrderBean.ActiveOrderBean.ActiveBean activeBean = entity.getActive_order().getActive();
            if (Helper.isNotEmpty(activeBean.getImages())){
                Picasso.with(activity).load(activeBean.getImages().get(0)).placeholder(R.drawable.img_default).into(holder.imv_icon);
            }
            holder.txv_title.setText(ProjectHelper.getCommonText(activeBean.getTitle()));
            holder.txv_content.setText(Helper.isEmpty(activeBean.getContent())?"": Html.fromHtml(activeBean.getContent()));
            holder.txv_time.setText("活动时间："+Helper.long2DateString(activeBean.getStart_time() * 1000, "yyyy-MM-dd HH:mm"));
        }
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", entity);
                NavigationHelper.startActivity(activity, ActvityOrderDetailAcitity.class, bundle, false);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView imv_icon;
        TextView txv_title;
        TextView txv_status;
        TextView txv_time;
        TextView txv_content;
        TextView txv_count;
        TextView txv_cost;
    }
}
