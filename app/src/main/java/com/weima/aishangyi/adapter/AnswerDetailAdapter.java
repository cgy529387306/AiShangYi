package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.entity.AnswerBean;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class AnswerDetailAdapter extends BaseAdapter {
     private Activity activity;
    private List<AnswerBean> dataList = new ArrayList<>();
    public AnswerDetailAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<AnswerBean> list){
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
    public AnswerBean getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_answer_detail, null);
            holder.imv_user_avater = (ImageView) convertView.findViewById(R.id.imv_user_avater);
            holder.txv_answer = (TextView) convertView.findViewById(R.id.txv_answer);
            holder.txv_user_name = (TextView) convertView.findViewById(R.id.txv_user_name);
            holder.txv_create_time = (TextView) convertView.findViewById(R.id.txv_words_count);
            holder.txv_words_count = (TextView) convertView.findViewById(R.id.txv_money);
            holder.txv_good_count = (TextView) convertView.findViewById(R.id.txv_good_count);
            holder.txv_see_count = (TextView) convertView.findViewById(R.id.txv_see_count);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AnswerBean entity = dataList.get(position);
        holder.txv_answer.setText(ProjectHelper.getCommonText(entity.getAnswer()));
        if (Helper.isNotEmpty(entity.getTeacher())){
            AnswerBean.TeacherBean teacherBean = entity.getTeacher();
            holder.txv_user_name.setText(ProjectHelper.getCommonText(teacherBean.getNickname()));
            holder.txv_create_time.setText(Helper.isEmpty(entity.getTeacher().getGood_at())?"听说该老师是全能型的哦":entity.getTeacher().getGood_at());
            if (Helper.isNotEmpty(teacherBean.getIcon())){
                Picasso.with(activity).load(teacherBean.getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_user_avater);
            }
        }
        return convertView;
    }


    private String formatTime(String time){
        String result = "";
        try {
            result = Helper.long2DateString(Long.parseLong(time) * 1000, "MM月dd日");
        }catch (Exception e){
            return "2017-10-10";
        }
        return result;
    }

    static class ViewHolder{
        ImageView imv_user_avater;
        TextView txv_answer;
        TextView txv_user_name;
        TextView txv_create_time;
        TextView txv_words_count;
        TextView txv_good_count;
        TextView txv_see_count;
    }
}
