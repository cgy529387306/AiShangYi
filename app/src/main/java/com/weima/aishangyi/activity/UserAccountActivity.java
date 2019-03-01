package com.weima.aishangyi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.mb.android.utils.view.LoadingView;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.BalanceResp;
import com.weima.aishangyi.entity.TalentResp;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 我的账户
 */
public class UserAccountActivity extends BaseActivity {
    private TextView txv_balance;
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
        setCustomTitle("我的账户");
        setContentView(R.layout.activity_user_account);
        initUI();
        requestData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mUpdateUserInfoReceiver, new IntentFilter(ProjectConstants.BroadCastAction.UPDATE_BALANCE));
    }


    private void initUI() {
        txv_balance = findView(R.id.txv_balance);//平台账户总金额
        findView(R.id.btn_paymentdetail).setOnClickListener(mClickListener);
        findView(R.id.btn_accountmanage).setOnClickListener(mClickListener);
        findView(R.id.btn_recharge).setOnClickListener(mClickListener);
        findView(R.id.btn_withdrawcash).setOnClickListener(mClickListener);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        BalanceResp entity = JsonHelper.fromJson(response, BalanceResp.class);
        if ("200".equals(entity.getCode())){
            txv_balance.setText("账户余额:¥"+ProjectHelper.getCommonText(entity.getData()));
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        return true;
    }


    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_paymentdetail://收支明细
                    NavigationHelper.startActivity(UserAccountActivity.this, UserPaymentDetailActivity.class, null, false);
                    break;

                case R.id.btn_accountmanage://账号管理
                    NavigationHelper.startActivity(UserAccountActivity.this, UserAccountManagerActivity.class, null, false);
                    break;

                case R.id.btn_recharge://充值
                    NavigationHelper.startActivity(UserAccountActivity.this, UserRechargeActivity.class, null, false);
                    break;
                case R.id.btn_withdrawcash://提现
                    NavigationHelper.startActivity(UserAccountActivity.this, UserWithdrawCashActivity.class, null, false);
                    break;
                default:
                    break;
            }
        }
    };

    private void requestData(){
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        post(ProjectConstants.Url.CASH_BALANCE, requestMap);
    }


}
