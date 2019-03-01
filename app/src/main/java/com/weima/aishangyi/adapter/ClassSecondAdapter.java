package com.weima.aishangyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.entity.ClassTypeResp;

import java.util.List;


/**
 * 二级分类（即右侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class ClassSecondAdapter extends BaseAdapter{
    private Context context;
    private List<ClassTypeResp.DataBean.ChildrenBean> list;

    public ClassSecondAdapter(Context context, List<ClassTypeResp.DataBean.ChildrenBean> childrenBeans){
        this.context = context;
        this.list = childrenBeans;
    }

    public void updateList(List<ClassTypeResp.DataBean.ChildrenBean> dataBeanList){
        this.list = dataBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public ClassTypeResp.DataBean.ChildrenBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_class_second, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.imv_icon = (ImageView) convertView.findViewById(R.id.imv_icon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ClassTypeResp.DataBean.ChildrenBean dataBean = list.get(position);
        if (Helper.isNotEmpty(dataBean.getIcon())){
            Picasso.with(context).load(dataBean.getIcon()).placeholder(R.drawable.img_default).into(holder.imv_icon);
        }
        holder.tv_name.setText(dataBean.getName()==null?"":dataBean.getName());
        return convertView;
    }

    private class ViewHolder{
        TextView tv_name;
        ImageView imv_icon;
    }

}
