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

import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class ClassFirstAdapter extends BaseAdapter {
    private Context context;
    private List<ClassTypeResp.DataBean> list;
    private int tempIndex = 0;

    public ClassFirstAdapter(Context context, List<ClassTypeResp.DataBean> dataBeanList) {
        this.context = context;
        this.list = dataBeanList;
    }

    public void updateList(List<ClassTypeResp.DataBean> dataBeanList){
        this.list = dataBeanList;
        notifyDataSetChanged();
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
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_class_first, null);
            holder = new ViewHolder();
            holder.tag = convertView.findViewById(R.id.tag);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_arrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClassTypeResp.DataBean dataBean = list.get(position);
        holder.tag.setVisibility(position == tempIndex ? View.VISIBLE : View.INVISIBLE);
        holder.tv_name.setSelected(position == tempIndex);
        holder.tv_name.setText(dataBean.getName() == null ? "" : dataBean.getName());
        holder.tv_name.setText(dataBean.getName()==null?"":dataBean.getName());
        holder.iv_arrow.setSelected(position == tempIndex);
        return convertView;
    }

    private class ViewHolder {
        View tag;
        TextView tv_name;
        ImageView iv_arrow;
    }
}
