package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.MsgDetailActivity;
import com.weima.aishangyi.entity.MsgBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

public class MsgAdapter extends BaseAdapter {
    private Activity activity;
    private List<MsgBean> dataList = new ArrayList<MsgBean>();

    public MsgAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<MsgBean> list){
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
        return dataList.size();
    }

    @Override
    public MsgBean getItem(int position) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_msg, null);
            holder.txv_title = (TextView) convertView.findViewById(R.id.txv_title);
            holder.txv_time = (TextView) convertView.findViewById(R.id.txv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MsgBean entity = getItem(position);
        holder.txv_title.setText(ProjectHelper.getCommonText(entity.getTitle()));
        holder.txv_time.setText(Helper.long2DateString(entity.getCreated_at()* 1000, "yyyy-MM-dd"));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("msg",entity);
                NavigationHelper.startActivity(activity, MsgDetailActivity.class, bundle, false);
            }
        });
        return convertView;
    }



    static class ViewHolder {
        TextView txv_title;
        TextView txv_time;
    }
}
