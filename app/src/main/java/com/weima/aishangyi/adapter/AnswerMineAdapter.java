package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.DialogHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.ClassroomOrderDetailAcitity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.AnswerBean;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 一级分类（即左侧菜单）的adapter
 * Created by hanj on 14-9-25.
 */
public class AnswerMineAdapter extends BaseAdapter implements ResponseListener {
    private Activity activity;
    private List<AnswerBean> dataList = new ArrayList<>();
    private ItemHandler itemHandler;
    private long bestAnswer;
    public void setItemHandler(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }

    public interface ItemHandler {
        void onRefresh();
    }


    public AnswerMineAdapter(Activity act) {
        this.activity = act;
    }



    public void addMore(List<AnswerBean> list,long best){
        this.bestAnswer = best;
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_answer_mine, null);
            holder.imv_teacher_avater = (ImageView) convertView.findViewById(R.id.imv_teacher_avater);
            holder.txv_answer = (TextView) convertView.findViewById(R.id.txv_answer);
            holder.txv_teacher_name = (TextView) convertView.findViewById(R.id.txv_teacher_name);
            holder.txv_teacher_goodat = (TextView) convertView.findViewById(R.id.txv_teacher_goodat);
            holder.btn_operate = (TextView) convertView.findViewById(R.id.btn_operate);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AnswerBean entity = dataList.get(position);
        holder.txv_answer.setText(ProjectHelper.getCommonText(entity.getAnswer()));
        if (Helper.isNotEmpty(entity.getTeacher())) {
            AnswerBean.TeacherBean teacherBean = entity.getTeacher();
            holder.txv_teacher_name.setText(ProjectHelper.getCommonText(teacherBean.getNickname()));
            holder.txv_teacher_goodat.setText(Helper.isEmpty(entity.getTeacher().getGood_at()) ? "听说该老师是全能型的哦" : entity.getTeacher().getGood_at());
            if (Helper.isNotEmpty(teacherBean.getIcon())){
                Picasso.with(activity).load(teacherBean.getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_teacher_avater);
            }
        }
        if (Helper.isEmpty(bestAnswer) || bestAnswer==0){
            holder.btn_operate.setText("采纳");
            acceptAnswerEvent(holder.btn_operate, entity.getQuestion_id(), entity.getId());
        }else {
            if (bestAnswer==entity.getId()){
                holder.btn_operate.setText("已采纳");
            }else{
                holder.btn_operate.setText("未采纳");
            }
        }
        return convertView;
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
        if ("200".equals(entity.getCode())){
            ToastHelper.showToast("已采纳");
            LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_QUESTION_LIST));
            if (itemHandler!=null){
                itemHandler.onRefresh();
            }
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ToastHelper.showToast("请求失败");
        return true;
    }

    private void acceptAnswerEvent(View view, final long question_id, final long answer_id){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.showConfirmDialog(activity, "确认采纳", "确认要采纳该老师的答案吗？", true,
                        R.string.dialog_positive, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestAcceptAnswer(question_id, answer_id);
                                dialog.dismiss();
                            }

                        }, R.string.dialog_negative, null);
            }
        });
    }

    private void requestAcceptAnswer(long question_id, long answer_id) {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("question_id", question_id);
        requestMap.put("answer_id", answer_id);
        post(ProjectConstants.Url.QUESTION_SETBEST, requestMap);
    }

    public void post(String url, HashMap<String, Object> requestParamsMap, Object... extras) {
        LogHelper.i(url);
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestParamsMap).getRequestEntity();
        RequestHelper.post(entity, this, extras);
    }


    static class ViewHolder{
        ImageView imv_teacher_avater;
        TextView txv_answer;
        TextView txv_teacher_name;
        TextView txv_teacher_goodat;
        TextView btn_operate;
    }
}
