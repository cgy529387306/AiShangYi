package com.weima.aishangyi.activity;

import android.os.Bundle;
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
import com.weima.aishangyi.adapter.NearbyAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.NearResp;
import com.weima.aishangyi.pop.AllSexPop;
import com.weima.aishangyi.pop.DistancePop;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 作者：cgy on 16/11/26 15:21
 * 邮箱：593960111@qq.com
 * 附近的人
 */
public class NearbyActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private LinearLayout lin_tab;
    private AllSexPop sexPop;
    private DistancePop distancePop;
    private TextView btn_filter_sex, btn_filter_distance;
    private XListView xListView;
    private LoadingView loadingView;
    private NearbyAdapter mAdapter;
    private int sex = 0;
    private int currentPage = 1;
    private int distance = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_list);
        setCustomTitle("附近的人");
        initUI();
        requestData();
    }

    private void initUI() {
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        mAdapter = new NearbyAdapter(NearbyActivity.this);
        xListView.setAdapter(mAdapter);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        sexPop = new AllSexPop(this, new AllSexPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                setTabType(-1);
                sex = type;
                if (sex == 0){
                    btn_filter_sex.setText("所有性别");
                }else if (sex == 1){
                    btn_filter_sex.setText("男");
                }else if (sex == 2){
                    btn_filter_sex.setText("女");
                }
                requestData();
            }
        });
        distancePop = new DistancePop(this, new DistancePop.SelectListener() {
            @Override
            public void onSelected(int type) {
                setTabType(-1);
                distance = type;
                if (type == 0){
                    btn_filter_distance.setText("全城");
                }else if (type == 1){
                    btn_filter_distance.setText("500米");
                }else if (type == 2){
                    btn_filter_distance.setText("1000米");
                }else if (type == 3){
                    btn_filter_distance.setText("3000米");
                }
                requestData();
            }
        });
        lin_tab = findView(R.id.lin_tab);
        btn_filter_sex = findView(R.id.btn_filter_sex);
        btn_filter_distance = findView(R.id.btn_filter_distance);
        xListView = findView(R.id.xListView);
        btn_filter_sex.setOnClickListener(this);
        btn_filter_distance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_filter_sex) {
            setTabType(0);
        } else if (id == R.id.btn_filter_distance) {
            setTabType(1);
        }
    }

    private void setTabType(int type) {
        btn_filter_sex.setSelected(type == 0 && !btn_filter_sex.isSelected());
        btn_filter_distance.setSelected(type == 1 && !btn_filter_distance.isSelected());
        if (btn_filter_sex.isSelected()) {
            sexPop.show(lin_tab);
        } else {
            sexPop.dismiss();
        }
        if (btn_filter_distance.isSelected()) {
            distancePop.show(lin_tab);
        } else {
            distancePop.dismiss();
        }
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        NearResp entity = JsonHelper.fromJson(response, NearResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                if (Helper.isNotEmpty(entity.getData())){
                    if (currentPage == 1){
                        mAdapter.clear();
                    }
//                    if (currentPage >= entity.getData().getLast_page()){
//                        xListView.setPullLoadEnable(false);
//                    }else{
//                        xListView.setPullLoadEnable(true);
//                    }
                    mAdapter.addMore(entity.getData());
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
        String longitude = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_LON);
        String latitude = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_LAT);
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("page", currentPage);
        requestMap.put("longitude", longitude);
        requestMap.put("latitude", latitude);
        requestMap.put("sex", sex);
        requestMap.put("distance", distance);
        post(ProjectConstants.Url.USER_NEAR, requestMap);
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
