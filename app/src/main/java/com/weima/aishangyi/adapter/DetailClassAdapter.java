package com.weima.aishangyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ClassDetailAcitity;
import com.weima.aishangyi.entity.LessonBean;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class DetailClassAdapter extends BaseAdapter {
    private Context activity;
    private List<LessonBean> dataList = new ArrayList<>();
    private int tempIndex = 0;

    public DetailClassAdapter(Context context) {
        this.activity = context;
    }

    public void addMore(List<LessonBean> list){
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
    public LessonBean getItem(int i) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_detail_class, null);
            holder.imv_class_icon = (ImageView) convertView.findViewById(R.id.imv_class_icon);
            holder.imv_class_select = (ImageView) convertView.findViewById(R.id.imv_class_select);
            holder.txv_class_title = (TextView) convertView.findViewById(R.id.txv_class_title);
            holder.txv_class_type = (TextView) convertView.findViewById(R.id.txv_class_type);
            holder.txv_class_desc = (TextView) convertView.findViewById(R.id.txv_class_desc);
            holder.txv_class_number = (TextView) convertView.findViewById(R.id.txv_class_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LessonBean entity = dataList.get(position);
        if (Helper.isNotEmpty(entity.getIcon())){
            Picasso.with(activity).load(entity.getIcon()).placeholder(R.drawable.img_default).into(holder.imv_class_icon);
        }
        holder.imv_class_select.setImageResource(tempIndex == position ? R.drawable.ic_checkbox_checked : R.drawable.ic_checkbox_uncheck);
        holder.txv_class_title.setText(ProjectHelper.getCommonText(entity.getName()));
        holder.txv_class_type.setText(entity.getLesson_item() == 1 ? "一对一" : "拼课");
        holder.txv_class_type.setBackgroundResource(entity.getLesson_item() == 1 ? R.drawable.shape_rect_blue : R.drawable.shape_rect_orange);
        holder.txv_class_desc.setText(ProjectHelper.getCommonText(entity.getLesson_brief()));
        holder.txv_class_number.setText(entity.getNumber()+"课时");
        holder.imv_class_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTempIndex(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ClassDetailAcitity.class);
                intent.putExtra("id", entity.getId());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        ImageView imv_class_icon;
        ImageView imv_class_select;
        TextView txv_class_title;
        TextView txv_class_type;
        TextView txv_class_desc;
        TextView txv_class_number;
    }
}
