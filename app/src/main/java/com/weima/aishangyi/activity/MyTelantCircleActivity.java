package com.weima.aishangyi.activity;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.TalentCircleAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.TalentResp;
import com.weima.aishangyi.entity.User;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 才艺圈
 */
public class MyTelantCircleActivity extends BaseActivity implements XListView.IXListViewListener , ResponseListener {
    private TalentCircleAdapter mAdapter;
    private XListView xListView;
    private LoadingView loadingView;
    private int currentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("才艺圈");
        setContentView(R.layout.common_listview);
        initUI();
        requestData();
    }

    private void initUI() {
        mAdapter = new TalentCircleAdapter(this);
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.setAdapter(mAdapter);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        TalentResp entity = JsonHelper.fromJson(response, TalentResp.class);
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
        User user = (User) getIntent().getSerializableExtra(ProjectConstants.BundleExtra.KEY_USER);
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("type",user.getDevice());
        requestMap.put("user_id", user.getId());
        post(ProjectConstants.Url.USER_TALEENT, requestMap);
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
