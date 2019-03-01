package com.weima.aishangyi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.PreferencesHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.activity.AnswerQuestionAcitity;
import com.weima.aishangyi.activity.AnswerSearchActivity;
import com.weima.aishangyi.activity.UserAnswerActivity;
import com.weima.aishangyi.adapter.AnswerAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.ClassTypeResp;
import com.weima.aishangyi.entity.QuestionResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.pop.SelectAnswerTypePop;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 问答
 * Created by cgy on 16/7/18.
 */
public class AnswerFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener, ResponseListener {
    private TextView txv_answer_type;
    private RelativeLayout rel_actionbar;
    private XListView xListView;
    private LoadingView loadingView;
    private AnswerAdapter mAdapter;
    private long type = -1;
    private int currentPage = 1;
    private SelectAnswerTypePop selectAnswerTypePop;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_QUESTION_LIST));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mUpdateUserInfoReceiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_answer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        requestData();
        initType();
    }

    private void initUI(View view) {
        rel_actionbar = (RelativeLayout) view.findViewById(R.id.rel_actionbar);
        txv_answer_type = (TextView) view.findViewById(R.id.txv_answer_type);
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        mAdapter = new AnswerAdapter(getActivity());
        xListView.setAdapter(mAdapter);
        xListView.setDivider(new ColorDrawable(0xfff8f8f8));
        xListView.setDividerHeight(20);
        txv_answer_type.setOnClickListener(this);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        view.findViewById(R.id.imv_answer_chat).setOnClickListener(this);
        view.findViewById(R.id.imv_answer_search).setOnClickListener(this);
        view.findViewById(R.id.btn_question).setOnClickListener(this);
    }

    private void initType(){
        try {
            List<ClassTypeResp.DataBean> classTypeList = new ArrayList<>();
            ClassTypeResp.DataBean  dataBean = new ClassTypeResp.DataBean();
            dataBean.setName("所有");
            dataBean.setId(-1);
            classTypeList.add(dataBean);
            String response = PreferencesHelper.getInstance().getString(ProjectConstants.Preferences.KEY_CLASS_TYPE);
            if (Helper.isNotEmpty(response)){
                ClassTypeResp entity = JsonHelper.fromJson(response, ClassTypeResp.class);
                if ("200".equals(entity.getCode())) {
                    if (Helper.isNotEmpty(entity.getData())){
                        classTypeList.addAll(entity.getData());
                    }
                } else {
                    ToastHelper.showToast(entity.getMessage());
                }
            }
            type = classTypeList.get(0).getId();
            txv_answer_type.setText(ProjectHelper.getCommonText(classTypeList.get(0).getName()));
            selectAnswerTypePop = new SelectAnswerTypePop(getActivity(), classTypeList, new SelectAnswerTypePop.SelectListener() {
                @Override
                public void onSelected(ClassTypeResp.DataBean typeData) {
                    if (Helper.isNotEmpty(typeData)){
                        type = typeData.getId();
                        txv_answer_type.setText(ProjectHelper.getCommonText(typeData.getName()));
                        requestData();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imv_answer_chat) {
            NavigationHelper.startActivity(getActivity(), UserAnswerActivity.class, null, false);
        } else if (id == R.id.imv_answer_search) {
            Intent intent = new Intent(getActivity(), AnswerSearchActivity.class);
            startActivityForResult(intent, ProjectConstants.ActivityRequestCode.REQUEST_HOME_LOCATION);
        } else if (id == R.id.txv_answer_type) {
            if (selectAnswerTypePop.isShowing()) {
                selectAnswerTypePop.dismiss();
            } else {
                selectAnswerTypePop.show(v);
            }
        }else if (id == R.id.btn_question){
            NavigationHelper.startActivity(getActivity(), AnswerQuestionAcitity.class, null, false);
        }
    }

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        if (type != -1){
            requestMap.put("item",type);
        }
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
}
