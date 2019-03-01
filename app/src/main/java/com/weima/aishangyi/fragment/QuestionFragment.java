package com.weima.aishangyi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AnswerAdapter;
import com.weima.aishangyi.adapter.ClassroomOrderAdapter;
import com.weima.aishangyi.adapter.QuestionAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassOrderResp;
import com.weima.aishangyi.entity.QuestionResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 我的提问
 */
public class QuestionFragment extends Fragment implements XListView.IXListViewListener , ResponseListener {
    private QuestionAdapter mAdapter;
    private XListView xListView;
    private LoadingView loadingView;
    private int type = 1;
    private static final String TYPE = "TYPE";
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_QUESTION_LIST));
    }

    public static QuestionFragment newInstance(int type) {
        //对外提供创建实例的方法，你给我需要显示的内容，我给你Fragment实例
        QuestionFragment fragment = new QuestionFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        requestData();
    }

    private void initUI(View view) {
        mAdapter = new QuestionAdapter(getActivity());
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.setAdapter(mAdapter);
        xListView.setDivider(new ColorDrawable(0xfff8f8f8));
        xListView.setDividerHeight(20);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        QuestionResp entity = JsonHelper.fromJson(response, QuestionResp.class);
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
        requestMap.put("process",type);
        requestMap.put("page",currentPage);
        post(ProjectConstants.Url.QUESTION_MY_ASK, requestMap);
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
}
