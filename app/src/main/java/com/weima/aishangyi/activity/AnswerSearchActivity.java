package com.weima.aishangyi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AnswerAdapter;
import com.weima.aishangyi.adapter.BaseTextAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.db.SearchAnswerHistoryDao;
import com.weima.aishangyi.db.SearchHistoryDao;
import com.weima.aishangyi.entity.QuestionResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.pop.SelectSearchTypePop;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：cgy on 16/11/24 22:01
 * 邮箱：593960111@qq.com
 * 问题搜索
 */
public class AnswerSearchActivity extends BaseActivity implements View.OnClickListener,XListView.IXListViewListener{
    private ListView listView;
    private List<String> dataList = new ArrayList<>();
    private EditText edt_home_search;
    private SearchAnswerHistoryDao searchHistoryDao;
    private XListView xListView;
    private AnswerAdapter mAdapter;
    private int currentPage = 1;
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_answer_search);
        initUI();
        fillListData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_QUESTION_LIST));
    }

    private void initUI() {
        searchHistoryDao = new SearchAnswerHistoryDao();
        edt_home_search = findView(R.id.edt_home_search);
        listView = findView(R.id.listView);
        listView.addHeaderView(getHeaderView());
        listView.addFooterView(getFooterView());
        xListView = (XListView) findViewById(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        mAdapter = new AnswerAdapter(AnswerSearchActivity.this);
        xListView.setAdapter(mAdapter);
        xListView.setDivider(new ColorDrawable(0xfff8f8f8));
        xListView.setDividerHeight(20);
        findView(R.id.lin_actionbar_back).setOnClickListener(this);
        findView(R.id.txv_search).setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - listView.getHeaderViewsCount();
                if (Helper.isNotEmpty(dataList) && dataList.size()>index){
                    edt_home_search.setText(ProjectHelper.getCommonText(dataList.get(index)));
                    edt_home_search.setSelection(ProjectHelper.getCommonSeletion(dataList.get(index)));
                    requestData();
                }
            }
        });
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        QuestionResp entity = JsonHelper.fromJson(response, QuestionResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                if (Helper.isNotEmpty(entity.getData().getData())){
                    xListView.setVisibility(View.VISIBLE);
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
                    ToastHelper.showToast("无搜索结果");
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
        ProgressDialogHelper.dismissProgressDialog();
        return true;
    }

    private void requestData(){
        ProgressDialogHelper.showProgressDialog(this,"搜索中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("keyword",edt_home_search.getText().toString());
        requestMap.put("page",currentPage);
        post(ProjectConstants.Url.QUESTION_SEARCH, requestMap);
    }

    /**
     * 发送get请求
     * @param url 接口地址
     * @param requestParamsMap 请求参数Map
     * @param extras 附加参数（本地参数，将原样返回给回调）
     */
    public void post(String url, HashMap<String, Object> requestParamsMap, Object... extras) {
        LogHelper.i(url);
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestParamsMap).getRequestEntity();
        RequestHelper.post(entity, this, extras);
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

    private void fillListData(){
        dataList = searchHistoryDao.getList();
        if (Helper.isNotEmpty(dataList)){
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new BaseTextAdapter(AnswerSearchActivity.this,dataList));
        }else{
            listView.setVisibility(View.GONE);
        }
    }


    private View getHeaderView(){
        View view = LayoutInflater.from(AnswerSearchActivity.this).inflate(R.layout.header_home_search, null);
        return view;
    }

    private View getFooterView(){
        View view = LayoutInflater.from(AnswerSearchActivity.this).inflate(R.layout.footer_home_search, null);
        view.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHistoryDao.clear();
                fillListData();
            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lin_actionbar_back){
            finish();
        }else if (id == R.id.txv_search){
            if (Helper.isNotEmpty(edt_home_search.getText().toString())){
                searchHistoryDao.add(edt_home_search.getText().toString());
                fillListData();
                requestData();
            }else{
                ToastHelper.showToast("请输入搜索内容");
            }
        }
    }
}
