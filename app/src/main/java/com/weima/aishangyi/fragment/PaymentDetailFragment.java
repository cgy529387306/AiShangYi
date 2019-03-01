package com.weima.aishangyi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.AnswerAdapter;
import com.weima.aishangyi.adapter.PaymentDetailAdapter;
import com.weima.aishangyi.adapter.TalentCircleAdapter;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.PaymentResp;
import com.weima.aishangyi.entity.QuestionResp;
import com.weima.aishangyi.http.RequestEntity;
import com.weima.aishangyi.http.RequestHelper;
import com.weima.aishangyi.http.ResponseListener;
import com.weima.aishangyi.utils.LogHelper;
import com.weima.aishangyi.utils.ProjectHelper;
import com.weima.aishangyi.xlistview.XListView;

import java.util.HashMap;

/**
 * 联系人
 */
public class PaymentDetailFragment extends Fragment implements XListView.IXListViewListener, ResponseListener {
    private PaymentDetailAdapter mAdapter;
    private XListView xListView;
    private LoadingView loadingView;
    private int type = 1;
    private static final String TYPE = "TYPE";
    private int currentPage = 1;
    private TextView txv_all;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE);
    }

    public static PaymentDetailFragment newInstance(int type) {
        //对外提供创建实例的方法，你给我需要显示的内容，我给你Fragment实例
        PaymentDetailFragment fragment = new PaymentDetailFragment();
        Bundle b = new Bundle();
        b.putInt(TYPE, type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_paymentdetail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        requestData();
    }

    private void initUI(View view) {
        txv_all = (TextView) view.findViewById(R.id.txv_all);
        mAdapter = new PaymentDetailAdapter(getActivity(),type);
        xListView = (XListView) view.findViewById(R.id.xListView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.setAdapter(mAdapter);
        loadingView = (LoadingView) view.findViewById(R.id.loadingView);
        loadingView.postLoadState(LoadingView.State.LOADING);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        loadingView.postLoadState(LoadingView.State.GONE);
        PaymentResp entity = JsonHelper.fromJson(response, PaymentResp.class);
        if ("200".equals(entity.getCode())) {
            if (Helper.isNotEmpty(entity.getData())){
                initAllPayment(entity.getData());
            }
            if (Helper.isNotEmpty(entity.getData())) {
                if (Helper.isNotEmpty(entity.getData().getData())) {
                    if (currentPage == 1) {
                        mAdapter.clear();
                    }
                    if (currentPage >= entity.getData().getLast_page()) {
                        xListView.setPullLoadEnable(false);
                    } else {
                        xListView.setPullLoadEnable(true);
                    }
                    mAdapter.addMore(entity.getData().getData());
                } else {
                    loadingView.postLoadState(LoadingView.State.LOADING_EMPTY);
                    xListView.setPullLoadEnable(false);
                }
            }
        } else {
            ToastHelper.showToast(entity.getMessage());
        }
        xListView.stopRefresh();
        xListView.stopLoadMore();
        return true;
    }

    private void initAllPayment(PaymentResp.DataBean en) {
        if (type == 1){
            txv_all.setText("共收入："+en.getCash_in()+"元");
        }else if (type == 2){
            txv_all.setText("共支出："+en.getCash_out()+"元");
        }else if (type == 3){
            txv_all.setText("共充值："+en.getRecharge_total()+"元" +"    "+"共提现："+en.getWithdrawal_total()+"元");
        }

    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        loadingView.postLoadState(LoadingView.State.LOADING_FALIED);
        return true;
    }

    private void requestData() {
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("type", type);
        requestMap.put("page", currentPage);
        post(ProjectConstants.Url.CASH_INOUT, requestMap);
    }

    /**
     * 发送get请求
     *
     * @param url              接口地址
     * @param requestParamsMap 请求参数Map
     * @param extras           附加参数（本地参数，将原样返回给回调）
     */
    public void post(String url, HashMap<String, Object> requestParamsMap, Object... extras) {
        LogHelper.i(url);
        RequestEntity entity = new RequestEntity.Builder().setTimeoutMs(20000).setUrl(url).setRequestHeader(ProjectHelper.getUserAgent1()).setRequestParamsMap(requestParamsMap).getRequestEntity();
        RequestHelper.post(entity, this, extras);
    }


    @Override
    public void onRefresh() {
        currentPage = 1;
        requestData();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        requestData();
    }
}
