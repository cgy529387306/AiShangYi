package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.AnswerDetailAcitity;
import com.weima.aishangyi.activity.AnswerQuestionAcitity;
import com.weima.aishangyi.activity.AnswerUnpayDetailAcitity;
import com.weima.aishangyi.activity.OrderPayActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.AnswerBean;
import com.weima.aishangyi.entity.CommonDataResp;
import com.weima.aishangyi.entity.QuestionBean;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class AnswerAdapter extends BaseAdapter {
    private Activity activity;
    private List<QuestionBean> dataList = new ArrayList<>();
    private double seeCost = 0.01;
    public AnswerAdapter(Activity act) {
        this.activity = act;
        initCommonData();
    }

    public void addMore(List<QuestionBean> list){
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
    public Object getItem(int position) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_answer, null);
            holder.imv_teacher_avater = (ImageView) convertView.findViewById(R.id.imv_teacher_avater);
            holder.txv_problem = (TextView) convertView.findViewById(R.id.txv_problem);
            holder.txv_teacher_name = (TextView) convertView.findViewById(R.id.txv_teacher_name);
            holder.txv_teacher_goodat = (TextView) convertView.findViewById(R.id.txv_teacher_goodat);
            holder.txv_words_count = (TextView) convertView.findViewById(R.id.txv_words_count);
            holder.txv_good_count = (TextView) convertView.findViewById(R.id.txv_good_count);
            holder.txv_see_count = (TextView) convertView.findViewById(R.id.txv_see_count);
            holder.btn_operate = (TextView) convertView.findViewById(R.id.btn_operate);
            holder.btn_question = (TextView) convertView.findViewById(R.id.btn_question);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final QuestionBean entity = dataList.get(position);
        holder.txv_problem.setText(ProjectHelper.getCommonText(entity.getProblem()));
        if (Helper.isNotEmpty(entity.getBest()) && Helper.isNotEmpty(entity.getBest().getTeacher())){
            AnswerBean.TeacherBean teacherBean = entity.getBest().getTeacher();
            holder.txv_teacher_name.setText(ProjectHelper.getCommonText(teacherBean.getNickname()));
            holder. txv_teacher_goodat.setText(Helper.isEmpty(teacherBean.getGood_at())?"听说该老师是全能型的哦":teacherBean.getGood_at());
            if (Helper.isNotEmpty(teacherBean.getIcon())){
                Picasso.with(activity).load(teacherBean.getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_teacher_avater);
            }
        }
        holder.txv_words_count.setText(entity.getAnswer_count()+"");
        holder.txv_good_count.setText(entity.getThumb_count()+"");
        holder.txv_see_count.setText(entity.getSee_count() + "");
        if (Helper.isEmpty(entity.getIs_thumb())){
            Drawable drawable= activity.getResources().getDrawable(R.drawable.ic_answer_good);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.txv_good_count.setCompoundDrawables(drawable, null, null, null);
        }else{
            Drawable drawable= activity.getResources().getDrawable(R.drawable.ic_answer_good_press);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.txv_good_count.setCompoundDrawables(drawable, null, null, null);
        }
        holder.btn_operate.setText(Helper.isEmpty(entity.getIs_see())?seeCost+"偷偷看":"看答案");
        holder.btn_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHelper.startActivity(activity, AnswerQuestionAcitity.class, null, false);
            }
        });
        holder.btn_operate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Helper.isEmpty(entity.getIs_see())){
                    Bundle bundle = new Bundle();
                    bundle.putInt("type",4);
                    bundle.putLong("question_id",entity.getId());
                    bundle.putDouble("price", seeCost);
                    NavigationHelper.startActivity(activity, OrderPayActivity.class, bundle, false);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("question", entity);
                    NavigationHelper.startActivity(activity, AnswerDetailAcitity.class, bundle, false);
                }

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isEmpty(entity.getIs_see())){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("question", entity);
                    NavigationHelper.startActivity(activity, AnswerUnpayDetailAcitity.class, bundle, false);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("question", entity);
                    NavigationHelper.startActivity(activity, AnswerDetailAcitity.class, bundle, false);
                }
            }
        });
        return convertView;
    }

    private void initCommonData(){
        try {
            String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_COMMON_DATA);
            if (Helper.isNotEmpty(response)){
                CommonDataResp entity = JsonHelper.fromJson(response, CommonDataResp.class);
                if ("200".equals(entity.getCode())) {
                    if (Helper.isNotEmpty(entity.getData())){
                        seeCost = Double.parseDouble(entity.getData().getPrice());
                    }
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static class ViewHolder{
        ImageView imv_teacher_avater;
        TextView txv_problem;
        TextView txv_teacher_name;
        TextView txv_teacher_goodat;
        TextView txv_words_count;
        TextView txv_good_count;
        TextView txv_see_count;
        TextView btn_question;
        TextView btn_operate;
    }
}
