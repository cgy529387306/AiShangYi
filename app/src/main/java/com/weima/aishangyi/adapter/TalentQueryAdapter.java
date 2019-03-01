package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.TalentqueryDetailAcitity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.InfoBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

public class TalentQueryAdapter extends BaseAdapter {
    private Activity activity;
    private List<InfoBean> dataList = new ArrayList<>();

    public TalentQueryAdapter(Activity act) {
        this.activity = act;
    }
    public void addMore(List<InfoBean> list){
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
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public InfoBean getItem(int position) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_talentquery, null);
            holder.imv_icon = (ImageView) convertView.findViewById(R.id.imv_icon);
            holder.imv_ding = (ImageView) convertView.findViewById(R.id.imv_ding);
            holder.imv_jing = (ImageView) convertView.findViewById(R.id.imv_jing);
            holder.txv_title = (TextView) convertView.findViewById(R.id.txv_title);
            holder.txv_content = (TextView) convertView.findViewById(R.id.txv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InfoBean infoBean = dataList.get(position);
        if (Helper.isNotEmpty(infoBean.getImage_url())){
            Picasso.with(activity).load(infoBean.getImage_url()).placeholder(R.drawable.img_default).into(holder.imv_icon);
        }
        holder.imv_ding.setVisibility(infoBean.getTop()==1?View.VISIBLE:View.GONE);
        holder.imv_jing.setVisibility(infoBean.getFine()==1?View.VISIBLE:View.GONE);
        holder.txv_title.setText(ProjectHelper.getCommonText(infoBean.getTitle()));
        holder.txv_content.setText(Html.fromHtml(infoBean.getContent()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProjectConstants.BundleExtra.KEY_INFO, infoBean);
                NavigationHelper.startActivity(activity, TalentqueryDetailAcitity.class, bundle, false);

            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView imv_icon;
        ImageView imv_ding;
        ImageView imv_jing;
        TextView txv_title;
        TextView txv_content;
    }
}
