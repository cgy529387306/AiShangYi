package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.PersonInfoActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.Appraise;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

public class AppraiseAdapter extends BaseAdapter {
    private Activity activity;
    private List<Appraise> dataList = new ArrayList<>();

    public AppraiseAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<Appraise> list){
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
    public Appraise getItem(int position) {
        return dataList != null ? dataList.get(position) : null;
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_appraise, null);
            holder.imv_user_avater = (ImageView) convertView.findViewById(R.id.imv_user_avater);
            holder.txv_user_name = (TextView) convertView.findViewById(R.id.txv_user_name);
            holder.txv_comment_time = (TextView) convertView.findViewById(R.id.txv_comment_time);
            holder.txv_comment_content = (TextView) convertView.findViewById(R.id.txv_comment_content);
            holder.star = (RatingBar) convertView.findViewById(R.id.star);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Appraise entity = dataList.get(position);
        if (Helper.isNotEmpty(entity.getUser())){
            if (Helper.isNotEmpty(entity.getUser().getNickname())){
                holder.txv_user_name.setText(ProjectHelper.getCommonText(entity.getUser().getNickname()));
            }else{
                holder.txv_user_name.setText(ProjectHelper.getCommonText(entity.getUser().getPhone()));
            }
            if (Helper.isNotEmpty(entity.getUser().getIcon())){
                Picasso.with(activity).load(entity.getUser().getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_user_avater);
            }else{
                holder.imv_user_avater.setImageResource(R.drawable.ic_avatar_default);
            }
        }
        holder.imv_user_avater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProjectConstants.BundleExtra.KEY_USER, entity.getUser());
                NavigationHelper.startActivity(activity, PersonInfoActivity.class, bundle, false);
            }
        });
//        holder.txv_comment_time.setText(Helper.long2DateString(Long.parseLong(entity.get()) * 1000, "yyyy-MM-dd HH:mm"));
        holder.txv_comment_content.setText(ProjectHelper.getCommonText(entity.getAppraise()));
        holder.star.setRating(entity.getStar());
        return convertView;
    }

    static class ViewHolder {
        ImageView imv_user_avater;
        RatingBar star;
        TextView txv_user_name;
        TextView txv_comment_time;
        TextView txv_comment_content;
    }
}
