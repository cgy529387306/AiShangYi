package com.weima.aishangyi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.ClassroomOrderAdapter;
import com.weima.aishangyi.adapter.CouponsAdapter;
import com.weima.aishangyi.adapter.TalentCircleAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CouponBean;
import com.weima.aishangyi.entity.CouponResp;
import com.weima.aishangyi.entity.TalentResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 我的优惠券
 */
public class CouponsFragment extends Fragment implements XListView.IXListViewListener , ResponseListener {
    private CouponsAdapter mAdapter;
    private XListView xListView;
    private LoadingView loadingView;
    private int currentPage = 1;
    private int type = 1;
    private static final String TYPE = "TYPE";
    private int is_clickable = 0;
    public static CouponsFragment newInstance(int type) { //对外提供创建实例的方法，你给我需要显示的内容，我给你Fragment实例
        CouponsFragment fragment = new CouponsFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE);
        is_clickable = getActivity().getIntent().getIntExtra("is_clickable",0);
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
        mAdapter = new CouponsAdapter(getActivity());
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(this);
        xListView.setDividerHeight(0);
        xListView.setAdapter(mAdapter);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (is_clickable==1){
                    int index = position-xListView.getHeaderViewsCount();
                    CouponBean couponBean = mAdapter.getItem(index);
                    if (couponBean.getStatus() == 0){
                        Intent intent = new Intent();
                        intent.putExtra(ProjectConstants.BundleExtra.KEY_COUPONS, couponBean.getMoney());
                        intent.putExtra(ProjectConstants.BundleExtra.KEY_COUPONS_ID, couponBean.getId());
                        getActivity().setResult(Activity.RESULT_OK, intent);
                        getActivity().finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        CouponResp entity = JsonHelper.fromJson(response, CouponResp.class);
        if ("200".equals(entity.getCode())){
            if (Helper.isNotEmpty(entity.getData())){
                mAdapter.clear();
                mAdapter.addMore(entity.getData());
            }else{
                loadingView.postLoadState(LoadingView.State.LOADING_EMPTY);
                xListView.setPullLoadEnable(false);
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
        requestMap.put("status", type);
        requestMap.put("page",currentPage);
        post(ProjectConstants.Url.USER_COUNPON, requestMap);
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
