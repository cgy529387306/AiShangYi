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
import com.weima.aishangyi.adapter.ClassListAdapter;
import com.weima.aishangyi.adapter.RecommendTeacherAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.constants.UrlConstants;
import com.weima.aishangyi.entity.LessonListResp;
import com.weima.aishangyi.entity.LessonResp;
import com.weima.aishangyi.entity.TeacherListResp;
import com.weima.aishangyi.pop.SelectFilterConditionPop;
import com.weima.aishangyi.pop.SelectFilterSortPop;
import com.weima.aishangyi.pop.SelectFilterTypePop;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 作者：cgy on 16/11/26 15:21
 * 邮箱：593960111@qq.com
 * 课程列表
 */
public class ClassListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private LinearLayout lin_tab;
    private SelectFilterTypePop selectFilterTypePop;
    private SelectFilterSortPop selectFilterSortPop;
    private SelectFilterConditionPop selectFilterConditionPop;
    private TextView btn_filter_type, btn_filter_sort, btn_filter_condition;
    private XListView xListView;
    private LoadingView loadingView;
    private ClassListAdapter mAdapter;
    private int currentPage = 1;
    private long typeId = -1;
    private int sort = -1;
    private int sex = -1;//性别 1男，2女
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
        setContentView(R.layout.activity_teacher_list);
        setCustomTitle("课程列表");
        setImageRightButton(R.drawable.ic_search_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.startActivity(ClassListActivity.this, HomeSearchActivity.class, null, false);
            }
        });
        initUI();
        requestData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_LESSON_LIST));
    }

    private void initUI() {
        typeId = getIntent().getLongExtra("id", 0);
        xListView = findView(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        mAdapter = new ClassListAdapter(ClassListActivity.this);
        xListView.setAdapter(mAdapter);
        loadingView = findView(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        selectFilterTypePop = new SelectFilterTypePop(this, new SelectFilterTypePop.SelectListener() {
            @Override
            public void onSelected(long type,String name) {
                setTabType(-1);
                typeId = type;
                btn_filter_type.setText(name);
                requestData();
            }
        });
        selectFilterSortPop = new SelectFilterSortPop(this, new SelectFilterSortPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                setTabType(-1);
                if (type == 0){
                    sort = -1;
                    btn_filter_sort.setText("智能排序");
                }else if (type == 1){
                    sort = 1;
                    btn_filter_sort.setText("离我最近");
                }else if (type == 2){
                    sort = 0;
                    btn_filter_sort.setText("评价最好");
                }else if (type == 3){
                    sort = 2;
                    btn_filter_sort.setText("人气最高");
                }else if (type == 4){
                    sort = 3;
                    btn_filter_sort.setText("价格最高");
                }else if (type == 5){
                    sort = 4;
                    btn_filter_sort.setText("价格最低");
                }else if (type == 6){
                    sort = 5;
                    btn_filter_sort.setText("教龄最高");
                }
                requestData();
            }
        });
        selectFilterConditionPop = new SelectFilterConditionPop(this, new SelectFilterConditionPop.SelectListener() {
            @Override
            public void onSelected(int type) {
                setTabType(-1);
                if (type == 0){
                    sex = 1;
                    btn_filter_condition.setText("男老师");
                }else if (type == 1){
                    sex = 2;
                    btn_filter_condition.setText("女老师");
                }
                requestData();
            }
        });

        lin_tab = findView(R.id.lin_tab);
        btn_filter_type = findView(R.id.btn_filter_type);
        btn_filter_sort = findView(R.id.btn_filter_sort);
        btn_filter_condition = findView(R.id.btn_filter_condition);
        btn_filter_type.setOnClickListener(this);
        btn_filter_sort.setOnClickListener(this);
        btn_filter_condition.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_filter_type) {
            setTabType(0);
        } else if (id == R.id.btn_filter_sort) {
            setTabType(1);
        } else if (id == R.id.btn_filter_condition) {
            setTabType(2);
        }
    }

    private void setTabType(int type) {
        btn_filter_type.setSelected(type == 0 && !btn_filter_type.isSelected());
        btn_filter_sort.setSelected(type == 1 && !btn_filter_sort.isSelected());
        btn_filter_condition.setSelected(type == 2 && !btn_filter_condition.isSelected());
        if (btn_filter_type.isSelected()) {
            selectFilterTypePop.show(lin_tab);
        } else {
            selectFilterTypePop.dismiss();
        }
        if (btn_filter_sort.isSelected()) {
            selectFilterSortPop.show(lin_tab);
        } else {
            selectFilterSortPop.dismiss();
        }
        if (btn_filter_condition.isSelected()) {
            selectFilterConditionPop.show(lin_tab);
        } else {
            selectFilterConditionPop.dismiss();
        }
    }


    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        try {
            LessonListResp entity = JsonHelper.fromJson(response, LessonListResp.class);
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
        }catch (Exception e){
            e.printStackTrace();
            ToastHelper.showToast("请求异常："+e.getMessage());
        }
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
        requestMap.put("type", 2);
        requestMap.put("page", currentPage);
        requestMap.put("longitude", longitude);
        requestMap.put("latitude", latitude);
        if (typeId != -1){
            requestMap.put("item", typeId);
        }
        if (sort != -1){
            requestMap.put("sort", sort);
        }
        if (sex != -1){
            requestMap.put("sex", sex);
        }
        post(UrlConstants.HOME_SEARCH, requestMap);
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
