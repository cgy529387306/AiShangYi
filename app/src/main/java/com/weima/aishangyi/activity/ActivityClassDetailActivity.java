package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ActivityBean;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.map.AddressActivity;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 作者：cgy on 16/11/26 15:21
 * 邮箱：593960111@qq.com
 * 活动课堂详情
 */
public class ActivityClassDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView txv_title,txv_beginTime,txv_endTime,txv_end_status,txv_time_status;
    private TextView txv_cost,txv_count_all,txv_count_join,txv_address,txv_content;
    private ActivityBean activityBean;
    private ImageView imv_icon;
    private ImageView collectBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityclass_detail);
        setCustomTitle("活动详情");
        setImageRightButton(R.drawable.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isNotEmpty(activityBean)){
                    Bundle bundle = new Bundle();
                    if (Helper.isNotEmpty(activityBean.getImages())){
                        bundle.putString("imageUrl",activityBean.getImages().get(0));
                    }
                    bundle.putString("title",activityBean.getTitle());
                    NavigationHelper.startActivity(ActivityClassDetailActivity.this,ShareActivity.class,bundle,false);
                }
            }
        });
        collectBtn = setImageRightButton2(R.drawable.ic_collect, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCollect();
            }
        });
        initUI();
        initData();
    }

    private void initUI() {
        imv_icon = findView(R.id.imv_icon);
        txv_title = findView(R.id.txv_title);
        txv_beginTime = findView(R.id.txv_beginTime);
        txv_endTime = findView(R.id.txv_endTime);
        txv_end_status = findView(R.id.txv_end_status);
        txv_time_status = findView(R.id.txv_time_status);
        txv_cost = findView(R.id.txv_cost);
        txv_count_all = findView(R.id.txv_count_all);
        txv_count_join = findView(R.id.txv_count_join);
        txv_address = findView(R.id.txv_address);
        txv_content = findView(R.id.txv_content);

        findView(R.id.btn_enroll).setOnClickListener(this);
        findView(R.id.txv_location).setOnClickListener(this);
    }

    private void initData() {
        ActivityBean entity = (ActivityBean) getIntent().getSerializableExtra(ProjectConstants.BundleExtra.KEY_ACTIVITY);
        if (Helper.isNotEmpty(entity)){
            activityBean = entity;
            if (Helper.isNotEmpty(entity.getImages())){
                Picasso.with(ActivityClassDetailActivity.this).load(entity.getImages().get(0)).placeholder(R.drawable.img_default).into(imv_icon);
            }
            txv_title.setText(ProjectHelper.getCommonText(entity.getTitle()));
            txv_beginTime.setText(ProjectHelper.getCommonText(Helper.long2DateString(entity.getStart_time()*1000,"yyyy-MM-dd HH:mm")));
            txv_endTime.setText(ProjectHelper.getCommonText(Helper.long2DateString(entity.getClose_time()*1000,"yyyy-MM-dd HH:mm")));
            txv_cost.setText("¥"+entity.getPrice());
            txv_count_all.setText("可报名数："+entity.getNumber());
            txv_count_join.setText("已报名数："+entity.getHas_number());
            txv_address.setText(ProjectHelper.getCommonText(entity.getAddress()));
            txv_content.setText(Helper.isEmpty(entity.getContent())?"":Html.fromHtml(entity.getContent()));
            txv_end_status.setText(ProjectHelper.getCommonText(entity.getEnd_status()));
            txv_time_status.setText(ProjectHelper.getCommonText(entity.getTime_status()));
            collectBtn.setImageResource(activityBean.is_collect()==1?R.drawable.ic_collect_press:R.drawable.ic_collect);
        }
    }


    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        switch (requestType){
            case 2:
                //收藏
                ProgressDialogHelper.dismissProgressDialog();
                CommonEntity collectEntity = JsonHelper.fromJson(response, CommonEntity.class);
                if ("200".equals(collectEntity.getCode())){
                    if (activityBean.is_collect()==1){
                        activityBean.setIs_collect(0);
                        ToastHelper.showToast("取消收藏成功");
                    }else{
                        activityBean.setIs_collect(1);
                        ToastHelper.showToast("收藏成功");
                    }
                    collectBtn.setImageResource(activityBean.is_collect()==1?R.drawable.ic_collect_press:R.drawable.ic_collect);
                    LocalBroadcastManager.getInstance(ActivityClassDetailActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_ACTIVITY_LIST));
                }else{
                    ToastHelper.showToast(collectEntity.getMessage());
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ToastHelper.showToast("请求失败！");
        return true;
    }

//    private void requestData(){
//        HashMap<String, Object> requestMap = new HashMap<String, Object>();
//        requestMap.put("id",id);
//        post(ProjectConstants.Url.ACTIVITY_DETAIl, requestMap,1);
//    }


    private void requestCollect(){
        ProgressDialogHelper.showProgressDialog(ActivityClassDetailActivity.this,"加载中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("id",activityBean.getId());
        requestMap.put("device",0);
        requestMap.put("type",4);
        post(ProjectConstants.Url.COLLECT, requestMap,2);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_enroll) {
            if (Helper.isNotEmpty(activityBean)){
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProjectConstants.BundleExtra.KEY_ACTIVITY,activityBean);
                NavigationHelper.startActivity(ActivityClassDetailActivity.this, EnrollApplyActivity.class, bundle, false);
            }
        }else if (id == R.id.txv_location){
            if (Helper.isNotEmpty(activityBean.getLatitude()) && Helper.isNotEmpty(activityBean.getLongitude())){
                Bundle bundle = new Bundle();
                bundle.putString("latitude",String.valueOf(activityBean.getLatitude()));
                bundle.putString("longitude",String.valueOf(activityBean.getLongitude()));
                NavigationHelper.startActivity(ActivityClassDetailActivity.this, AddressActivity.class, bundle, false);
            }
        }
    }
}
