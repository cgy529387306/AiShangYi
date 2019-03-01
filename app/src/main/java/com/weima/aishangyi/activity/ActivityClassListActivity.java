package com.weima.aishangyi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.ActivityClassAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ActivityResp;
import com.weima.aishangyi.pop.FunSortPop;
import com.weima.aishangyi.pop.FunTypePop;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 作者：cgy on 16/11/26 15:21
 * 邮箱：593960111@qq.com
 * 活动课堂
 */
public class ActivityClassListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private LinearLayout lin_tab;
    private FunTypePop funTypePop;
    private FunSortPop funSortPop;
    private TextView btn_filter_type, btn_filter_sort;
    private XListView xListView;
    private ActivityClassAdapter adapter;
    private int currentPage = 1;
    private LoadingView loadingView;
    private int classType = 0;
    private int sortType = 0;
    private LocalBroadcastManager mLocalBroadcastManager;
    /**
     * 更新用户信息广播接受者
     */
    private BroadcastReceiver mUpdateUserInfoReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            requestData();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mUpdateUserInfoReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityclass_list);
        setCustomTitle("活动课堂");
        initUI();
        requestData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_ACTIVITY_LIST));
    }

    private void initUI() {
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        adapter = new ActivityClassAdapter(ActivityClassListActivity.this);
        xListView.setAdapter(adapter);
        funTypePop = new FunTypePop(this, new FunTypePop.SelectListener()  {
            @Override
            public void onSelected(int type) {
                setTabType(-1);
                classType = type;
                if (classType == 1){
                    btn_filter_type.setText("艺术");
                }else if (classType == 2){
                    btn_filter_type.setText("兴趣");
                }else if (classType == 3){
                    btn_filter_type.setText("演出");
                }else{
                    btn_filter_type.setText("兴趣分类");
                }
                currentPage = 1;
                requestData();
            }
        });
        funSortPop = new FunSortPop(this, new FunSortPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                setTabType(-1);
                if (type == 0){
                    sortType = 0;
                    btn_filter_sort.setText("综合排序");
                }else if (type == 1){
                    sortType = 1;
                    btn_filter_sort.setText("最新上线");
                }else if (type == 2){
                    sortType = 6;
                    btn_filter_sort.setText("最近开始");
                }else if (type == 3){
                    sortType = 4;
                    btn_filter_sort.setText("价格低到高");
                }
                currentPage = 1;
                requestData();
            }
        });

        lin_tab = findView(R.id.lin_tab);
        btn_filter_type = findView(R.id.btn_filter_type);
        btn_filter_sort = findView(R.id.btn_filter_sort);
        xListView = findView(R.id.xListView);
        btn_filter_type.setOnClickListener(this);
        btn_filter_sort.setOnClickListener(this);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        loadingView.postLoadState(LoadingView.State.GONE);
        ActivityResp entity = JsonHelper.fromJson(response,ActivityResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                if (Helper.isNotEmpty(entity.getData().getData())){
                    if (currentPage == 1){
                        adapter.clear();
                    }
                    if (currentPage >= entity.getData().getLast_page()){
                        xListView.setPullLoadEnable(false);
                    }else{
                        xListView.setPullLoadEnable(true);
                    }
                    adapter.addMore(entity.getData().getData());
                }else{
                    loadingView.postLoadState(LoadingView.State.LOADING_EMPTY);
                    xListView.setPullLoadEnable(false);
                }
            }
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        xListView.stopRefresh();
        xListView.stopLoadMore();
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        loadingView.postLoadState(LoadingView.State.LOADING_FALIED);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_filter_type) {
            setTabType(0);
        } else if (id == R.id.btn_filter_sort) {
            setTabType(1);
        }
    }

    private void setTabType(int type) {
        btn_filter_type.setSelected(type == 0 && !btn_filter_type.isSelected());
        btn_filter_sort.setSelected(type == 1 && !btn_filter_sort.isSelected());
        if (btn_filter_type.isSelected()) {
            funTypePop.show(lin_tab);
        } else {
            funTypePop.dismiss();
        }
        if (btn_filter_sort.isSelected()) {
            funSortPop.show(lin_tab);
        } else {
            funSortPop.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        currentPage=1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        requestData();
    }

    private void requestData(){
        String longitude = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_LON);
        String latitude = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_LAT);
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        if (sortType!=0){
            requestMap.put("sort",sortType);
        }
        if (classType!=0){
            requestMap.put("type",classType);
        }
        requestMap.put("page",currentPage);
        requestMap.put("longitude", longitude);
        requestMap.put("latitude", latitude);
        post(ProjectConstants.Url.ACTIVITY_LIST, requestMap);
    }
}
