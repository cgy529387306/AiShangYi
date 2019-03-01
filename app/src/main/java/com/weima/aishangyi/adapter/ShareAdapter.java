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
 * 详情分享的adapter
 * Created by hanj on 14-9-25.
 */
public class ShareAdapter extends BaseAdapter {
    private Context context;
    private int tempIndex = 0;
    private String[] name = {"朋友圈", "微信", "QQ", "QQ空间", "新浪微博"};
    private int image[] = {R.drawable.ic_weixincircle, R.drawable.ic_weixins, R.drawable.ic_qq, R.drawable.ic_qcenter, R.drawable.ic_sina};

    public ShareAdapter(Context context, List<String> list) {
        this.context = context;
    }


    public void setTempIndex(int index) {
        this.tempIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public String getItem(int position) {
        return name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_share, null);
            holder = new ViewHolder();
            holder.imv_checked = (ImageView) convertView.findViewById(R.id.imv_checked);
            holder.txv_name = (TextView) convertView.findViewById(R.id.txv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txv_name.setSelected(position == tempIndex);
        holder.txv_name.setText(name[position]);
        holder.imv_checked.setImageResource(image[position]);
        return convertView;
    }

    private class ViewHolder {
        TextView txv_name;
        ImageView imv_checked;
    }
}
