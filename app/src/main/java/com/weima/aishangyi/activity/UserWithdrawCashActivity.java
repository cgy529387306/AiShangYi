package com.weima.aishangyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.BalanceResp;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.utils.ProjectHelper;

import java.util.HashMap;

/**
 * 提现
 */
public class UserWithdrawCashActivity extends BaseActivity {
    private ImageView btn_zhifubao, btn_card;
    private TextView txv_balance,txv_account_ali,txv_account_card;
    private int payType = 0;//1支付宝，2银行卡
    private EditText edit_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("提现");
        setContentView(R.layout.activity_user_withdrawcash);
        initUI();
        initData();
        requestData();
    }


    private void initUI() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edit_money = findView(R.id.edit_money);
        txv_balance = findView(R.id.txv_balance);//当前账户余额
        btn_zhifubao = findView(R.id.btn_zhifubao);
        txv_account_ali = findView(R.id.txv_account_ali);
        txv_account_card = findView(R.id.txv_account_card);
        btn_zhifubao.setOnClickListener(mClickListener);
        btn_card = findView(R.id.btn_card);
        btn_card.setOnClickListener(mClickListener);
        findView(R.id.btn_accountmanage).setOnClickListener(mClickListener);
        findView(R.id.btn_submit).setOnClickListener(mClickListener);
    }

    private void initData(){
        txv_account_ali.setText(CurrentUser.getInstance().getAlipay()==null?"无账号":CurrentUser.getInstance().getAlipay());
        txv_account_card.setText(CurrentUser.getInstance().getBank_card()==null?"无账号":CurrentUser.getInstance().getBank_card());
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        int requestType = extras.length != 0 ? (Integer) extras[0] : 0;
        if (requestType == 1){
            CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
            if ("200".equals(entity.getCode())){
                ToastHelper.showToast("提现成功");
                LocalBroadcastManager.getInstance(UserWithdrawCashActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_BALANCE));
                finish();
            }else{
                ToastHelper.showToast(entity.getMessage());
            }
        }else{
            BalanceResp entity = JsonHelper.fromJson(response, BalanceResp.class);
            if ("200".equals(entity.getCode())){
                txv_balance.setText("账户余额:¥"+ ProjectHelper.getCommonText(entity.getData()));
            }else{
                ToastHelper.showToast(entity.getMessage());
            }
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ToastHelper.showToast("提现失败！");
        return true;
    }

    /**
     * 点击事件
     */
    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_zhifubao://支付宝
                    if (CurrentUser.getInstance().getAlipay()==null){
                        ToastHelper.showToast("未绑定支付宝账号");
                    }else{
                        payType = 1;
                        btn_zhifubao.setImageResource(R.drawable.ic_checkbox_checked);
                        btn_card.setImageResource(R.drawable.ic_checkbox_uncheck);
                    }
                    break;
                case R.id.btn_card://银行卡
                    if (CurrentUser.getInstance().getAlipay()==null){
                        ToastHelper.showToast("未绑定银行卡");
                    }else{
                        payType = 2;
                        btn_card.setImageResource(R.drawable.ic_checkbox_checked);
                        btn_zhifubao.setImageResource(R.drawable.ic_checkbox_uncheck);
                    }
                    break;
                case R.id.btn_accountmanage://账户管理
                    NavigationHelper.startActivity(UserWithdrawCashActivity.this, UserAccountManagerActivity.class, null, false);
                    break;
                case R.id.btn_submit://提交
                    requestWithdrawal();
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

    private void requestWithdrawal(){
        String money = edit_money.getText().toString();
        if (Helper.isEmpty(money) || Double.parseDouble(money)==0){
            ToastHelper.showToast("请输入提现金额");
            return;
        }
        if (payType == 0){
            ToastHelper.showToast("请选择收款方式");
            return;
        }
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("money",Double.parseDouble(money));
        requestMap.put("type",payType);
        post(ProjectConstants.Url.CASH_WITHDRAWAL, requestMap,1);
    }


}
