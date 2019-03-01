package com.weima.aishangyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weima.aishangyi.R;
import com.weima.aishangyi.entity.ClassTypeResp;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class AnswerTypeAdapter extends BaseAdapter {
    private Context context;
    List<ClassTypeResp.DataBean> mData;
    private int tempIndex = 0;

    public AnswerTypeAdapter(Context context, List<ClassTypeResp.DataBean> data) {
        this.context = context;
        this.mData = data;
    }

    public void setTempIndex(int index){
        this.tempIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public ClassTypeResp.DataBean getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filter_sort, null);
            holder = new ViewHolder();
            holder.imv_checkbox = (ImageView) convertView.findViewById(R.id.imv_checkbox);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClassTypeResp.DataBean bean = mData.get(position);
        holder.tv_name.setSelected(position == tempIndex);
        holder.tv_name.setText(bean.getName());
        holder.imv_checkbox.setImageResource(position == tempIndex?R.drawable.ic_checkbox_checked:R.drawable.ic_checkbox_uncheck);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
        ImageView imv_checkbox;
    }
}
