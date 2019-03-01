package com.weima.aishangyi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.PersonInfoActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.FollowBean;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Activity activity;
    private List<FollowBean> dataList = new ArrayList<FollowBean>();

    public ContactAdapter(Activity act) {
        this.activity = act;
    }

    public void addMore(List<FollowBean> list){
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
        return dataList.size();
    }

    @Override
    public FollowBean getItem(int position) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_contact, null);
            holder.imv_user_avater = (ImageView) convertView.findViewById(R.id.imv_user_avater);
            holder.imv_concern = (ImageView) convertView.findViewById(R.id.imv_concern);
            holder.txv_user_name = (TextView) convertView.findViewById(R.id.txv_user_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FollowBean followBean = getItem(position);
        final User entity = followBean.getUser();
        if (Helper.isNotEmpty(entity)){
            convertView.setVisibility(View.VISIBLE);
            if (Helper.isNotEmpty(entity.getIcon())){
                Picasso.with(activity).load(entity.getIcon()).placeholder(R.drawable.ic_avatar_default).into(holder.imv_user_avater);
            }
            if (Helper.isNotEmpty(entity.getNickname())){
                holder.txv_user_name.setText(entity.getNickname());
            }else{
                holder.txv_user_name.setText(entity.getPhone());
            }
            if (entity.getIs_follow() == 0){
                holder.imv_concern.setImageResource(R.drawable.ic_fans_none);
            }else if (entity.getIs_follow() == 1){
                holder.imv_concern.setImageResource(R.drawable.ic_fans_one);
            }else if (entity.getIs_follow() == 2){
                holder.imv_concern.setImageResource(R.drawable.ic_fans_each);
            }
            holder.imv_user_avater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ProjectConstants.BundleExtra.KEY_USER, entity);
                    NavigationHelper.startActivity(activity, PersonInfoActivity.class, bundle, false);
                }
            });
            holder.imv_concern.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickFollow(entity);
                }
            });
        }
        return convertView;
    }

    private void clickFollow(User entity) {
        ProgressDialogHelper.showProgressDialog(activity, "加载中...");
        if (entity.getIs_follow() == 0){
            requestFollow(entity);
        }else{
            cancelFollow(entity);
        }
    }

    private void requestFollow(User user){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",user.getId());
        requestMap.put("type", user.getDevice());
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(ProjectConstants.Url.FOLLOW)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestMap).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity upEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(upEntity.getCode())) {
                    ToastHelper.showToast("关注成功");
                    LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_FANS_LIST));
                    LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
                } else {
                    ToastHelper.showToast(upEntity.getMessage());
                }
                return false;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                ToastHelper.showToast("关注失败");
                return false;
            }
        });
    }

    private void cancelFollow(User user){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",user.getId());
        requestMap.put("type", user.getDevice());
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(ProjectConstants.Url.FOLLOW_CANCEL)
                .setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestMap).getRequestEntity();
        RequestHelper.post(entity, new ResponseListener() {
            @Override
            public boolean onResponseSuccess(int gact, String response, Object... extras) {
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity upEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(upEntity.getCode())){
                    ToastHelper.showToast("取消关注成功");
                    LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_FANS_LIST));
                    LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
                }else{
                    ToastHelper.showToast(upEntity.getMessage());
                }
                return false;
            }

            @Override
            public boolean onResponseError(int gact, String response, VolleyError error, Object... extras) {
                ToastHelper.showToast("取消关注失败");
                return false;
            }
        });
    }



    static class ViewHolder {
        ImageView imv_user_avater;
        ImageView imv_concern;
        TextView txv_user_name;
    }
}
