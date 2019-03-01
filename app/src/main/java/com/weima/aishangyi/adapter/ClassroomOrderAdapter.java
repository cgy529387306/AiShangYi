package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.weima.aishangyi.activity.OrderPayActivity;
import com.weima.aishangyi.activity.ClassroomOrderDetailAcitity;
import com.weima.aishangyi.activity.EvaluateActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassOrderBean;
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

public class ClassroomOrderAdapter extends BaseAdapter implements ResponseListener {
    private Activity activity;
    private List<ClassOrderBean> dataList = new ArrayList<>();
    private static final int REQUEST_TYPE_CANCEL_ORDER = 1;
    private static final int REQUEST_TYPE_COMFIRM_ORDER = 2;
    private static final int REQUEST_TYPE_DELETE_ORDER = 3;
    public ClassroomOrderAdapter(Activity act) {
        this.activity = act;
    }
    public void addMore(List<ClassOrderBean> list){
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
    public  ClassOrderBean getItem(int position) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_classroomorder, null);
            holder.imv_icon = (ImageView) convertView.findViewById(R.id.imv_icon);
            holder.txv_teacher = (TextView) convertView.findViewById(R.id.txv_teacher);
            holder.txv_status = (TextView) convertView.findViewById(R.id.txv_status);
            holder.txv_classname = (TextView) convertView.findViewById(R.id.txv_classname);
            holder.txv_type = (TextView) convertView.findViewById(R.id.txv_type);
            holder.txv_count = (TextView) convertView.findViewById(R.id.txv_count);
            holder.txv_cost = (TextView) convertView.findViewById(R.id.txv_cost);
            holder.comfirmOrder = (TextView) convertView.findViewById(R.id.comfirmOrder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ClassOrderBean entity = dataList.get(position);
        if (Helper.isNotEmpty(entity.getLesson())) {
            String name = entity.getLesson().getName();
            if (Helper.isNotEmpty(entity.getLesson().getLesson_brief())){
                name = entity.getLesson().getName()+"-"+entity.getLesson().getLesson_brief();
            }
            holder.txv_classname.setText(name);
            if (Helper.isNotEmpty(entity.getLesson().getIcon())) {
                Picasso.with(activity).load(entity.getLesson().getIcon()).placeholder(R.drawable.img_default).into(holder.imv_icon);
            }
            holder.txv_type.setText(entity.getLesson().getLesson_item()==1?"一对一课程":"拼课");
        }
        if (Helper.isNotEmpty(entity.getTeacher())){
            holder.txv_teacher.setText(ProjectHelper.getCommonText(entity.getTeacher().getNickname()));
        }
        holder.txv_status.setText(ProjectHelper.getOrderStatus(entity.getStatus()));
        holder.txv_count.setText(entity.getNumber()+"课时");
        //  "status" : "0", //0待确认，1待授课，2待评价，3已完成，4取消订单
        if (entity.getStatus() == 0){
            holder.comfirmOrder.setText("取消订单");
            orderCancelEvent(holder.comfirmOrder,entity.getId());
        }else if (entity.getStatus()==1){
            holder.comfirmOrder.setText("确认授课");
            orderComfirmEvent(holder.comfirmOrder,entity.getId());
        }else if (entity.getStatus()==2){
            holder.comfirmOrder.setText("去评价");
            orderEvaluateEvent(holder.comfirmOrder,position);
        }else{
            holder.comfirmOrder.setText("删除订单");
            orderDeleteEvent(holder.comfirmOrder,entity.getId());
        }
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("order_id",entity.getId());
                NavigationHelper.startActivity(activity, ClassroomOrderDetailAcitity.class, bundle, false);
            }
        });
        return convertView;
    }

    private void orderCancelEvent(View view, final long orderId){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.showConfirmDialog(activity, "取消订单", "确定要取消该订单？", true,
                        R.string.dialog_positive, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestCancelOrder(orderId);
                                dialog.dismiss();
                            }

                        }, R.string.dialog_negative, null);
            }
        });
    }

    private void orderDeleteEvent(View view, final long orderId){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.showConfirmDialog(activity, "删除订单", "确定要删除该订单？", true,
                        R.string.dialog_positive, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestDeleteOrder(orderId);
                                dialog.dismiss();
                            }

                        }, R.string.dialog_negative, null);
            }
        });
    }

    private void orderComfirmEvent(View view, final long orderId){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.showConfirmDialog(activity, "确认授课", "确定要确认授课？", true,
                        R.string.dialog_positive, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestComfirmlOrder(orderId);
                                dialog.dismiss();
                            }

                        }, R.string.dialog_negative, null);
            }
        });
    }

    private void orderEvaluateEvent(View view, final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassOrderBean entity = dataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order",entity);
                NavigationHelper.startActivity(activity, EvaluateActivity.class, bundle, false);
            }
        });
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        int requestType = Integer.parseInt(extras[0].toString());
        CommonEntity entity = JsonHelper.fromJson(response,CommonEntity.class);
        if ("200".equals(entity.getCode())){
            LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent("classOrder"));
            if (requestType == REQUEST_TYPE_CANCEL_ORDER){
                ToastHelper.showToast("取消订单");
            }else if (requestType == REQUEST_TYPE_COMFIRM_ORDER){
                ToastHelper.showToast("确认授课");
            }else if (requestType == REQUEST_TYPE_DELETE_ORDER){
                ToastHelper.showToast("删除订单");
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
        ProgressDialogHelper.dismissProgressDialog();
        return true;
    }

    private void requestCancelOrder(long orderId) {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("order_id", orderId);
        post(ProjectConstants.Url.ORDER_CANCEL, requestMap, REQUEST_TYPE_CANCEL_ORDER);
    }

    private void requestComfirmlOrder(long orderId) {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("order_id", orderId);
        post(ProjectConstants.Url.ORDER_COMFIRM, requestMap, REQUEST_TYPE_COMFIRM_ORDER);
    }

    private void requestDeleteOrder(long orderId) {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("order_id", orderId);
        post(ProjectConstants.Url.ORDER_LESSON_DELETE, requestMap, REQUEST_TYPE_DELETE_ORDER);
    }


    public void post(String url, HashMap<String, Object> requestParamsMap, Object... extras) {
        LogHelper.i(url);
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestParamsMap).getRequestEntity();
        RequestHelper.post(entity, this, extras);
    }

    static class ViewHolder {
        ImageView imv_icon;
        TextView txv_classname;
        TextView txv_status;
        TextView txv_teacher;
        TextView txv_type;
        TextView txv_count;
        TextView txv_cost;
        TextView comfirmOrder;
    }
}
