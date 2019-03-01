package com.weima.aishangyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weima.aishangyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动课堂的兴趣分类的adapter
 * Created by hanj on 14-9-25.
 */
public class FunTypeAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private int tempIndex = 0;

    public FunTypeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list == null?getTestData():list;
    }

    private List<String> getTestData(){
        List<String> dataList = new ArrayList<>();
        dataList.add("艺术");
        dataList.add("兴趣");
        dataList.add("演出");
        return  dataList;
    }

    public void setTempIndex(int index){
        this.tempIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
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
        holder.tv_name.setSelected(position == tempIndex);
        holder.tv_name.setText(list.get(position));
        holder.imv_checkbox.setImageResource(position == tempIndex?R.drawable.ic_checkbox_checked:R.drawable.ic_checkbox_uncheck);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
        ImageView imv_checkbox;
    }
}
