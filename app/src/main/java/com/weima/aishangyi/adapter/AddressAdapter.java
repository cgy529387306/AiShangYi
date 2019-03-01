package com.weima.aishangyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.mb.android.utils.Helper;
import com.weima.aishangyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
public class AddressAdapter extends BaseAdapter {
    private Context context;
    private List<PoiItem> poiItems = new ArrayList<>();

    public AddressAdapter(Context context, List<PoiItem> list) {
        this.context = context;
        this.poiItems = list;
    }


    @Override
    public int getCount() {
        return poiItems.size();
    }

    @Override
    public PoiItem getItem(int position) {
        return poiItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_address, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.snippet = (TextView) convertView.findViewById(R.id.snippet);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PoiItem poiItem = getItem(position);
        holder.title.setText(Helper.isEmpty(poiItem.getTitle())?"":poiItem.getTitle());
        holder.snippet.setText(Helper.isEmpty(poiItem.getSnippet())?"":poiItem.getSnippet());
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView snippet;
    }
}