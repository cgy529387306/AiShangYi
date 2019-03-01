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
import com.weima.aishangyi.entity.LunboEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 才艺资讯详情的演出经历的adapter
 * Created by hanj on 14-9-25.
 */
public class PlayAdapter extends BaseAdapter {
    public PlayAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public String getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_play, null);
            holder.txv_title = (TextView) convertView.findViewById(R.id.txv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        TextView txv_title;
    }
}
