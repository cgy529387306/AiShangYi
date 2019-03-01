package com.weima.aishangyi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.TalentQueryAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.InfoResp;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 才艺资讯
 *
 * @author cgy
 */
public class TalentQueryActivity extends BaseActivity implements XListView.IXListViewListener {
    private XListView xListView;
    private LoadingView loadingView;
    private int currentPage = 1;
    private TalentQueryAdapter mAdapter;
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
        setCustomTitle("才艺资讯");
        setContentView(R.layout.common_listview);
        initUI();
        requestData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_INFO_LIST));
    }

    private void initUI() {
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        mAdapter = new TalentQueryAdapter(TalentQueryActivity.this);
        xListView.setAdapter(mAdapter);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        InfoResp entity = JsonHelper.fromJson(response, InfoResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                if (Helper.isNotEmpty(entity.getData().getData())){
                    if (currentPage == 1){
                        mAdapter.clear();
                    }
                    if (currentPage >= entity.getData().getLast_page()){
                        xListView.setPullLoadEnable(false);
                    }else{
                        xListView.setPullLoadEnable(true);
                    }
                    mAdapter.addMore(entity.getData().getData());
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

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("page", currentPage);
        post(ProjectConstants.Url.INFO, requestMap);
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
}
