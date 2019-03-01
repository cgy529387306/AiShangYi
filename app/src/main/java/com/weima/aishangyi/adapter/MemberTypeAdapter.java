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

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class MemberTypeAdapter extends BaseAdapter {
    private Context activity;
    private List<MemberBean.ContentBean> dataList = new ArrayList<>();
    private int tempIndex = 0;

    public MemberTypeAdapter(Context context) {
        this.activity = context;
    }

    public void addMore(List<MemberBean.ContentBean> list){
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
    public MemberBean.ContentBean getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_member_type, null);
            holder.imv_select = (ImageView) convertView.findViewById(R.id.imv_select);
            holder.txv_type = (TextView) convertView.findViewById(R.id.txv_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MemberBean.ContentBean entity = dataList.get(position);
        holder.imv_select.setImageResource(tempIndex == position ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_uncheck);
        String name = entity.getMonth()+"个月 ("+entity.getNumber()+"课时)  "+ entity.getMoney()+"元";
        holder.txv_type.setText(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTempIndex(position);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView imv_select;
        TextView txv_type;
    }
}
